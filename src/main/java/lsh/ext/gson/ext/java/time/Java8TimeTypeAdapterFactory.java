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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class Java8TimeTypeAdapterFactory {

	@Getter
	private static final ITypeAdapterFactory<DayOfWeek> defaultForDayOfWeek = forDayOfWeek(Java8TimeTypeAdapter.getDefaultForDayOfWeek());

	public static ITypeAdapterFactory<DayOfWeek> forDayOfWeek(final TypeAdapter<DayOfWeek> typeAdapter) {
		return ITypeAdapterFactory.forClass(DayOfWeek.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<Duration> defaultForDuration = forDuration(Java8TimeTypeAdapter.getDefaultForDuration());

	public static ITypeAdapterFactory<Duration> forDuration(final TypeAdapter<Duration> typeAdapter) {
		return ITypeAdapterFactory.forClass(Duration.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<Instant> defaultForInstant = forInstant(Java8TimeTypeAdapter.getDefaultForInstant());

	public static ITypeAdapterFactory<Instant> forInstant(final TypeAdapter<Instant> typeAdapter) {
		return ITypeAdapterFactory.forClass(Instant.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<LocalDateTime> defaultForLocalDateTime = forLocalDateTime(Java8TimeTypeAdapter.getLocalDateTimeTypeAdapter());

	public static ITypeAdapterFactory<LocalDateTime> forLocalDateTime(final TypeAdapter<LocalDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalDateTime.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<LocalDate> defaultForLocalDate = forLocalDate(Java8TimeTypeAdapter.getLocalDateTypeAdapter());

	public static ITypeAdapterFactory<LocalDate> forLocalDate(final TypeAdapter<LocalDate> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalDate.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<LocalTime> defaultForLocalTime = forLocalTime(Java8TimeTypeAdapter.getLocalTimeTypeAdapter());

	public static ITypeAdapterFactory<LocalTime> forLocalTime(final TypeAdapter<LocalTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(LocalTime.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<MonthDay> defaultForMonthDay = forMonthDay(Java8TimeTypeAdapter.getMonthDayTypeAdapter());

	public static ITypeAdapterFactory<MonthDay> forMonthDay(final TypeAdapter<MonthDay> typeAdapter) {
		return ITypeAdapterFactory.forClass(MonthDay.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<Month> defaultForMonth = forMonth(Java8TimeTypeAdapter.getMonthTypeAdapter());

	public static ITypeAdapterFactory<Month> forMonth(final TypeAdapter<Month> typeAdapter) {
		return ITypeAdapterFactory.forClass(Month.class, () -> typeAdapter);
	}

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final ITypeAdapterFactory<OffsetDateTime> defaultForOffsetDateTime = forOffsetDateTime(Java8TimeTypeAdapter.getOffsetDateTimeTypeAdapter());

	public static ITypeAdapterFactory<OffsetDateTime> forOffsetDateTime(final TypeAdapter<OffsetDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(OffsetDateTime.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<OffsetTime> defaultForOffsetTime = forOffsetTime(Java8TimeTypeAdapter.getOffsetTimeTypeAdapter());

	public static ITypeAdapterFactory<OffsetTime> forOffsetTime(final TypeAdapter<OffsetTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(OffsetTime.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<Period> defaultForPeriod = forPeriod(Java8TimeTypeAdapter.getPeriodTypeAdapter());

	public static ITypeAdapterFactory<Period> forPeriod(final TypeAdapter<Period> typeAdapter) {
		return ITypeAdapterFactory.forClass(Period.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<YearMonth> defaultForYearMonth = forYearMonth(Java8TimeTypeAdapter.getYearMonthTypeAdapter());

	public static ITypeAdapterFactory<YearMonth> forYearMonth(final TypeAdapter<YearMonth> typeAdapter) {
		return ITypeAdapterFactory.forClass(YearMonth.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<Year> defaultForYear = forYear(Java8TimeTypeAdapter.getYearTypeAdapter());

	public static ITypeAdapterFactory<Year> forYear(final TypeAdapter<Year> typeAdapter) {
		return ITypeAdapterFactory.forClass(Year.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<ZonedDateTime> defaultForZonedDateTime = forZonedDateTime(Java8TimeTypeAdapter.getZonedDateTimeTypeAdapter());

	public static ITypeAdapterFactory<ZonedDateTime> forZonedDateTime(final TypeAdapter<ZonedDateTime> typeAdapter) {
		return ITypeAdapterFactory.forClass(ZonedDateTime.class, () -> typeAdapter);
	}

}
