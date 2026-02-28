# AGENTS.md - Project Guidelines for AI Agents

## 1. Project Overview

**Project:** OrbisGrip (formerly HyGrip)  
**Type:** Hytale Server Mod (Kotlin + Gradle KTS)  
**Core Functionality:** A robotic arm system ("Grips") for Hytale that enables gripping, lifting, and placing objects programmatically. Uses a data-driven framework where grip types are defined via JSON.

---

## 2. Tech Stack

- **Language:** Kotlin
- **Build System:** Gradle KTS
- **API:** Hytale Server API (`com.hypixel.hytale`)
- **Testing:** JUnit 5
- **Architecture:** Entity-Component-System (ECS) pattern

---

## 3. Core Conventions

### 3.1 Code Language
- **ALL code** (variables, functions, classes, files, commits, comments, documentation) must be in **ENGLISH**.
- **Interaction language** with users: **SPANISH**.

### 3.2 Architecture Patterns
- **ECS Pattern:** Use components for state (`*Component.java`) and systems for logic (`*System.java`).
- **Data-Driven:** Grip definitions should be loaded from JSON files in `src/main/resources/definitions/grips/`.
- **Registry Pattern:** Use centralized registries (e.g., `GripRegistry`) to manage definitions.

### 3.3 Testing Standards
- **Framework:** JUnit 5 (Jupiter)
- **Location:** `src/test/java/com/hyfactory/hygrip/`
- **Isolation:** Tests must not require Hytale runtime. Use mocks/stubs for API dependencies.
- **Coverage:** All new functionality should have corresponding unit tests.

---

## 4. Git Conventions

### 4.1 Commit Messages
Follow **Conventional Commits**:
- `feat(grips): add new grip type functionality`
- `fix(crane): resolve movement calculation error`
- `test(grips): add unit tests for GripRegistry`
- `refactor(ecs): simplify component state management`

### 4.2 Branch Naming
- Feature: `feature/feature-name`
- Bugfix: `fix/issue-description`
- Hotfix: `hotfix/critical-fix`

---

## 5. SDD Workflow

This project uses **Spec-Driven Development (SDD)**:

1. **Proposal** → What and Why
2. **Specs** → Requirements and scenarios
3. **Design** → Technical approach
4. **Tasks** → Atomic implementation steps
5. **Apply** → Code implementation
6. **Verify** → Tests + Build validation
7. **Commit** → Version control
8. **Archive** → Merge specs to main

All SDD artifacts are stored in **Engram** (memory system).

---

## 6. Build Commands

```bash
# Run tests
./gradlew test

# Build JAR
./gradlew build

# Run dev server
./gradlew runServer

# Setup server first time
./gradlew setupServer
```

---

## 7. Key Files

| File | Purpose |
|------|---------|
| `GripDefinition.java` | Data model for grip types (loaded from JSON) |
| `GripRegistry.java` | Central registry for loading grip definitions |
| `CraneStateComponent.java` | ECS component tracking crane state |
| `CraneInteractionSystem.java` | ECS system handling pickup/deposit logic |
| `CraneMovementSystem.java` | ECS system handling crane movement |
| `HyGripPlugin.java` | Main plugin entry point |
| `standard.json` | Default grip definition (example) |

---

## 8. Rules for AI Agents

- **NEVER** modify the format of existing files unless explicitly requested.
- **ALWAYS** run `./gradlew test` after making changes to verify no regressions.
- **ALWAYS** use the "Better Comments" standard:
  - `//!` Critical issue or warning
  - `//?` Question or explanation
  - `//*` Important highlight or new feature
  - `//TODO:` Future improvement
- **DO NOT** create PRs automatically — ask the user first.
- **USE** the SDD workflow for any non-trivial changes.
