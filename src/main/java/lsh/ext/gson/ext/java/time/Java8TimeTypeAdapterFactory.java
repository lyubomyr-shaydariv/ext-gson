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

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class Java8TimeTypeAdapterFactory {

	public static final ITypeAdapterFactory<DayOfWeek> defaultForDayOfWeek = forDayOfWeek(Java8TimeTypeAdapter.defaultForDayOfWeek);

	public static ITypeAdapterFactory<DayOfWeek> forDayOfWeek(final TypeAdapter<DayOfWeek> typeAdapter) {
		return ITypeAdapterFactory.forClass(DayOfWeek.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Duration> defaultForDuration = forDuration(Java8TimeTypeAdapter.defaultForDuration);

	public static ITypeAdapterFactory<Duration> forDuration(final TypeAdapter<Duration> typeAdapter) {
		return ITypeAdapterFactory.forClass(Duration.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Instant> defaultForInstant = forInstant(Java8TimeTypeAdapter.defaultForInstant);

	public static ITypeAdapterFactory<Instant> forInstant(final TypeAdapter<Instant> typeAdapter) {
		return ITypeAdapterFactory.forClass(Instant.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<LocalDateTime> defaultForLocalDateTime = forLocalDateTime(Java8TimeTypeAdapter.defaultForLocalDateTime);

	public static ITypeAdapterFactory<LocalDateTime> forLocalDateTime(final TypeAdapter<LocalDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalDateTime.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<LocalDate> defaultForLocalDate = forLocalDate(Java8TimeTypeAdapter.defaultForLocalDate);

	public static ITypeAdapterFactory<LocalDate> forLocalDate(final TypeAdapter<LocalDate> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalDate.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<LocalTime> defaultForLocalTime = forLocalTime(Java8TimeTypeAdapter.defaultForLocalTime);

	public static ITypeAdapterFactory<LocalTime> forLocalTime(final TypeAdapter<LocalTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalTime.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<MonthDay> defaultForMonthDay = forMonthDay(Java8TimeTypeAdapter.defaulFortMonthDay);

	public static ITypeAdapterFactory<MonthDay> forMonthDay(final TypeAdapter<MonthDay> typeAdapter) {
		return ITypeAdapterFactory.forClass(MonthDay.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Month> defaultForMonth = forMonth(Java8TimeTypeAdapter.defaultForMonth);

	public static ITypeAdapterFactory<Month> forMonth(final TypeAdapter<Month> typeAdapter) {
		return ITypeAdapterFactory.forClass(Month.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<OffsetDateTime> defaultForOffsetDateTime = forOffsetDateTime(Java8TimeTypeAdapter.defaultForOffsetDateTime);

	public static ITypeAdapterFactory<OffsetDateTime> forOffsetDateTime(final TypeAdapter<OffsetDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(OffsetDateTime.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<OffsetTime> defaultForOffsetTime = forOffsetTime(Java8TimeTypeAdapter.defaultForOffsetTime);

	public static ITypeAdapterFactory<OffsetTime> forOffsetTime(final TypeAdapter<OffsetTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(OffsetTime.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Period> defaultForPeriod = forPeriod(Java8TimeTypeAdapter.defaultForPeriod);

	public static ITypeAdapterFactory<Period> forPeriod(final TypeAdapter<Period> typeAdapter) {
		return ITypeAdapterFactory.forClass(Period.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<YearMonth> defaultForYearMonth = forYearMonth(Java8TimeTypeAdapter.defaultForYearMonth);

	public static ITypeAdapterFactory<YearMonth> forYearMonth(final TypeAdapter<YearMonth> typeAdapter) {
		return ITypeAdapterFactory.forClass(YearMonth.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<Year> defaultForYear = forYear(Java8TimeTypeAdapter.defaultForYear);

	public static ITypeAdapterFactory<Year> forYear(final TypeAdapter<Year> typeAdapter) {
		return ITypeAdapterFactory.forClass(Year.class, provider -> typeAdapter);
	}

	public static final ITypeAdapterFactory<ZonedDateTime> defaultForZonedDateTime = forZonedDateTime(Java8TimeTypeAdapter.defaultForZonedDateTime);

	public static ITypeAdapterFactory<ZonedDateTime> forZonedDateTime(final TypeAdapter<ZonedDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(ZonedDateTime.class, provider -> typeAdapter);
	}

}
