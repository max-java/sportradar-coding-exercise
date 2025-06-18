# Sportradar Coding Exercise

This solution is implemented using Spring Boot to leverage dependency injection, focus on core logic, and simplify testing.

The `GameEngine` simulates games by randomly updating scores every second for 10 seconds. It starts multiple threads for concurrent game simulation, while the `ScoreBoard` and `SummaryBoard` display live and completed games respectively. Both boards are updated automatically.

An in-memory `ConcurrentHashMap` is used to store game data safely across threads. The display mechanism is abstracted via the `BoardDisplay` interface to allow easy extension for future outputs like data lakes or message queues.

Tests cover all major components except `GameEngine`, which is intentionally excluded due to its used only for simple simulation. Test data is based on the original task description for easy result verification.

**Note:** To support multiple concurrent games, it's recommended to include a game ID with each score update, as score pairs alone are ambiguous.
