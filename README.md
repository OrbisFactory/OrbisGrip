# OrbisGrip

OrbisGrip (formerly HyGrip) is a Hytale Server Mod that adds a robotic arm system to the game. It enables gripping, lifting, and placing objects programmatically, making construction and object manipulation significantly easier.

## Features

- **Data-Driven Grip System**: Define multiple grip types via JSON configuration
- **ECS Architecture**: Clean separation between state (Components) and logic (Systems)
- **Automated Testing**: Comprehensive unit tests with JUnit 5
- **Visual Feedback**: Model attachment system for picked items

## Architecture

OrbisGrip follows the Entity-Component-System (ECS) pattern:

```
Components/          → State data (CraneStateComponent, etc.)
Systems/             → Business logic (CraneInteractionSystem, CraneMovementSystem)
plugin/              → Entry point and registries (GripRegistry, GripDefinition)
```

### Key Files

| File | Purpose |
|------|---------|
| `GripDefinition.java` | Data model for grip types (loaded from JSON) |
| `GripRegistry.java` | Central registry for loading grip definitions |
| `CraneStateComponent.java` | ECS component tracking crane state |
| `CraneInteractionSystem.java` | ECS system handling pickup/deposit logic |
| `CraneMovementSystem.java` | ECS system handling crane movement |
| `HyGripPlugin.java` | Main plugin entry point |

## Grip Definition (JSON)

Grips are defined in JSON files under `src/main/resources/definitions/grips/`:

```json
{
  "id": "standard",
  "name": "Standard Grip",
  "model": "models/block/wooden_crane",
  "speed": 2.0,
  "maxReach": 5
}
```

## Development

### Prerequisites

- JDK 17+
- Gradle 9.x
- Hytale Server API (provided by build system)

### Build Commands

```bash
# Run unit tests
./gradlew test

# Build the mod JAR
./gradlew build

# Start development server
./gradlew runServer

# Setup server (first time only)
./gradlew setupServer
```

### Testing

The project uses **JUnit 5** for unit testing. Tests are located in:

```
src/test/java/com/hyfactory/hygrip/
```

Run all tests:
```bash
./gradlew test
```

Run specific test class:
```bash
./gradlew test --tests com.hyfactory.hygrip.plugin.GripRegistryTest
```

## Usage In-Game

### Commands

- `/hygrip test` - Spawn a test crane at default position (0, 117, 0)
- `/hygrip test <x> <y> <z>` - Spawn crane at custom position
- `/hygrip test <x> <y> <z> <direction>` - With direction (north, south, east, west, up, down)
- `/hygrip test <direction>` - Default position with custom direction

### How It Works

1. **Pick Phase**: Crane moves to source position and "picks" the item
2. **Carry Phase**: Item is visually attached to the crane hook
3. **Deposit Phase**: Crane moves to target and releases the item

## Contributing

This project uses **Spec-Driven Development (SDD)**. See [AGENTS.md](AGENTS.md) for development guidelines.

### SDD Workflow

1. **Proposal** → Define what and why
2. **Specs** → Write requirements and scenarios
3. **Design** → Technical approach
4. **Tasks** → Atomic implementation steps
5. **Apply** → Code implementation
6. **Verify** → Tests + Build validation
7. **Commit** → Version control
8. **Archive** → Merge specs to main

## License

Internal use only - OrbisFactory Project
