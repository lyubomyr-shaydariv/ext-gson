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
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonStringTypeAdapter;

@UtilityClass
public final class Java8TimeTypeAdapter {

	@Getter
	private static final TypeAdapter<DayOfWeek> defaultForDayOfWeek = JsonStringTypeAdapter.getInstance(Enum::toString, DayOfWeek::valueOf);

	@Getter
	private static final TypeAdapter<Duration> defaultForDuration = JsonStringTypeAdapter.getInstance(Duration::toString, Duration::parse);

	@Getter
	private static final TypeAdapter<Instant> defaultForInstant = forInstantTypeAdapter(DateTimeFormatter.ISO_INSTANT);

	public static TypeAdapter<Instant> forInstantTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, Instant::from));
	}

	@Getter
	private static final TypeAdapter<LocalDate> localDateTypeAdapter = getLocalDateTypeAdapter(DateTimeFormatter.ISO_LOCAL_DATE);

	public static TypeAdapter<LocalDate> getLocalDateTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalDate::from));
	}

	@Getter
	private static final TypeAdapter<LocalDateTime> localDateTimeTypeAdapter = getLocalDateTimeTypeAdapter(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	public static TypeAdapter<LocalDateTime> getLocalDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalDateTime::from));
	}

	@Getter
	private static final TypeAdapter<LocalTime> localTimeTypeAdapter = getLocalTimeTypeAdapter(DateTimeFormatter.ISO_LOCAL_TIME);

	public static TypeAdapter<LocalTime> getLocalTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, LocalTime::from));
	}

	@Getter
	private static final TypeAdapter<Month> monthTypeAdapter = JsonStringTypeAdapter.getInstance(Enum::toString, Month::valueOf);

	@Getter
	private static final TypeAdapter<MonthDay> monthDayTypeAdapter = getMonthDayTypeAdapter(new DateTimeFormatterBuilder()
			.appendLiteral("--")
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.appendLiteral('-')
			.appendValue(ChronoField.DAY_OF_MONTH, 2)
			.toFormatter()
	);

	public static TypeAdapter<MonthDay> getMonthDayTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, MonthDay::from));
	}

	@Getter
	private static final TypeAdapter<OffsetDateTime> offsetDateTimeTypeAdapter = getOffsetDateTimeTypeAdapter(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	public static TypeAdapter<OffsetDateTime> getOffsetDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, OffsetDateTime::from));
	}

	@Getter
	private static final TypeAdapter<OffsetTime> offsetTimeTypeAdapter = getOffsetTimeTypeAdapter(DateTimeFormatter.ISO_OFFSET_TIME);

	public static TypeAdapter<OffsetTime> getOffsetTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, OffsetTime::from));
	}

	@Getter
	private static final TypeAdapter<Period> periodTypeAdapter = JsonStringTypeAdapter.getInstance(Period::toString, Period::parse);

	@Getter
	private static final TypeAdapter<YearMonth> yearMonthTypeAdapter = getYearMonthTypeAdapter(new DateTimeFormatterBuilder()
			.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
			.appendLiteral('-')
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.toFormatter()
	);

	public static TypeAdapter<YearMonth> getYearMonthTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, YearMonth::from));
	}

	@Getter
	private static final TypeAdapter<Year> yearTypeAdapter = getYearTypeAdapter(new DateTimeFormatterBuilder()
			.parseLenient()
			.appendValue(ChronoField.YEAR, 1, 10, SignStyle.NORMAL)
			.toFormatter()
	);

	public static TypeAdapter<Year> getYearTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, Year::from));
	}

	@Getter
	private static final TypeAdapter<ZonedDateTime> zonedDateTimeTypeAdapter = getZonedDateTimeTypeAdapter(DateTimeFormatter.ISO_ZONED_DATE_TIME);

	public static TypeAdapter<ZonedDateTime> getZonedDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		return JsonStringTypeAdapter.getInstance(dateTimeFormatter::format, s -> dateTimeFormatter.parse(s, ZonedDateTime::from));
	}

}
