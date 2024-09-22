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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

public final class Java8TimeModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = new Builder()
			.build();

	private Java8TimeModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<DayOfWeek> dayOfWeekTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForDayOfWeek;

		@Setter
		private ITypeAdapterFactory<Duration> durationTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForDuration;

		@Setter
		private ITypeAdapterFactory<Instant> instantTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForInstant;

		@Setter
		private ITypeAdapterFactory<LocalDateTime> localDateTimeTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForLocalDateTime;

		@Setter
		private ITypeAdapterFactory<LocalDate> localDateTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForLocalDate;

		@Setter
		private ITypeAdapterFactory<LocalTime> localTimeTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForLocalTime;

		@Setter
		private ITypeAdapterFactory<MonthDay> monthDayTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForMonthDay;

		@Setter
		private ITypeAdapterFactory<Month> monthTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForMonth;

		@Setter
		private ITypeAdapterFactory<OffsetDateTime> offsetDateTimeTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForOffsetDateTime;

		@Setter
		private ITypeAdapterFactory<OffsetTime> offsetTimeTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForOffsetTime;

		@Setter
		private ITypeAdapterFactory<Period> periodTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForPeriod;

		@Setter
		private ITypeAdapterFactory<YearMonth> yearMonthTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForYearMonth;

		@Setter
		private ITypeAdapterFactory<Year> yearTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForYear;

		@Setter
		private ITypeAdapterFactory<ZonedDateTime> zonedDateTimeTypeAdapterFactory = Java8TimeTypeAdapterFactory.defaultForZonedDateTime;

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new Java8TimeModule(
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
			);
		}

	}

}
