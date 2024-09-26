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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.LiteralStringTypeAdapter;

@UtilityClass
public final class Java8TimeTypeAdapter {

	public static final TypeAdapter<DayOfWeek> defaultForDayOfWeek = LiteralStringTypeAdapter.getInstance(Enum::toString, DayOfWeek::valueOf);

	public static final TypeAdapter<Duration> defaultForDuration = LiteralStringTypeAdapter.getInstance(Duration::toString, Duration::parse);

	public static final TypeAdapter<Instant> defaultForInstant = forInstant(DateTimeFormatter.ISO_INSTANT);

	public static TypeAdapter<Instant> forInstant(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, Instant::from));
	}

	public static final TypeAdapter<LocalDate> defaultForLocalDate = forLocalDate(DateTimeFormatter.ISO_LOCAL_DATE);

	public static TypeAdapter<LocalDate> forLocalDate(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalDate::from));
	}

	public static final TypeAdapter<LocalDateTime> defaultForLocalDateTime = forLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	public static TypeAdapter<LocalDateTime> forLocalDateTime(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalDateTime::from));
	}

	public static final TypeAdapter<LocalTime> defaultForLocalTime = forLocalTime(DateTimeFormatter.ISO_LOCAL_TIME);

	public static TypeAdapter<LocalTime> forLocalTime(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalTime::from));
	}

	public static final TypeAdapter<Month> defaultForMonth = LiteralStringTypeAdapter.getInstance(Enum::toString, Month::valueOf);

	public static final TypeAdapter<MonthDay> defaulFortMonthDay = forMonthDay(new DateTimeFormatterBuilder()
			.appendLiteral("--")
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.appendLiteral('-')
			.appendValue(ChronoField.DAY_OF_MONTH, 2)
			.toFormatter()
	);

	public static TypeAdapter<MonthDay> forMonthDay(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, MonthDay::from));
	}

	public static final TypeAdapter<OffsetDateTime> defaultForOffsetDateTime = forOffsetDateTime(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	public static TypeAdapter<OffsetDateTime> forOffsetDateTime(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, OffsetDateTime::from));
	}

	public static final TypeAdapter<OffsetTime> defaultForOffsetTime = forOffsetTime(DateTimeFormatter.ISO_OFFSET_TIME);

	public static TypeAdapter<OffsetTime> forOffsetTime(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, OffsetTime::from));
	}

	public static final TypeAdapter<Period> defaultForPeriod = LiteralStringTypeAdapter.getInstance(Period::toString, Period::parse);

	public static final TypeAdapter<YearMonth> defaultForYearMonth = forYearMonth(new DateTimeFormatterBuilder()
			.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
			.appendLiteral('-')
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.toFormatter()
	);

	public static TypeAdapter<YearMonth> forYearMonth(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, YearMonth::from));
	}

	public static final TypeAdapter<Year> defaultForYear = forYear(new DateTimeFormatterBuilder()
			.parseLenient()
			.appendValue(ChronoField.YEAR, 1, 10, SignStyle.NORMAL)
			.toFormatter()
	);

	public static TypeAdapter<Year> forYear(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, Year::from));
	}

	public static final TypeAdapter<ZonedDateTime> defaultForZonedDateTime = forZonedDateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME);

	public static TypeAdapter<ZonedDateTime> forZonedDateTime(final DateTimeFormatter dateTimeFormatter) {
		return LiteralStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, ZonedDateTime::from));
	}

}
