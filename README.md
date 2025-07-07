# LLMs
# LLM Question Generation Framework

This repository implements the experimental pipeline described in the paper
"Large Language Models for Automatic Code-Based Question Generation and
Evaluation". The project loads code snippets, prompts fifteen large language
models to generate questions, evaluates the generated questions using GPT‑4, and
ranks the models using average scores, win rate, and Elo rating metrics.

## Usage
1. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
2. Export API keys for the services you wish to use:
   - `OPENAI_API_KEY` for OpenAI models
   - `ANTHROPIC_API_KEY` for Claude models
   - `REPLICATE_API_TOKEN` for models hosted on Replicate
3. Run the evaluation:
   ```bash
   python -m src.main
   ```
4. Generate plots summarizing win rates:
   ```bash
   python -m src.plots
   ```

Results will be stored in the `results/` directory as JSON files together with a
summary ranking of the evaluated models. The plotting script will create
`win_rate.png` and `win_matrix.png` in the same directory.

Human evaluation data and the resulting figures are provided in the
`HumanEvaluation/` folder. You can regenerate the plots from the included
`summary.json` using:

```bash
python -m src.plots HumanEvaluation/summary.json
```

## Flask App
A simple Flask web app `app.py` allows interactive generation of questions using `gpt-4-0314`. Install Flask from `requirements.txt`, set the `OPENAI_API_KEY` environment variable, and run:

```bash
python app.py
```

Open `http://localhost:5000` in a browser to paste or upload code, choose the programming language, and specify how many question‑answer pairs to generate. The app displays each question with its answer and evaluation metrics (Relevance, Clarity and Coherence, Conciseness, and Coverage).
The app also attempts to compile or parse the submitted code to detect syntax errors. Any problems found will be reported alongside the generated questions so instructors can generate Q&A even for imperfect code samples.

## Flask App User Interface

The Flask web application provides a clean and minimal interface for interactive code-based question generation and evaluation. Below is a conceptual visualization of the app’s UI:

### Main Page (`/`) – Code Q&A Generator

```
+---------------------------------------------------------------+
|                    Code Q&A Generator                         |
+---------------------------------------------------------------+
| Programming Language: [ Python ▼ ]                            |
| Number of questions: [ 5 ]                                    |
|                                                               |
| Paste code:                                                   |
| [---------------------------------------------------------]   |
| |                                                         |   |
| |   <textarea rows="10" cols="80">                        |   |
| |                                                         |   |
| [---------------------------------------------------------]   |
| Or upload file: [ Choose File ]                               |
|                                                               |
|                [ Generate ]                                   |
+---------------------------------------------------------------+
```
- **Dropdown** for language selection (Python, C++, Java, etc.)
- **Input** for number of questions
- **Textarea** for pasting code
- **File upload** option
- **Generate** button

---

### Results Page – Generated Questions and Answers

```
+---------------------------------------------------------------+
|            Generated Questions and Answers                    |
|  ← Back                                                       |
|                                                               |
| Q1: <question text>                                           |
| A1: <answer text>                                             |
|   Relevance: 0.89                                             |
|   Clarity and Coherence: 0.92                                 |
|   Conciseness: 0.87                                           |
|   Coverage: 0.84                                              |
|                                                               |
| [Additional question–answer pairs...]                         |
+---------------------------------------------------------------+
```
- Each question/answer pair is shown with its evaluation metrics.
- If there’s a code error, a message appears at the top in orange:  
  `Code analysis: Syntax error: ...`

**Visual Style:**
- Clean, modern, and minimal.
- Clear separation between form and results.
- Metrics are easy to read and visually grouped with each Q&A.

This interface enables instructors and researchers to quickly generate and review question–answer pairs and their quality metrics for any code sample, even if the code contains syntax errors.
