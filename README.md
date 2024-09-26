`ext-gson` is a simple extension library for [Google Gson](https://github.com/google/gson) including a few extensions for the following libraries:

### Dependencies

No `ext-gson` dependencies are bundled or planned to be bundled, so all dependencies are referenced with the `provided` scope.

### What's introduced

#### Java pre-8

* `java.util.Enumeration`
* `java.util.Iterator`

##### Regular expressions

* `java.util.regex.Pattern`

#### Java 8

##### Optionals

* `java.util.Optional`
* `java.util.OptionalDouble`
* `java.util.OptionalInt`
* `java.util.OptionalDouble`

##### Streams

* `java.util.stream.Collector`
* `java.util.stream.DoubleStream`
* `java.util.stream.IntStream`
* `java.util.stream.LongStream`
* `java.util.stream.Stream`

##### Time

* `java.time.DayOfWeek`
* `java.time.Duration`
* `java.time.Instant`
* `java.time.LocalDate`
* `java.time.LocalDateTime`
* `java.time.LocalTime`
* `java.time.Month`
* `java.time.MonthDay`
* `java.time.OffsetDateTime`
* `java.time.OffsetTime`
* `java.time.Period`
* `java.time.Year`
* `java.time.YearMonth`
* `java.time.ZonedDateTime`

<!--
#### Java 16

* `java.lang.Record`
-->

#### Java JSON API

* `javax.json.JsonValue`
* `jakarta.json.JsonValue`

#### Google Guava

##### Base

* `com.google.common.base.Optional`

##### Cache

* `com.google.common.cache.Cache`
* `com.google.common.cache.LoadingCache`

##### Collections

* `com.google.common.collect.BiMap`
* `com.google.common.collect.Collection`
* `com.google.common.collect.Graph`
* `com.google.common.collect.List`
* `com.google.common.collect.ListMultimap`
* `com.google.common.collect.Map`
* `com.google.common.collect.Multimap`
* `com.google.common.collect.Multiset`
* `com.google.common.collect.Network`
* `com.google.common.collect.RangeMap`
* `com.google.common.collect.RangeSet`
* `com.google.common.collect.Set`
* `com.google.common.collect.SetMultimap`
* `com.google.common.collect.SortedMap`
* `com.google.common.collect.SortedMultiset`
* `com.google.common.collect.SortedSet`
* `com.google.common.collect.Table`
* `com.google.common.collect.ValueGraph`

#### Apache Commons Collections 4

* `org.apache.commons.collections4.Bag`
* `org.apache.commons.collections4.BidiMap`
* `org.apache.commons.collections4.BoundedCollection`
* `org.apache.commons.collections4.IterableMap`
* `org.apache.commons.collections4.MultiSet`
* `org.apache.commons.collections4.MultiValuedMap`

#### Jayway JsonPath

* `com.jayway.jsonpath.JsonPath`
