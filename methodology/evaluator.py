"""Evaluation utilities."""
from __future__ import annotations

import json
import os
from typing import List

import openai

from .prompt import build_evaluation_prompt


EVAL_MODEL = os.environ.get("EVAL_MODEL", "gpt-4-0314")


def evaluate_questions(questions: List[str]) -> dict:
    """Return evaluation metrics as parsed JSON."""
    client = openai.OpenAI(api_key=os.environ.get("OPENAI_API_KEY"))
    prompt = build_evaluation_prompt("\n".join(questions))
    resp = client.chat.completions.create(
        model=EVAL_MODEL,
        messages=[{"role": "user", "content": prompt}],
    )
    content = resp.choices[0].message.content
    try:
        return json.loads(content)
    except json.JSONDecodeError:
        return {"raw": content}