`ext-gson` is a simple extension library for [Google Gson 2.8.2](https://github.com/google/gson) and later including a few extensions for the following libraries:

* [Jayway JsonPath 2.4.0](https://github.com/json-path/JsonPath)
* [Google Guava 24.1 for JRE](https://github.com/google/guava)
* [JSR 374 (JSON Processing) API](https://javaee.github.io/jsonp)
* [JSR 374 (JSON Processing) Default Provider](https://javaee.github.io/jsonp)

New libraries might be added someday.
Maybe.

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

* Specialized type adapters and factories:
  * `OptionalTypeAdapter` and `OptionalTypeAdapterFactory` - type adapter to support `java.util.Optional`;

##### Java 8 Time API

* Specialized type adapters and factories:
  * `DayOfWeekTypeAdapterFactory` and `DayOfWeekTypeAdapterFactory` - type adapter to support `java.time.DayOfWeek`;
  * `DurationTypeAdapterFactory` and `DurationTypeAdapterFactory` - type adapter to support `java.time.Duration`;
  * `InstantTypeAdapterFactory` and `InstantTypeAdapterFactory` - type adapter to support `java.time.Instant`;
  * `LocalDateTimeTypeAdapterFactory` and `LocalDateTimeTypeAdapterFactory` - type adapter to support `java.time.LocalDateTime`;
  * `LocalDateTypeAdapterFactory` and `LocalDateTypeAdapterFactory` - type adapter to support `java.time.LocalDate`;
  * `LocalTimeTypeAdapterFactory` and `LocalTimeTypeAdapterFactory` - type adapter to support `java.time.LocalTime`;
  * `MonthDayTypeAdapterFactory` and `MonthDayTypeAdapterFactory` - type adapter to support `java.time.MonthDay`;
  * `MonthTypeAdapterFactory` and `MonthTypeAdapterFactory` - type adapter to support `java.time.Month`;
  * `OffsetDateTimeTypeAdapterFactory` and `OffsetDateTimeTypeAdapterFactory` - type adapter to support `java.time.OffsetDateTime`;
  * `OffsetTimeTypeAdapterFactory` and `OffsetTimeTypeAdapterFactory` - type adapter to support `java.time.OffsetTime`;
  * `PeriodTypeAdapterFactory` and `PeriodTypeAdapterFactory` - type adapter to support `java.time.Period`;
  * `YearMonthTypeAdapterFactory` and `YearMonthTypeAdapterFactory` - type adapter to support `java.time.YearMonth`;
  * `YearTypeAdapterFactory` and `YearTypeAdapterFactory` - type adapter to support `java.time.Year`;
  * `ZonedDateTimeTypeAdapterFactory` and `ZonedDateTimeTypeAdapterFactory` - type adapter to support `java.time.ZonedDateTime`.

#### Java JSON API

* Specialized type adapters and factories:
  * `JsonValueTypeAdapter` - type adapter to support `javax.json.JsonValue`.

#### Google Guava

* Specialized type adapters and factories:
  * `OptionalTypeAdapter` and `OptionalTypeAdapterFactory` - type adapter to support `com.google.common.base.Optional`;
  * `MultisetTypeAdapter` and `MultisetTypeAdapterFactory` - type adapter to support `com.google.common.collect.Multiset`.
  * `MultimapTypeAdapter` and `MultimapTypeAdapterFactory` - type adapter to support `com.google.common.collect.Multimap`.

### Development

![Travis CI](https://travis-ci.org/lyubomyr-shaydariv/ext-gson.svg?branch=master)
