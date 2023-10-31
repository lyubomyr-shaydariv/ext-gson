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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<DayOfWeek> dayOfWeekTypeAdapterFactory = DayOfWeekTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<Duration> durationTypeAdapterFactory = DurationTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<Instant> instantTypeAdapterFactory = InstantTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<Month> monthTypeAdapterFactory = MonthTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<Period> periodTypeAdapterFactory = PeriodTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalDateTime> localDateTimeTypeAdapterFactory = LocalDateTimeTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalDate> localDateTypeAdapterFactory = LocalDateTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<LocalTime> localTimeTypeAdapterFactory = LocalTimeTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<MonthDay> monthDayTypeAdapterFactory = MonthDayTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<OffsetDateTime> offsetDateTimeTypeAdapterFactory = OffsetDateTimeTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<OffsetTime> offsetTimeTypeAdapterFactory = OffsetTimeTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<YearMonth> yearMonthTypeAdapterFactory = YearMonthTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<Year> yearTypeAdapterFactory = YearTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<ZonedDateTime> zonedDateTimeTypeAdapterFactory = ZonedDateTimeTypeAdapter.Factory.getInstance();

		public static Builder create() {
			return new Builder();
		}

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
