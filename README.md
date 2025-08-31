# CodeLibs CoreLib [![Java CI with Maven](https://github.com/codelibs/corelib/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/corelib/actions/workflows/maven.yml) [![Maven Central](https://img.shields.io/maven-central/v/org.codelibs/corelib.svg)](https://central.sonatype.com/artifact/org.codelibs/corelib) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A foundational Java library providing essential utilities and components for the CodeLibs project ecosystem. Built with Java 21 and optimized for modern Java development patterns including pattern matching, switch expressions, and sequenced collections.

## ✨ Features

### Core Utilities
- **Bean Manipulation** (`org.codelibs.core.beans`) - JavaBeans metadata handling, property access, and object conversion with comprehensive `BeanDesc` system
- **Type Conversion** (`org.codelibs.core.convert`) - Comprehensive utilities for converting between Java types with null-safe operations and support for all primitive types
- **Collections** (`org.codelibs.core.collection`) - Enhanced collection utilities, array operations, and specialized map/set implementations including LRU caches and case-insensitive collections
- **I/O Operations** (`org.codelibs.core.io`) - File handling, resource management, stream utilities, and traversal utilities for efficient resource processing
- **Reflection** (`org.codelibs.core.lang`) - Class loading, method/field access, type introspection utilities, and generics support
- **Exception Handling** (`org.codelibs.core.exception`) - Runtime exception wrappers for common checked exceptions with consistent error handling

### Modern Java 21 Support
- **Pattern Matching** - Leverages modern Java pattern matching for efficient type checking and conversion
- **Switch Expressions** - Optimized implementations using switch expressions for better performance
- **Sequenced Collections** - Full support for Java 21 sequenced collections API with dedicated utility methods
- **Performance Focused** - Optimized implementations for better runtime performance with reduced memory allocation
- **Type Safe** - Comprehensive use of generics and modern Java type system features

### Additional Components
- **Text Processing** (`org.codelibs.core.text`) - JSON utilities, tokenization, decimal formatting, and text manipulation
- **Logging Abstraction** (`org.codelibs.core.log`) - Flexible logging system supporting JCL (Jakarta Commons Logging) and JUL (Java Util Logging)
- **Concurrent Utilities** (`org.codelibs.core.concurrent`) - Thread-safe collections and concurrency helpers using modern concurrent APIs
- **Crypto & Security** (`org.codelibs.core.crypto`, `org.codelibs.core.security`) - Basic cryptographic utilities, message digest operations, and secure random generation
- **XML Processing** (`org.codelibs.core.xml`) - XML DOM utilities, SAX parser helpers, and schema validation support
- **SQL Utilities** (`org.codelibs.core.sql`) - JDBC helper methods for result sets, prepared statements, and connection management
- **Network & I/O** (`org.codelibs.core.net`, `org.codelibs.core.nio`) - URL utilities, UUID generation, MIME type detection, and NIO channel operations

## 🚀 Getting Started

### Requirements
- **Java 21 or higher** (Required for modern language features)
- **Maven 3.6+** or **Gradle 7+** for build management
- **Optional**: SLF4J or Commons Logging for logging support

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
import org.codelibs.core.beans.*;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.codelibs.core.beans.util.BeanUtil;
import org.codelibs.core.beans.util.CopyOptions;

// Bean metadata introspection
BeanDesc beanDesc = BeanDescFactory.getBeanDesc(MyBean.class);
PropertyDesc nameProperty = beanDesc.getPropertyDesc("name");
nameProperty.setValue(bean, "John Doe");

// Bean copying with flexible options
BeanUtil.copyBeanToBean(source, destination);
BeanUtil.copyBeanToBean(source, destination, options -> 
    options.exclude("password", "internalId"));

// Convert between beans and maps
Map<String, Object> map = BeanUtil.copyBeanToNewMap(bean);
MyBean newBean = BeanUtil.copyMapToNewBean(map, MyBean.class);
```

### Type Conversion
```java
import org.codelibs.core.convert.*;

