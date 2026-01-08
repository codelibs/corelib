# CLAUDE.md - AI Assistant Guide for CodeLibs CoreLib

This document provides guidance for AI assistants working with the CodeLibs CoreLib codebase.

## Project Overview

**CodeLibs CoreLib** is a foundational Java utility library providing essential utilities and components for the CodeLibs project ecosystem. It is built with **Java 21** and optimized for modern Java development patterns.

- **Group ID**: `org.codelibs`
- **Artifact ID**: `corelib`
- **Current Version**: `0.7.2-SNAPSHOT`
- **License**: Apache License 2.0
- **Build System**: Maven

## Repository Structure

```
corelib/
├── pom.xml                           # Maven build configuration
├── src/
│   ├── main/java/org/codelibs/core/  # Main source code
│   │   ├── CoreLibConstants.java     # Core constants (UTF-8, date formats)
│   │   ├── beans/                    # Bean manipulation and introspection
│   │   │   ├── converter/            # Type converters for beans
│   │   │   ├── factory/              # BeanDescFactory, ParameterizedClassDescFactory
│   │   │   ├── impl/                 # Implementation classes
│   │   │   └── util/                 # BeanUtil, BeanMap, CopyOptions
│   │   ├── collection/               # Enhanced collections (LruHashMap, CaseInsensitiveMap)
│   │   ├── concurrent/               # Concurrency utilities
│   │   ├── convert/                  # Type conversion utilities (*ConversionUtil)
│   │   ├── crypto/                   # Cryptographic utilities (CachedCipher)
│   │   ├── exception/                # Runtime exception wrappers
│   │   ├── io/                       # I/O and resource utilities
│   │   ├── jar/                      # JAR file utilities
│   │   ├── lang/                     # Reflection and language utilities
│   │   ├── log/                      # Logging abstraction
│   │   ├── message/                  # Message formatting
│   │   ├── misc/                     # Miscellaneous (AssertionUtil, Base64Util)
│   │   ├── naming/                   # JNDI naming utilities
│   │   ├── net/                      # Network utilities (URL, UUID, MIME)
│   │   ├── nio/                      # NIO channel operations
│   │   ├── security/                 # Security utilities (MessageDigest)
│   │   ├── sql/                      # JDBC helper utilities
│   │   ├── stream/                   # Stream utilities
│   │   ├── text/                     # Text processing (JSON, Tokenizer)
│   │   ├── timer/                    # Timer and timeout management
│   │   ├── xml/                      # XML processing utilities
│   │   └── zip/                      # ZIP file utilities
│   └── test/java/                    # Test classes (mirrors main structure)
├── .github/workflows/maven.yml       # CI/CD configuration
└── LICENSE                           # Apache 2.0 License
```

## Development Commands

### Build and Test

```bash
# Compile the project
mvn clean compile

# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=BeanUtilTest

# Run a specific test method
mvn test -Dtest=BeanUtilTest#testCopyBeanToBean

# Build JAR with all verifications
mvn clean package

# Generate test coverage report (available at target/site/jacoco/index.html)
mvn verify
```

### Code Quality

```bash
# Format code according to project standards (Eclipse formatter)
mvn formatter:format

# Apply license headers to source files
mvn license:format

# Full build with formatting
mvn clean package
```

## Code Conventions

### Class Structure

1. **Utility Classes**: Abstract classes with protected constructors to prevent instantiation
   ```java
   public abstract class StringUtil {
       protected StringUtil() {
       }
       // Static methods only
   }
   ```

2. **Constants Classes**: Same pattern with protected constructor
   ```java
   public class CoreLibConstants {
       protected CoreLibConstants() {
       }
       public static final String UTF_8 = "UTF-8";
   }
   ```

### License Header

All source files MUST include the Apache 2.0 license header:
```java
/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * ...
 */
```

### Argument Validation

