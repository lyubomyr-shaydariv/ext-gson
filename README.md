`ext-gson` is a simple extension library for [Google Gson](https://github.com/google/gson) including a few extensions for the following libraries:

* [JSR 374 (JSON Processing) API](https://javaee.github.io/jsonp) and [JSR 374 (JSON Processing) Default Provider](https://javaee.github.io/jsonp)
* [Jayway JsonPath](https://github.com/json-path/JsonPath)
* [Google Guava for JRE](https://github.com/google/guava)

New libraries support might be added someday.
Maybe.

### Dependencies

No `ext-gson` dependencies are bundled or planned to be bundled, so all dependencies are supposed to be added with the `provided` scope.

### What's introduced

#### Java 8

* [`java.time.DayOfWeek`](https://docs.oracle.com/javase/8/docs/api/java/time/DayOfWeek.html)
* [`java.time.Duration`](https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html)
* [`java.time.Instant`](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)
* [`java.time.LocalDate`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [`java.time.LocalDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html)
* [`java.time.LocalTime`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html)
* [`java.time.Month`](https://docs.oracle.com/javase/8/docs/api/java/time/Month.html)
* [`java.time.MonthDay`](https://docs.oracle.com/javase/8/docs/api/java/time/MonthDay.html)
* [`java.time.OffsetDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/OffsetDateTime.html)
* [`java.time.OffsetTime`](https://docs.oracle.com/javase/8/docs/api/java/time/OffsetTime.html)
* [`java.time.Period`](https://docs.oracle.com/javase/8/docs/api/java/time/Period.html)
* [`java.time.Year`](https://docs.oracle.com/javase/8/docs/api/java/time/Year.html)
* [`java.time.YearMonth`](https://docs.oracle.com/javase/8/docs/api/java/time/YearMonth.html)
* [`java.time.ZonedDateTime`](https://docs.oracle.com/javase/8/docs/api/java/time/ZonedDateTime.html)
* [`java.util.Optional`](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [`java.util.stream.Stream`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)

#### Java 16

* [`java.lang.Record`](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/Record.html)

#### Java JSON API

* [`javax.json.JsonValue`](https://docs.oracle.com/javaee/7/api/javax/json/JsonValue.html)
* [`jakarta.json.JsonValue`](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/json/jsonvalue)

#### Google Guava

* [`com.google.common.base.Optional`](https://github.com/google/guava/blob/master/android/guava/src/com/google/common/base/Optional.java)
* [`com.google.common.collect.BiMap`](https://github.com/google/guava/blob/master/android/guava/src/com/google/common/collect/BiMap.java)
* [`com.google.common.collect.Multimap`](https://github.com/google/guava/blob/master/android/guava/src/com/google/common/collect/Multimap.java)
* [`com.google.common.collect.Multiset`](https://github.com/google/guava/blob/master/android/guava/src/com/google/common/collect/Multiset.java)
* [`com.google.common.collect.Table`](https://github.com/google/guava/blob/master/android/guava/src/com/google/common/collect/Table.java)