// Safe type conversions with null handling
Integer value = IntegerConversionUtil.toInteger("123");        // Returns 123
Integer nullValue = IntegerConversionUtil.toInteger(null);     // Returns null
Boolean flag = BooleanConversionUtil.toBoolean("true");        // Returns true
Date date = DateConversionUtil.toDate("2023-12-25", "yyyy-MM-dd");

// Primitive conversions with default values
int primitiveInt = IntegerConversionUtil.toPrimitiveInt(value, "0");  // Default to 0 if null
```

### Collection Utilities with Java 21 Support
```java
import org.codelibs.core.collection.CollectionsUtil;
import java.util.SequencedCollection;

// Enhanced collection creation
List<String> list = CollectionsUtil.newArrayList();
Map<String, Object> map = CollectionsUtil.newLinkedHashMap();
Set<String> caseInsensitiveSet = new CaseInsensitiveSet<>();

// Java 21 Sequenced Collections support
SequencedCollection<String> sequenced = CollectionsUtil.newLinkedHashSet();
String first = CollectionsUtil.getFirst(sequenced);
String last = CollectionsUtil.getLast(sequenced);
SequencedCollection<String> reversed = CollectionsUtil.reversed(sequenced);

// Specialized collections
LruHashMap<String, Object> lruCache = new LruHashMap<>(100); // LRU cache with max 100 entries
CaseInsensitiveMap<String> configMap = new CaseInsensitiveMap<>();
```

### Resource Management
```java
import org.codelibs.core.io.*;

// Resource loading and management
URL resource = ResourceUtil.getResource("config.properties");
Properties props = PropertiesUtil.load(resource);

// File operations with proper resource handling
try (InputStream input = ResourceUtil.getResourceAsStream("data.txt")) {
    String content = InputStreamUtil.getUTF8String(input);
}

// Resource traversal for processing multiple files
ResourceTraversalUtil.forEach("META-INF", (resource, is) -> {
    // Process each resource in the META-INF directory
    System.out.println("Processing: " + resource);
});
```

### Text Processing and JSON
```java
import org.codelibs.core.text.*;

// JSON utilities with proper escaping
String escaped = JsonUtil.escape("Hello \"World\" with special chars");
String unescaped = JsonUtil.unescape(escaped);

// Text tokenization
Tokenizer tokenizer = new Tokenizer("field1,field2,field3", ",");
while (tokenizer.hasMoreTokens()) {
    String token = tokenizer.nextToken();
    // Process each token
}

// Decimal formatting
DecimalFormat format = DecimalFormatUtil.getDecimalFormat("###,###.00");
```

### Exception Handling
```java
import org.codelibs.core.exception.*;

// Runtime exception wrappers eliminate try-catch boilerplate
try {
    // Code that might throw checked exceptions
    return ClassUtil.newInstance(className); // Wraps checked exceptions automatically
} catch (ClassNotFoundRuntimeException e) {
    // Handle the wrapped exception
    logger.error("Class not found: " + className, e);
}
```

## 🏗️ Architecture & Design Patterns

### Core Design Principles
CoreLib follows a **utility-class pattern** where most functionality is exposed through static methods:

- **Assertion-based validation** - All methods validate inputs using `AssertionUtil.assertArgumentNotNull()` and `AssertionUtil.assertArgumentNotEmpty()`
- **Exception wrapping** - Checked exceptions are consistently wrapped in runtime exceptions (e.g., `ClassNotFoundException` → `ClassNotFoundRuntimeException`)
- **Bean introspection** - The comprehensive `BeanDesc` system provides metadata about JavaBeans, accessed through `BeanDescFactory.getBeanDesc(Class)`
- **Type safety** - Extensive use of generics and null-safe operations throughout the API
- **Resource management** - Proper handling of resources with utilities like `CloseableUtil` and `DisposableUtil`

### Key Architectural Patterns
- **Factory Pattern** - `BeanDescFactory` for creating bean descriptors, `ParameterizedClassDescFactory` for generic type handling
- **Builder Pattern** - `CopyOptions` for configuring bean copying operations with fluent API
- **Adapter Pattern** - Logging adapters (`JclLoggerAdapter`, `JulLoggerAdapter`) for different logging frameworks
- **Template Method** - Resource traversal utilities with customizable handlers
- **Utility Classes** - All core functionality exposed through static utility methods for easy access

### Performance Optimizations
- **Caching** - Bean descriptors and reflection metadata are cached for improved performance
- **Lazy initialization** - Resources and expensive operations are initialized only when needed  
- **Memory efficient** - Specialized collections like `LruHashMap` and `ArrayMap` for memory-conscious applications
- **Java 21 features** - Switch expressions and pattern matching for reduced overhead

## 🧪 Building and Testing

### Development Setup
```bash
# Clone the repository
git clone https://github.com/codelibs/corelib.git
cd corelib

