`ext-gson` is a simple extension library for [Google Gson](https://github.com/google/gson) (2.8.x, 2.9.0, and later) including a few extensions for the following libraries:

* [Jayway JsonPath](https://github.com/json-path/JsonPath)
* [Google Guava for JRE](https://github.com/google/guava)
* [JSR 374 (JSON Processing) API](https://javaee.github.io/jsonp)
* [JSR 374 (JSON Processing) Default Provider](https://javaee.github.io/jsonp)

New libraries might be added someday.
Maybe.

![Travis CI](https://travis-ci.org/lyubomyr-shaydariv/ext-gson.svg?branch=master)

### Requirements

* Build: Java 8 and Apache Maven.
* Use: Java 6, Java 7 or Java 8 depending on components.

No `ext-gson` dependencies are bundled or planned to be bundled, so all dependencies are supposed to be added with the `provided` scope.

### What's introduced

Currently `ext-gson` supports:

* Minor utility methods for `JsonReader`s and `JsonWriter`s.
* `JsonElement` static factory methods and builders, `JsonElement` merge methods.
* Parameterized type arguments parsers and simple collections/maps type factory methods.
* Specialized type adapters and factories:
  * `AbstractBoundTypeAdapterFactory` - a type adapter factory super class bound to a specific type getting rid of `@SuppressWarnings("unchecked")` boilerplates;
  * `AlwaysListTypeAdapter` and `AlwaysListTypeAdapterFactory` - a type adapter to "align" single element object and multiple elements objects array to `java.util.List`;
  * `EpochDateTypeAdapter` - UNIX epoch timestamps type adapter;
  * `IteratorTypeAdapter` and `IteratorTypeAdapterFactory` - type adapter to support `java.util.Iterator` allowing to stream elements;
  * `JsonPathTypeAdapterFactory` - type adapter factory to enable `@JsonPathExpression` support (requires JsonPath dependency);
  * `PackedJsonTypeAdapter` - a type adapter to "pack" and "unpack" JSON to and from string respectively; 
  * `ReadOnlyTypeAdapter` - a type adapter with disabled write operation;
  * `TypeAwareTypeAdapter` and `AbstractTypeTypeAdapterFactory` - a type adapter to keep abstract classes instances class names directly in JSON.
  
#### Java 8

* Types provided by `Java8Module`
  * [`java.util.Optional`](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html);
* Types provided by `Java8TimeModule`:
  * [`java.time.DayOfWeek`](https://docs.oracle.com/javase/8/docs/api/java/time/DayOfWeek.html);
  * [`java.time.Duration`](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html);
  * [`java.time.Instant`](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html);
  * [`java.time.LocalDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html);
  * [`java.time.LocalDate`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html);
  * [`java.time.LocalTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html);
  * [`java.time.MonthDay`](https://docs.oracle.com/javase/8/docs/api/java/time/MonthDay.html);
  * [`java.time.Month`](https://docs.oracle.com/javase/8/docs/api/java/time/Month.html);
  * [`java.time.OffsetDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/OffsetDateTime.html);
  * [`java.time.OffsetTime`](https://docs.oracle.com/javase/8/docs/api/java/time/OffsetTime.html);
  * [`java.time.Period`](https://docs.oracle.com/javase/8/docs/api/java/time/Period.html);
  * [`java.time.YearMonth`](https://docs.oracle.com/javase/8/docs/api/java/time/YearMonth.html);
  * [`java.time.Year`](https://docs.oracle.com/javase/8/docs/api/java/time/Year.html);
  * [`java.time.ZonedDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/ZonedDateTime.html).

#### Java 16

* Types provided by `Java16Module`
  * [`java.lang.Record`](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/Record.html).

#### Java JSON API

* Types provided by `JsonApiModule`:
  * [`javax.json.JsonValue`](https://docs.oracle.com/javaee/7/api/javax/json/JsonValue.html).

#### Google Guava

* Types provided by `GuavaModule`:
  * [`com.google.common.base.Optional`](https://google.github.io/guava/releases/30.1.1-jre/api/docs/com/google/common/base/Optional.html);
  * [`com.google.common.collect.BiMap`](https://google.github.io/guava/releases/30.1.1-jre/api/docs/com/google/common/collect/BiMap.html);
  * [`com.google.common.collect.Multimap`](https://google.github.io/guava/releases/30.1.1-jre/api/docs/com/google/common/collect/Multimap.html);
  * [`com.google.common.collect.Multiset`](https://google.github.io/guava/releases/30.1.1-jre/api/docs/com/google/common/collect/Multiset.html);
  * [`com.google.common.collect.Table`](https://google.github.io/guava/releases/30.1.1-jre/api/docs/com/google/common/collect/Table.html).
