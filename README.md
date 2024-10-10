### Hexlet tests and linter status:

[![Actions Status](https://github.com/VIIIunknownVIII/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/VIIIunknownVIII/java-project-78/actions)
[![Java CI](https://github.com/VIIIunknownVIII/java-project-78/actions/workflows/gradle.yml/badge.svg)](https://github.com/VIIIunknownVIII/java-project-78/actions/workflows/gradle.yml)
<a href="https://codeclimate.com/github/VIIIunknownVIII/java-project-78/maintainability"><img src="https://api.codeclimate.com/v1/badges/c5e5680b551cb43b27cd/maintainability" /></a>

## Валидация объектов с использованием `Validator`

### Метод `shape()`

Метод `shape()` используется для определения свойств объекта `Map` и создания схемы для валидации их значений. Каждому свойству объекта `Map` привязывается свой набор ограничений (своя схема), что позволяет более точно контролировать данные:

```java
import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

var v = new Validator();

var schema = v.map();


// Проверяем объекты
Map<String, String> human1 = new HashMap<>();
human1.put("firstName", "John");
human1.put("lastName", "Smith");
schema.isValid(human1); // true

Map<String, String> human2 = new HashMap<>();
human2.put("firstName", "John");
human2.put("lastName", null);
schema.isValid(human2); // false

Map<String, String> human3 = new HashMap<>();
human3.put("firstName", "Anna");
human3.put("lastName", "B");
schema.isValid(human3); // false
