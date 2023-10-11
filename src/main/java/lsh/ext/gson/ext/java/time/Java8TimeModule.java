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

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;

public final class Java8TimeModule
		extends AbstractModule {

	private Java8TimeModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<DayOfWeek> dayOfWeekTypeAdapterFactory = DayOfWeekTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Duration> durationTypeAdapterFactory = DurationTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Instant> instantTypeAdapterFactory = InstantTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Month> monthTypeAdapterFactory = MonthTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Period> periodTypeAdapterFactory = PeriodTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalDateTime> localDateTimeTypeAdapterFactory = LocalDateTimeTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalDate> localDateTypeAdapterFactory = LocalDateTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalTime> localTimeTypeAdapterFactory = LocalTimeTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<MonthDay> monthDayTypeAdapterFactory = MonthDayTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<OffsetDateTime> offsetDateTimeTypeAdapterFactory = OffsetDateTimeTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<OffsetTime> offsetTimeTypeAdapterFactory = OffsetTimeTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<YearMonth> yearMonthTypeAdapterFactory = YearMonthTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<Year> yearTypeAdapterFactory = YearTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<ZonedDateTime> zonedDateTimeTypeAdapterFactory = ZonedDateTimeTypeAdapterFactory.getInstance();

		public IModule build() {
			return new Java8TimeModule(UnmodifiableIterable.copyOf(
					dayOfWeekTypeAdapterFactory,
					durationTypeAdapterFactory,
					instantTypeAdapterFactory,
					monthTypeAdapterFactory,
					periodTypeAdapterFactory,
					localDateTimeTypeAdapterFactory,
					localDateTypeAdapterFactory,
					localTimeTypeAdapterFactory,
					monthDayTypeAdapterFactory,
					offsetDateTimeTypeAdapterFactory,
					offsetTimeTypeAdapterFactory,
					yearMonthTypeAdapterFactory,
					yearTypeAdapterFactory,
					zonedDateTimeTypeAdapterFactory
			));
		}

	}

}
