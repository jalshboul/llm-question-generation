"""Simple Flask app to generate questions from code using GPT-4."""
import json
import os
import tempfile
import subprocess
import shutil
import ast
from flask import Flask, request, render_template

from src.llm import LLM
from src.evaluator import evaluate_questions

app = Flask(__name__)
llm = LLM("gpt-4-0314")


def check_code_errors(code: str, language: str) -> str | None:
    """Return a description of compile-time syntax errors if any."""
    language = language.lower()
    if language == "python":
        try:
            ast.parse(code)
        except SyntaxError as e:
            return f"Syntax error: {e}"
        return None
    if language == "c++":
        if not shutil.which("g++"):
            return "Unable to check C++ syntax: g++ compiler not available"
        with tempfile.NamedTemporaryFile("w", suffix=".cpp", delete=False) as tmp:
            tmp.write(code)
            tmp_path = tmp.name
        proc = subprocess.run(["g++", "-fsyntax-only", tmp_path], capture_output=True, text=True)
        os.unlink(tmp_path)
        if proc.returncode != 0:
            return proc.stderr.strip()
        return None
    if language == "java":
        if not shutil.which("javac"):
            return "Unable to check Java syntax: javac compiler not available"
        with tempfile.TemporaryDirectory() as tmpdir:
            path = os.path.join(tmpdir, "Temp.java")
            with open(path, "w") as f:
                f.write(code)
            proc = subprocess.run(["javac", path], capture_output=True, text=True)
            if proc.returncode != 0:
                return proc.stderr.strip()
        return None
    return None


def build_qa_prompt(code: str, language: str, count: int) -> str:
    return (
        f"Generate {count} programming questions and answers about the following {language} code. "
        "Return a JSON array of objects with 'question' and 'answer' fields.\n" + code
    )


@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        code = request.form.get("code", "")
        if not code and "file" in request.files:
            file = request.files["file"]
            if file and file.filename:
                code = file.read().decode("utf-8")
        language = request.form.get("language", "Python")
        count = int(request.form.get("count", 5))
        if not code:
            return render_template("index.html", error="No code provided")
        error_msg = check_code_errors(code, language)
        prompt = build_qa_prompt(code, language, count)
        raw = llm.generate(prompt)
        try:
            qa_pairs = json.loads(raw)
        except json.JSONDecodeError:
            return render_template("results.html", data=[], error="Failed to parse model output")
        questions = [item.get("question", "") for item in qa_pairs]
        evaluation = evaluate_questions(questions)
        eval_map = {}
        for q in evaluation.get("questions", []):
            eval_map[q.get("question", "")] = q
        data = [(item, eval_map.get(item.get("question", ""))) for item in qa_pairs]
        return render_template("results.html", data=data, code_error=error_msg)
    return render_template("index.html")


if __name__ == "__main__":
    app.run(debug=True)