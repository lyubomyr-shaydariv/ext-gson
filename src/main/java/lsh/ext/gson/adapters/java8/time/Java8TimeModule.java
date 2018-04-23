package lsh.ext.gson.adapters.java8.time;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;

public final class Java8TimeModule
		extends AbstractModule {

	private static final IModule instance = build()
			.done();

	protected Java8TimeModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java 8 Time", typeAdapterFactories);
	}

	public static IModule get() {
		return instance;
	}

	public static Builder build() {
		return new Builder();
	}

	public static final class Builder {

		@Nullable
		private DateTimeFormatter localDateTimeFormatter;

		@Nullable
		private DateTimeFormatter localDateFormatter;

		@Nullable
		private DateTimeFormatter localTimeFormatter;

		@Nullable
		private DateTimeFormatter monthDayFormatter;

		@Nullable
		private DateTimeFormatter offsetDateTimeFormatter;

		@Nullable
		private DateTimeFormatter offsetTimeFormatter;

		@Nullable
		private DateTimeFormatter yearMonthFormatter;

		@Nullable
		private DateTimeFormatter yearFormatter;

		@Nullable
		private DateTimeFormatter zonedDateTimeFormatter;

		private Builder() {
		}

		public Builder withLocalDateTimeFormatter(@Nullable final DateTimeFormatter localDateTimeFormatter) {
			this.localDateTimeFormatter = localDateTimeFormatter;
			return this;
		}

		public Builder withLocalDateFormatter(@Nullable final DateTimeFormatter localDateFormatter) {
			this.localDateFormatter = localDateFormatter;
			return this;
		}

		public Builder withLocalTimeFormatter(@Nullable final DateTimeFormatter localTimeFormatter) {
			this.localTimeFormatter = localTimeFormatter;
			return this;
		}

		public Builder withMonthDayFormatter(@Nullable final DateTimeFormatter monthDayFormatter) {
			this.monthDayFormatter = monthDayFormatter;
			return this;
		}

		public Builder withOffsetDateTimeFormatter(@Nullable final DateTimeFormatter offsetDateTimeFormatter) {
			this.offsetDateTimeFormatter = offsetDateTimeFormatter;
			return this;
		}

		public Builder withOffsetTimeFormatter(@Nullable final DateTimeFormatter offsetTimeFormatter) {
			this.offsetTimeFormatter = offsetTimeFormatter;
			return this;
		}

		public Builder withYearMonthFormatter(@Nullable final DateTimeFormatter yearMonthFormatter) {
			this.yearMonthFormatter = yearMonthFormatter;
			return this;
		}

		public Builder withYearFormatter(@Nullable final DateTimeFormatter yearFormatter) {
			this.yearFormatter = yearFormatter;
			return this;
		}

		public Builder withZonedDateTimeFormatter(@Nullable final DateTimeFormatter zonedDateTimeFormatter) {
			this.zonedDateTimeFormatter = zonedDateTimeFormatter;
			return this;
		}

		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Stream.of(
					DayOfWeekTypeAdapterFactory.get(),
					DurationTypeAdapterFactory.get(),
					InstantTypeAdapterFactory.get(),
					LocalDateTimeTypeAdapterFactory.get(localDateTimeFormatter),
					LocalDateTypeAdapterFactory.get(localDateFormatter),
					LocalTimeTypeAdapterFactory.get(localTimeFormatter),
					MonthDayTypeAdapterFactory.get(monthDayFormatter),
					MonthTypeAdapterFactory.get(),
					OffsetDateTimeTypeAdapterFactory.get(offsetDateTimeFormatter),
					OffsetTimeTypeAdapterFactory.get(offsetTimeFormatter),
					PeriodTypeAdapterFactory.get(),
					YearMonthTypeAdapterFactory.get(yearMonthFormatter),
					YearTypeAdapterFactory.get(yearFormatter),
					ZonedDateTimeTypeAdapterFactory.get(zonedDateTimeFormatter)
			)
					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
			return new Java8TimeModule(typeAdapterFactories);
		}

	}

}
