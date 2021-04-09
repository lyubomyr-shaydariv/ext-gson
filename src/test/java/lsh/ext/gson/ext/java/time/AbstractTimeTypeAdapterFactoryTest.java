package lsh.ext.gson.ext.java.time;

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
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

abstract class AbstractTimeTypeAdapterFactoryTest<T>
		extends AbstractTypeAdapterFactoryTest {

	private static final Class<?>[] java8TimeApiClasses = {
			DayOfWeek.class,
			Duration.class,
			Instant.class,
			LocalDate.class,
			LocalDateTime.class,
			LocalTime.class,
			Month.class,
			MonthDay.class,
			OffsetDateTime.class,
			OffsetTime.class,
			Period.class,
			Year.class,
			YearMonth.class,
			ZonedDateTime.class
	};

	private final Class<T> clazz;

	protected AbstractTimeTypeAdapterFactoryTest(final Class<T> clazz) {
		super(false);
		this.clazz = clazz;
	}

	@Nonnull
	@Override
	protected final Stream<Arguments> supported() {
		return Stream.of(java8TimeApiClasses)
				.filter(c -> c.equals(clazz))
				.map(TypeToken::get)
				.map(Arguments::of);
	}

	@Nonnull
	@Override
	protected final Stream<Arguments> unsupported() {
		return Stream.of(java8TimeApiClasses)
				.filter(tt -> !tt.equals(clazz))
				.map(TypeToken::get)
				.map(Arguments::of);
	}

}
