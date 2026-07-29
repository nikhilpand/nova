# LangGraph Calculator Agent

A minimal LangGraph agent built from the [official quickstart](https://docs.langchain.com/oss/python/langgraph/quickstart), using the **Graph API**.

## Model

`google_genai:gemini-2.5-pro` — swap this in `agent.py` with any `provider:model` string (e.g. `openai:gpt-4o`, `anthropic:claude-sonnet-5`).

## Setup

```bash
# 1. Install dependencies
pip install langgraph langchain langchain-google-genai python-dotenv

# 2. Add your API key to .env
#    GOOGLE_API_KEY=your-key-here

# 3. Run
python agent.py
```

## How It Works

The agent uses a **StateGraph** with two nodes:

1. **`llm_call`** — calls Gemini with bound tools (add, multiply, divide)
2. **`tool_node`** — executes whichever tool the LLM selected

A conditional edge (`should_continue`) loops back to the LLM if tool calls were made, or ends if the LLM returns a final answer.

```
START → llm_call → [tool_calls?] → tool_node → llm_call → ... → END
```

## Next Steps

- Explore `langgraph-fundamentals` for deeper patterns (memory, human-in-the-loop, streaming).
- For a higher-level agent API, see LangChain `create_agent`.