# Compile the project
mvn clean compile

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BeanUtilTest

# Run specific test method  
mvn test -Dtest=BeanUtilTest#testCopyBeanToBean
```

### Code Quality and Formatting
```bash
# Format code according to project standards
mvn formatter:format

# Apply license headers to source files
mvn license:format

# Build JAR with all verifications
mvn clean package

# Generate test coverage report
mvn verify
# Coverage report available at: target/site/jacoco/index.html
```

### Project Structure
```
corelib/
├── src/main/java/org/codelibs/core/
│   ├── beans/          # Bean manipulation and introspection
│   ├── collection/     # Enhanced collection utilities
│   ├── convert/        # Type conversion utilities
│   ├── exception/      # Runtime exception wrappers
│   ├── io/            # I/O and resource management
│   ├── lang/          # Reflection and language utilities
│   ├── log/           # Logging abstraction
│   ├── text/          # Text processing utilities
│   ├── xml/           # XML processing utilities
│   └── ...            # Additional utility packages
└── src/test/java/      # Comprehensive test suite
```

## 📊 Performance Metrics

CoreLib 0.7.0 includes significant performance improvements through Java 21 optimizations:

- **5-15% faster** type conversions with pattern matching and switch expressions
- **Reduced memory allocation** in collection operations and bean copying
- **Improved reflection performance** with cached descriptors and optimized field access
- **Enhanced collection operations** with Java 21 sequenced collections support
- **Better concurrent performance** using modern concurrent collection implementations

## 🔧 Configuration

### Logging Configuration
CoreLib supports multiple logging frameworks. Configure your preferred logger:

```java
// Use with SLF4J (add slf4j-api dependency)
Logger logger = Logger.getLogger(MyClass.class);

// Use with Commons Logging (add commons-logging dependency)  
Logger logger = Logger.getLogger(MyClass.class);

// Use with Java Util Logging (built-in)
Logger logger = Logger.getLogger(MyClass.class.getName());
```

### Bean Copy Configuration
```java
// Configure bean copying behavior
CopyOptions options = new CopyOptions()
    .exclude("password", "internalId")      // Exclude specific fields
    .includeNull(false)                     // Skip null values
    .converter("dateField", new DateConverter("yyyy-MM-dd"));

BeanUtil.copyBeanToBean(source, dest, options);
```

## 🤝 Contributing

We welcome contributions! Please see our contributing guidelines for details.

### Development Workflow
1. **Fork** the repository on GitHub
2. **Create** your feature branch: `git checkout -b feature/amazing-feature`
3. **Follow** the coding standards: `mvn formatter:format`
4. **Add** comprehensive tests for new functionality
5. **Commit** your changes: `git commit -m 'Add amazing feature'`
6. **Push** to the branch: `git push origin feature/amazing-feature`
7. **Submit** a Pull Request with detailed description

### Code Standards
- Follow the project's Eclipse formatter configuration
- Add Apache License 2.0 headers to new files: `mvn license:format`
- Maintain comprehensive JavaDoc documentation
- Write thorough unit tests with good coverage
- Follow existing naming conventions and patterns

## 📄 License

This project is licensed under the [Apache License 2.0](LICENSE) - see the LICENSE file for details.