Always validate arguments using `AssertionUtil` at method entry:
```java
public static void process(String value, Object obj) {
    AssertionUtil.assertArgumentNotNull("value", value);
    AssertionUtil.assertArgumentNotEmpty("obj", obj);
    // Method logic
}
```

### Exception Handling

- Wrap checked exceptions in runtime exceptions from `org.codelibs.core.exception`
- Exception classes use error codes (e.g., "ECL0008" for null argument)
- Example pattern:
  ```java
  // Instead of throwing checked exceptions
  throw new NullArgumentException("paramName");  // Uses ECL0008
  throw new EmptyArgumentException("paramName", "ECL0010", asArray("paramName"));
  ```

### JavaDoc Style

- All public methods must have JavaDoc
- Include `@param`, `@return`, and `@throws` tags
- Use `{@code null}` for null references
- Use `{@literal true/false}` for boolean values
- Include usage examples where helpful:
  ```java
  /**
   * Capitalizes a string according to JavaBeans conventions.
   * <p>
   * Usage example:
   * </p>
   * <pre>
   * StringUtil.capitalize("userId")  = "UserId"
   * </pre>
   *
   * @param name the string to capitalize
   * @return the capitalized string
   */
  ```

### Test Structure

- Tests use JUnit 4 with Hamcrest matchers
- Test class naming: `{ClassName}Test.java`
- Test method naming: `test{MethodName}` or descriptive names
- Use `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertThat`
- Each test method should have a JavaDoc comment

```java
public class StringUtilTest {
    /**
     * @throws Exception
     */
    @Test
    public void testReplace() throws Exception {
        assertEquals("1", "1bc45", StringUtil.replace("12345", "23", "bc"));
    }
}
```

## Key Design Patterns

### Factory Pattern
- `BeanDescFactory.getBeanDesc(Class<?>)` - Creates/caches bean descriptors
- `ParameterizedClassDescFactory` - Handles generic type information

### Builder Pattern
- `CopyOptions` - Fluent configuration for bean copying operations

### Utility Pattern
- All core functionality via static methods
- Null-safe operations throughout
- Methods return null for null inputs where appropriate

### Exception Wrapping
- `ClassNotFoundException` → `ClassNotFoundRuntimeException`
- `IOException` → `IORuntimeException`
- Consistent error handling without checked exception pollution

## Important Notes for AI Assistants

### When Making Changes

1. **Always run tests** after modifications: `mvn test`
2. **Format code** before committing: `mvn formatter:format`
3. **Apply license headers** to new files: `mvn license:format`
4. **Maintain backwards compatibility** - deprecate before removing
5. **Follow existing patterns** - study similar classes before adding new ones

### Naming Conventions

- Utility classes: `{Feature}Util.java` (e.g., `StringUtil`, `ArrayUtil`)
- Conversion classes: `{Type}ConversionUtil.java` (e.g., `IntegerConversionUtil`)
- Exception classes: `{Name}RuntimeException.java` or `Cl{Name}Exception.java`
- Interface implementations: `{Interface}Impl.java`

### Package Organization

- Place new utilities in the appropriate existing package
- Create new packages only when absolutely necessary
- Test classes mirror the main source structure exactly

### Dependencies

The project has minimal dependencies (all optional except for testing):
- `slf4j-api` (optional) - Logging facade
- `commons-logging` (optional) - Logging facade
- `junit` (test scope) - Testing framework

### Java 21 Features

The codebase leverages Java 21 features:
- Pattern matching in switch expressions
- Sequenced collections support
- Modern stream operations
- Enhanced type inference

### CI/CD

- GitHub Actions runs on push/PR to `master` and `*.x` branches
- Uses Temurin JDK 21
- Runs `mvn -B package`

## Quick Reference

| Task | Command |
|------|---------|
| Build | `mvn clean package` |
| Test | `mvn test` |
| Format code | `mvn formatter:format` |
| Add license headers | `mvn license:format` |
| Generate coverage | `mvn verify` |
| Run single test | `mvn test -Dtest=ClassName#methodName` |
