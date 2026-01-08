# CLAUDE.md - AI Assistant Guide for CodeLibs CoreLib

Java 21 utility library. Maven build system, Apache License 2.0.

## Repository Structure

```
src/main/java/org/codelibs/core/
├── beans/       # Bean manipulation & introspection (converter/, factory/, impl/, util/)
├── collection/  # Enhanced collections (LruHashMap, CaseInsensitiveMap)
├── convert/     # Type conversion (*ConversionUtil)
├── exception/   # Runtime exception wrappers
├── io/          # I/O & resource utilities
├── lang/        # Reflection & language utilities
├── misc/        # AssertionUtil, Base64Util, etc.
├── text/        # Text processing (JSON, Tokenizer)
└── ...          # Others (crypto, log, net, xml, zip, etc.)
src/test/java/   # Test classes (mirrors main structure)
```

## Development Commands

```bash
mvn test                              # Run tests
mvn test -Dtest=ClassName#methodName  # Run specific test
mvn clean package                     # Build
mvn formatter:format                  # Format code
mvn license:format                    # Apply license headers
mvn verify                            # Generate coverage report
```

## Code Conventions

### Class Structure

- **Utility classes**: `abstract` class + `protected` constructor
- **Constants classes**: Same pattern

### Argument Validation

Use `AssertionUtil` at method entry:
- `AssertionUtil.assertArgumentNotNull("argName", value)`
- `AssertionUtil.assertArgumentNotEmpty("argName", value)`

### Exception Handling

- Wrap checked exceptions in runtime exceptions from `org.codelibs.core.exception`
- Use error codes (e.g., "ECL0008" = null argument)

### Test Structure

- JUnit 4 + Hamcrest matchers
- Test class: `{ClassName}Test.java`
- Test method: `test{MethodName}`

## Naming Conventions

| Type | Pattern |
|------|---------|
| Utility | `{Feature}Util.java` |
| Conversion | `{Type}ConversionUtil.java` |
| Exception | `{Name}RuntimeException.java` / `Cl{Name}Exception.java` |
| Implementation | `{Interface}Impl.java` |

## Important Notes

1. Always run `mvn test` after changes
2. Run `mvn formatter:format` before committing
3. Add license headers to new files with `mvn license:format`
4. Maintain backwards compatibility - deprecate before removing
5. Follow existing patterns - study similar classes first
