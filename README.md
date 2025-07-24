# CodeLibs CoreLib [![Java CI with Maven](https://github.com/codelibs/corelib/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/corelib/actions/workflows/maven.yml) [![Maven Central](https://img.shields.io/maven-central/v/org.codelibs/corelib.svg)](https://central.sonatype.com/artifact/org.codelibs/corelib) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A foundational Java library providing essential utilities and components for the CodeLibs project ecosystem. Built with Java 21 and optimized for modern Java development.

## ✨ Features

### Core Utilities
- **Bean Manipulation** - JavaBeans metadata handling, property access, and object conversion
- **Type Conversion** - Comprehensive utilities for converting between Java types with null-safe operations
- **Collections** - Enhanced collection utilities, array operations, and specialized map/set implementations
- **I/O Operations** - File handling, resource management, and stream utilities
- **Reflection** - Class loading, method/field access, and type introspection utilities
- **Exception Handling** - Runtime exception wrappers for common checked exceptions

### Modern Java Support
- **Java 21 Optimized** - Leverages pattern matching, switch expressions, and sequenced collections
- **Performance Focused** - Optimized implementations for better runtime performance
- **Type Safe** - Comprehensive use of generics and modern Java type system features

### Additional Components
- **Text Processing** - JSON utilities, tokenization, and formatting
- **Logging Abstraction** - Support for multiple logging frameworks (JCL, JUL)
- **Concurrent Utilities** - Thread-safe collections and concurrency helpers
- **Crypto Support** - Basic cryptographic utilities and message digest operations

## 🚀 Getting Started

### Requirements
- Java 21 or higher
- Maven 3.6+ or Gradle 7+

### Maven Dependency
```xml
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>corelib</artifactId>
    <version>0.7.0</version>
</dependency>
```

### Gradle Dependency
```gradle
implementation 'org.codelibs:corelib:0.7.0'
```

## 📖 Usage Examples

### Bean Utilities
```java
// Bean property access and conversion
BeanDesc beanDesc = BeanDescFactory.getBeanDesc(MyBean.class);
PropertyDesc prop = beanDesc.getPropertyDesc("name");
prop.setValue(bean, "value");

// Bean copying with options
BeanUtil.copyProperties(source, dest, CopyOptions.exclude("password"));
```

### Type Conversion
```java
// Safe type conversions
Integer value = IntegerConversionUtil.toInteger("123");
Boolean flag = BooleanConversionUtil.toBoolean("true");
String json = JsonUtil.escape("Hello \"World\"");
```

### Collection Utilities
```java
// Enhanced collection operations
List<String> list = CollectionsUtil.newArrayList();
Map<String, Object> map = CollectionsUtil.newLinkedHashMap();

// Java 21 Sequenced Collections support
String first = CollectionsUtil.getFirst(sequencedList);
String last = CollectionsUtil.getLast(sequencedList);
```

### Resource Management
```java
// Resource loading and management
URL resource = ResourceUtil.getResource("config.properties");
Properties props = PropertiesUtil.load(resource);
```

## 🏗️ Architecture

CoreLib follows a utility-class pattern where most functionality is exposed through static methods:

- **Assertion-based validation** - All methods validate inputs using `AssertionUtil`
- **Exception wrapping** - Checked exceptions are wrapped in runtime exceptions
- **Bean introspection** - Comprehensive metadata system via `BeanDesc`
- **Type safety** - Extensive use of generics and null-safe operations

## 🧪 Building and Testing

```bash
# Compile the project
mvn clean compile

# Run tests
mvn test

# Build JAR
mvn package

# Format code
mvn formatter:format

# Apply license headers
mvn license:format
```

## 📊 Performance

CoreLib 0.7.0 includes significant performance improvements through Java 21 optimizations:

- **5-15% faster** type conversions with pattern matching
- **Reduced memory allocation** through switch expressions
- **Improved collection operations** with sequenced collections support

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the [Apache License 2.0](LICENSE) - see the LICENSE file for details.

## 🔗 Links

- [CodeLibs Website](https://codelibs.org)
- [API Documentation](https://javadoc.io/doc/org.codelibs/corelib)
- [Maven Central Repository](https://central.sonatype.com/artifact/org.codelibs/corelib)
- [Issue Tracker](https://github.com/codelibs/corelib/issues)
- [Discussions](https://github.com/codelibs/corelib/discussions)

## 🏢 About CodeLibs

CodeLibs is an open-source project that provides Java libraries and tools for enterprise application development. Visit [codelibs.org](https://codelibs.org) to learn more about our other projects.

---

**Supported by**: Java 21+ | Maven Central | Apache License 2.0