"""Prompt utilities for question generation and evaluation."""
from dataclasses import dataclass


@dataclass
class PromptConfig:
    question_count: int = 50
    template: str = (
        "Generate {count} questions based on the following {lang} code:\n"\
        "{code}\n"
    )


def build_generation_prompt(code: str, language: str, count: int | None = None) -> str:
    """Return a prompt instructing the LLM to generate questions."""
    cfg = PromptConfig()
    count = count or cfg.question_count
    return cfg.template.format(count=count, lang=language, code=code)


def build_evaluation_prompt(questions: str) -> str:
    return (
        "the criteria are : Relevance, Clarity and Coherence (as one metric), "
        "Conciseness, Coverage and average of all. "
        "Give the score of each criteria for each and every question with the "
        "question itself. as the json file\n" + questions
    )