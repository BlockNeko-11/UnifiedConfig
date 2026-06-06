# Unified Config

> [!IMPORTANT]
> The library has been refactored since 2.0.0.

A configuration library for Java.

## Features
### Support
| Provider  | Module                     | Supported formats | Link                                                   | Note                                                                          |
|-----------|----------------------------|-------------------|--------------------------------------------------------|-------------------------------------------------------------------------------|
| Gson      | [`gson`](./gson)           | json              | [GitHub](https://github.com/google/gson)               | -                                                                             |
| Jackson   | [`jackson`](./jackson)     | json, yaml        | [GitHub](https://github.com/FasterXML/jackson)         | Only json, yaml is tested that works.                                         |
| SnakeYaml | [`snakeyaml`](./snakeyaml) | yaml              | [BitBucket](https://bitbucket.org/snakeyaml/snakeyaml) | -                                                                             |
| Toml4J    | [`toml4j`](./toml4j)       | toml              | [GitHub](https://github.com/mwanji/toml4j)             | Not support TOML spec version higher than [v0.4.0](https://toml.io/en/v0.4.0) |

## Requirements
- Java 8+

## Installation (For developers)
Latest maven version:
[![](https://jitpack.io/v/BlockNeko-11/UnifiedConfig.svg)](https://jitpack.io/#BlockNeko-11/UnifiedConfig)

### Add repository
In your `build.gradle`:
```gradle
repositories {
    maven {
        url = "https://jitpack.io/"
    }
}
```

### Add dependency
In your `build.gradle`:
```gradle
dependencies {
    // replace [PROJECT] with the project name u want
    
    implementation "com.github.BlockNeko-11.UnifiedConfig:unified-config-[PROJECT]:${rootProject.unified_config_version}"
}
```

## Build
1. Clone this repository
2. run `./gradlew build` in the project directory
