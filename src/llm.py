"""LLM wrappers for generating questions."""
from __future__ import annotations

import os
from dataclasses import dataclass
from typing import List

import openai
import replicate
import anthropic


@dataclass
class LLM:
    name: str

    def generate(self, prompt: str) -> str:
        if self.name.startswith("gpt-4") or self.name.startswith("gpt-3.5"):
            client = openai.OpenAI(api_key=os.environ.get("OPENAI_API_KEY"))
            response = client.chat.completions.create(
                model=self.name,
                messages=[{"role": "user", "content": prompt}],
            )
            return response.choices[0].message.content
        if self.name.startswith("claude"):
            client = anthropic.Anthropic(api_key=os.environ.get("ANTHROPIC_API_KEY"))
            msg = client.messages.create(
                model=self.name,
                messages=[{"role": "user", "content": prompt}],
                max_tokens=1000,
            )
            return "\n".join(part.text for part in msg.content)
        # assume replicate model name
        output = replicate.run(self.name, input={"prompt": prompt})
        if isinstance(output, list):
            return "\n".join(str(o) for o in output)
        return str(output)

    def generate_questions(self, code: str, language: str, count: int = 50) -> List[str]:
        from .prompt import build_generation_prompt

        prompt = build_generation_prompt(code, language, count)
        raw = self.generate(prompt)
        if isinstance(raw, str):
            lines = [line.strip() for line in raw.splitlines() if line.strip()]
            return lines
        return list(raw)