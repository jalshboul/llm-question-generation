"""Run the full evaluation pipeline."""
from __future__ import annotations

import json
import pathlib

from .dataset import load_code_samples
from .llm import LLM
from .evaluator import evaluate_questions
from .metrics import (
    average_scores,
    repetition_rate,
    aggregate_repetition,
    ModelResult,
    build_win_matrix,
    win_rates,
    elo_ratings,
)


MODELS = [
    "gpt-4-0314",
    "Llama-2-70b-chat-hf",
    "gpt-4-0613",
    "Llama-2-13b-chat-hf",
    "claude-2",
    "gpt-3.5-turbo-0613",
    "h2ogpt-gm-oasst1-en-2048-falcon-40b-v1",
    "h2ogpt-gm-oasst1-en-2048-falcon-40b-v2",
    "vicuna-33b-v1.3",
    "falcon-40b-sft-top1-560",
    "h2ogpt-research-oasst1-llama-65b",
    "mixtral-8x7b-instruct-v0.1",
    "h2ogpt-gm-oasst1-en-2048-falcon-7b",
    "h2ogpt-gm-oasst1-en-2048-falcon-7b-v3",
    "falcon-40b-instruct",
]


def run():
    results: list[ModelResult] = []
    scores_by_model: dict[str, list[float]] = {m: [] for m in MODELS}
    reps_by_model: dict[str, list[float]] = {m: [] for m in MODELS}
    for sample in load_code_samples():
        for model_name in MODELS:
            llm = LLM(model_name)
            questions = llm.generate_questions(sample["code"], sample["language"]) 
            eval_data = evaluate_questions(questions)
            score = average_scores(eval_data)
            rep = repetition_rate(questions)
            result = ModelResult(model_name, score, {
                "sample": sample["id"],
                "repetition_rate": rep,
                "evaluation": eval_data,
            })
            results.append(result)
            scores_by_model[model_name].append(score)
            reps_by_model[model_name].append(rep)
            out_dir = pathlib.Path("results")
            out_dir.mkdir(exist_ok=True)
            with open(out_dir / f"{model_name}_{sample['id']}.json", "w") as f:
                json.dump(result.data, f, indent=2)
    # aggregate average scores
    ranking = sorted(
        ((m, sum(scores) / len(scores)) for m, scores in scores_by_model.items()),
        key=lambda x: x[1],
        reverse=True,
    )
    wins, comps = build_win_matrix(scores_by_model)
    winrate = win_rates(wins, comps)
    elo = elo_ratings(scores_by_model)
    repetition = aggregate_repetition(reps_by_model)
    summary = {
        "average_scores": ranking,
        "win_rate": winrate,
        "elo_rating": elo,
        "repetition_rate": repetition,
        "wins": wins,
        "comparisons": comps,
    }
    out_dir = pathlib.Path("results")
    out_dir.mkdir(exist_ok=True)
    with open(out_dir / "summary.json", "w") as f:
        json.dump(summary, f, indent=2)
    for model, score in ranking:
        print(f"{model}: {score:.2f}")


if __name__ == "__main__":
    run()