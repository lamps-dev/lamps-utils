# Lamp's Utils [![](https://jitpack.io/v/lamps-dev/lamps-utils.svg)](https://jitpack.io/#lamps-dev/lamps-utils)

A lightweight utility and library mod for Fabric (Minecraft 1.21.11).

## Features

- **Error Block** — A decorative/utility block registered under `lamps-utils:error_block`
- **HTTP Command** — `/http get <url>` fetches data from a URL and displays it in chat, with configurable safety warnings and text length limits
- **Config System** — In-game configuration via ModMenu powered by OWO-Lib

## Configuration

| Option | Default | Description |
|--------|---------|-------------|
| `showHttpWarning` | `true` | Show a security warning when using the HTTP command |
| `maxTextLimit` | `300` | Max characters displayed from HTTP responses (100–5000) |

## Using as a Library

Lamp's Utils is available via [JitPack](https://jitpack.io/#lamps-dev/lamps-utils).

Add the following to your `build.gradle`:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation 'com.github.lamps-dev:lamps-utils:v1.5.0'
}
```

### Available APIs

**Block Registration** (`ModBlocks`)
```java
// Register blocks with automatic item registration
ModBlocks.register("my_block", Block::new, properties, true);

// Access built-in blocks
ModBlocks.ERROR_BLOCK
```

**Configuration** (`ConfigManager`)
```java
// Access config values
ConfigManager.CONFIG.showHttpWarning();
ConfigManager.CONFIG.maxTextLimit();
```

**Command Registration** (`ModCommands`)
```java
// Commands are registered via Fabric's command event
ModCommands.register(dispatcher);
```

## Dependencies

| Dependency | Purpose |
|------------|---------|
| [Fabric API](https://modrinth.com/mod/fabric-api) | Required — Core Fabric hooks |
| [OWO-Lib](https://modrinth.com/mod/owo-lib) | Required — Configuration system |
| [ModMenu](https://modrinth.com/mod/modmenu) | Optional — In-game config GUI |
| [OkHttp3](https://square.github.io/okhttp/) | Bundled — HTTP client for `/http` command |

## Building from Source

```bash
git clone https://github.com/lamps-dev/lamps-utils.git
cd lamps-utils
./gradlew build
```

The built jar will be in `build/libs/`.

## License

[CC0-1.0](LICENSE) — Public Domain
