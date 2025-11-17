# Deep Clone

Deep Clone — это утилита для выполнения **глубокого копирования объектов** в Java.  
Проект поддерживает копирование массивов, коллекций, карт, а также произвольных пользовательских объектов с учётом циклических ссылок.

---

## ✨ Возможности
- Глубокое копирование любых Java‑объектов
- Поддержка:
    - массивов
    - `Optional` 
    - `UUID`
    - коллекций (`List`, `Set`, `Queue`)
    - карт (`Map`, `HashMap`, `TreeMap`, `LinkedHashMap`)
    - объектов с вложенными полями
- Обработка циклических ссылок через `IdentityHashMap`
- Расширяемая архитектура: можно добавлять собственные `CopyHandler` для специфических типов

---

## ⚙️ Требования
- Java 21+ 
- Maven для сборки

---

## 🚀 Запуск

Для корректной работы с приватными полями JDK необходимо открыть некоторые пакеты модуля `java.base`.  
Запускать приложение нужно с дополнительными аргументами JVM:

```bash
java \
  -Djava.base/jdk.internal.reflect=ALL-UNNAMED \
  -Djava.base/java.lang=ALL-UNNAMED \
  -Djava.base/java.util=ALL-UNNAMED \
  -jar target/deep-clone.jar
```

### Запуск через Maven exec-maven-plugin
Также можно запускать проект напрямую через Maven, используя плагин exec-maven-plugin:

```bash
mvn clean compile exec:java \
-Dexec.mainClass="com.ecwid.deepcopy.Main" \
-Dexec.args="" \
-Dexec.jvmArgs="--add-opens java.base/jdk.internal.reflect=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.util=ALL-UNNAMED"
```

## 🧪 Тестирование

Для проверки корректности работы реализован набор тестов на JUnit 5:
  - Копирование массивов
  - Копирование коллекций и карт
  - Копирование объектов с вложенными полями
  - Проверка циклических ссылок
  - Поддержка Optional и UUID

Запуск тестов:

```bash
mvn test
```