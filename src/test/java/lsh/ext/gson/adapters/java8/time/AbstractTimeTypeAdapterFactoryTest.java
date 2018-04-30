package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;

abstract class AbstractTimeTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final Collection<TypeToken<?>> allTypeTokens = ImmutableList.<TypeToken<?>>builder()
			.add(new TypeToken<DayOfWeek>() {})
			.add(new TypeToken<Duration>() {})
			.add(new TypeToken<Instant>() {})
			.add(new TypeToken<LocalDate>() {})
			.add(new TypeToken<LocalDateTime>() {})
			.add(new TypeToken<LocalTime>() {})
			.add(new TypeToken<Month>() {})
			.add(new TypeToken<MonthDay>() {})
			.add(new TypeToken<OffsetDateTime>() {})
			.add(new TypeToken<OffsetTime>() {})
			.add(new TypeToken<Period>() {})
			.add(new TypeToken<Year>() {})
			.add(new TypeToken<YearMonth>() {})
			.add(new TypeToken<ZonedDateTime>() {})
			.build();

	protected AbstractTimeTypeAdapterFactoryTest(final TestWith testWith) {
		super(false, testWith);
	}

	protected static Iterable<TestWith> params(final TypeToken<?> typeToken) {
		return allTypeTokens
				.stream()
				.map(actualTypeToken -> typeToken.equals(actualTypeToken) ? testWith(typeToken) : testWith(typeToken, actualTypeToken))
				.collect(ImmutableList.toImmutableList());
	}

}
