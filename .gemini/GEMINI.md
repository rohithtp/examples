# Project: Java Examples Repository

## Overview

This is a **Java 21** educational code repository demonstrating real-world patterns,
data structures, concurrency, streams, and design patterns. The project uses **Maven**
for builds and **JUnit 5** for testing.

---

## Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Language    | Java 21 (LTS)                     |
| Build Tool  | Maven (`pom.xml`)                 |
| Testing     | JUnit 5 (`junit-jupiter:5.10.0`)  |
| Utility lib | Apache Commons Lang 3 (`3.12.0`)  |
| Security    | OWASP Dependency-Check Maven      |

---

## Project Layout

```
examples/
├── src/
│   ├── main/java/com/code/examples/
│   │   ├── intro/
│   │   │   ├── serialization/        # Object serialization examples
│   │   │   └── solutions/            # Classic problem solutions (LRU cache, etc.)
│   │   ├── patterns/
│   │   │   └── create/single/        # Creational design patterns (Singleton, etc.)
│   │   ├── stream/                   # Java Stream API examples
│   │   ├── thread/                   # Concurrency & threading examples
│   │   └── InterfaceExamples.java    # Generic interfaces & strategy pattern
│   └── test/java/com/code/examples/  # Mirror of main; unit tests per package
├── .gemini/                          # Gemini AI instruction files (this folder)
│   └── GEMINI.md                     # Root instruction set (this file)
├── pom.xml
├── README.md
└── CONTRIBUTING.md
```

---

## Coding Conventions

### Java Style
- **Java 21** language features are fully available: records, sealed classes,
  pattern matching, text blocks, virtual threads, etc.
- Package root: `com.code.examples`
- Sub-packages mirror the topic: `thread`, `stream`, `patterns.create.single`, etc.
- Class names are **PascalCase**; method/variable names are **camelCase**.
- Constants are `UPPER_SNAKE_CASE` and declared `static final`.

### Documentation
- **Every new class** must have a Javadoc block explaining:
  - *What* the class demonstrates.
  - *Why* the approach was chosen.
  - *Key concepts* and trade-offs.
- Complex logic blocks use **inline box-style comments** (see `RestaurantOrderSystem`
  as the canonical reference):
  ```java
  // ┌──────────────────────────────────────────────────────┐
  // │  queue.put(order)                                    │
  // │  • Inserts the order into the queue.                 │
  // └──────────────────────────────────────────────────────┘
  ```
- Section separators use em-dashes:
  ```java
  // ─── public API ──────────────────────────────────────────
  ```

### Testing
- Test classes live under `src/test/java/` in the **same package** as the class under test.
- Use JUnit 5 annotations: `@Test`, `@BeforeEach`, `@AfterEach`, `@ParameterizedTest`.
- Test method names follow the pattern: `methodName_condition_expectedResult`.
- Each test must be self-contained (no shared mutable state between tests).

---

## Example Categories

### Currently Implemented

| Category              | Package                             | Description                               |
|-----------------------|-------------------------------------|-------------------------------------------|
| Interfaces / Strategy | `com.code.examples`                 | Generic `IOperation<T>` + calculator      |
| Streams               | `com.code.examples.stream`          | Filter, map, reduce, flatMap on lists     |
| Concurrency           | `com.code.examples.thread`          | BlockingQueue producer-consumer (restaurant) |
| Design Patterns       | `com.code.examples.patterns`        | Singleton (eager + lazy)                  |
| Serialization         | `com.code.examples.intro.serialization` | Java object serialization             |
| Data Structures       | `com.code.examples.intro.solutions` | LRU Cache (custom DLL+HashMap, LinkedHashMap) |

### Planned / Suggested Next Examples

- `thread/` — Virtual threads (Project Loom), `ExecutorService`, `CompletableFuture`
- `patterns/` — Builder, Factory, Observer, Decorator
- `stream/` — Collectors, groupingBy, custom collectors
- `collections/` — TreeMap, PriorityQueue, ConcurrentHashMap
- `functional/` — Function composition, Optional chaining

---

## Build & Run

```bash
# Compile
mvn compile

# Run all tests
mvn test

# Run a specific main class
mvn exec:java -Dexec.mainClass="com.code.examples.thread.RestaurantOrderSystem"

# Package
mvn package

# OWASP vulnerability check
mvn dependency-check:check
```

---

## AI Assistance Guidelines

When Gemini assists with this project:

1. **Always target Java 21** — prefer modern idioms (records, text blocks, switch
   expressions, pattern matching) over older alternatives.
2. **Maintain the rich documentation style** used throughout — new code must include
   class-level Javadoc and inline explanatory comments.
3. **Write the unit test alongside the implementation** — every new class should have
   a corresponding `*Test.java` in `src/test/`.
4. **Real-world scenarios preferred** — examples should demonstrate concepts through
   relatable analogies (e.g., a restaurant for producer-consumer, a bank for locks).
5. **No external dependencies** unless absolutely required and added to `pom.xml`
   with an explicit version.
6. **Thread safety** — concurrent examples must document which constructs provide
   safety and why (`synchronized`, `volatile`, `java.util.concurrent.*`).
7. **Keep examples runnable** — every non-abstract class in `main/` should have a
   `public static void main(String[] args)` entry point or be covered by tests.

---

## Key Design Patterns in Use

### Poison Pill (Graceful Shutdown)
Used in `RestaurantOrderSystem` to signal a consumer thread to stop:
```java
private static final String POISON_PILL = "KITCHEN_CLOSED";
orderBoard.put(POISON_PILL); // producer sends signal
// consumer breaks loop when it receives POISON_PILL
```

### Sentinel / Dummy Nodes
Used in `LRUCacheCustom` to simplify doubly-linked-list edge cases:
```java
head.next = tail; // always valid — no null checks needed
tail.prev = head;
```

### Strategy Pattern
Used in `InterfaceExamples` via `IOperation<T>` — callers inject an operation
implementation at runtime (`Addition`, `Subtraction`).

---

## Security

- The OWASP Dependency-Check plugin runs on every build to flag known CVEs.
- No user-provided data is deserialized without explicit documentation.
- Serialization examples (`SerializationExample`) are educational only — production
  code should prefer structured formats (JSON, Protobuf).
