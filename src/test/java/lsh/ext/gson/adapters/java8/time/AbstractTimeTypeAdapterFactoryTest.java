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
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

abstract class AbstractTimeTypeAdapterFactoryTest<T>
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

	private final TypeToken<T> typeToken;

	protected AbstractTimeTypeAdapterFactoryTest(final TypeToken<T> typeToken) {
		super(false);
		this.typeToken = typeToken;
	}

	@Nonnull
	@Override
	protected final Stream<Arguments> supported() {
		return allTypeTokens.stream()
				.filter(tt -> tt.equals(typeToken))
				.map(Arguments::of);
	}

	@Nonnull
	@Override
	protected final Stream<Arguments> unsupported() {
		return allTypeTokens.stream()
				.filter(tt -> !tt.equals(typeToken))
				.map(Arguments::of);
	}

}
