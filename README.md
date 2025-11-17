# Deep Copy

Deep Copy is a utility for performing **deep copying of objects** in Java.  
The project supports copying arrays, collections, maps, as well as arbitrary user-defined objects, including handling of cyclic references.

---

## ✨ Features
- Deep cloning of any Java objects
- Support for:
    - arrays
    - `Optional`
    - `UUID`
    - collections (`List`, `Set`, `Queue`, `Dequeue`)
    - maps (`Map`, `HashMap`, `TreeMap`, `LinkedHashMap`)
    - objects with nested fields
- Handling of cyclic references using `IdentityHashMap`
- Extensible architecture: you can add custom `CopyHandler` implementations for specific types

---

## ⚙️ Requirements
- Java 21+
- Maven for build

---

## 🚀 Running

To work correctly with private JDK fields, you need to open certain packages of the `java.base` module.  
Run the application with additional JVM arguments:

```bash
java \
  -Djava.base/jdk.internal.reflect=ALL-UNNAMED \
  -Djava.base/java.lang=ALL-UNNAMED \
  -Djava.base/java.util=ALL-UNNAMED \
  -jar target/deep-copy.jar
```

### Running via Maven exec-maven-plugin
You can also run the project directly through Maven using the exec-maven-plugin:

```bash
mvn clean compile exec:java \
-Dexec.mainClass="com.ecwid.deepcopy.Main" \
-Dexec.args="" \
-Dexec.jvmArgs="--add-opens java.base/jdk.internal.reflect=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.util=ALL-UNNAMED"
```

---

## 🧪 Testing
A set of unit tests using JUnit 5 is implemented to verify correctness:
- Copying arrays
- Copying collections and maps
- Copying objects with nested fields
- Handling cyclic references
- Support for Optional and UUID

Run tests:

```bash
mvn test
```