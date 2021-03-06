package lsh.ext.gson.ext;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.adapters.AbstractModule;
import lsh.ext.gson.adapters.IModule;
import lsh.ext.gson.ext.java.time.DayOfWeekTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.DurationTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.InstantTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.LocalDateTimeTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.LocalDateTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.LocalTimeTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.MonthDayTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.MonthTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.OffsetDateTimeTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.OffsetTimeTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.PeriodTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.YearMonthTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.YearTypeAdapterFactory;
import lsh.ext.gson.ext.java.time.ZonedDateTimeTypeAdapterFactory;

/**
 * <p>
 * Implements a Java 8 Time API module registering the following type adapter factories:
 * </p>
 *
 * <ul>
 * <li>{@link DayOfWeekTypeAdapterFactory}</li>
 * <li>{@link DurationTypeAdapterFactory}</li>
 * <li>{@link InstantTypeAdapterFactory}</li>
 * <li>{@link LocalDateTimeTypeAdapterFactory}</li>
 * <li>{@link LocalDateTypeAdapterFactory}</li>
 * <li>{@link LocalTimeTypeAdapterFactory}</li>
 * <li>{@link MonthDayTypeAdapterFactory}</li>
 * <li>{@link MonthTypeAdapterFactory}</li>
 * <li>{@link OffsetDateTimeTypeAdapterFactory}</li>
 * <li>{@link OffsetTimeTypeAdapterFactory}</li>
 * <li>{@link PeriodTypeAdapterFactory}</li>
 * <li>{@link YearMonthTypeAdapterFactory}</li>
 * <li>{@link YearTypeAdapterFactory}</li>
 * <li>{@link ZonedDateTimeTypeAdapterFactory}</li>
 * </ul>
 *
 * @author Lyubomyr Shaydariv
 * @version 0-SNAPSHOT
 */
public final class Java8TimeModule
		extends AbstractModule {

	private static final IModule defaultInstance = build()
			.done();

	protected Java8TimeModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super("Java 8 Time", typeAdapterFactories);
	}

	/**
	 * @return The default instance of the module with the default type adapter factories settings.
	 */
	public static IModule getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder build() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
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

		/**
		 * Sets a date/time formatter to be used in {@link LocalDateTimeTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param localDateTimeFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withLocalDateTimeFormatter(@Nullable final DateTimeFormatter localDateTimeFormatter) {
			this.localDateTimeFormatter = localDateTimeFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link LocalDateTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param localDateFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withLocalDateFormatter(@Nullable final DateTimeFormatter localDateFormatter) {
			this.localDateFormatter = localDateFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link LocalTimeTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param localTimeFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withLocalTimeFormatter(@Nullable final DateTimeFormatter localTimeFormatter) {
			this.localTimeFormatter = localTimeFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link MonthDayTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param monthDayFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withMonthDayFormatter(@Nullable final DateTimeFormatter monthDayFormatter) {
			this.monthDayFormatter = monthDayFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link OffsetDateTimeTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param offsetDateTimeFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withOffsetDateTimeFormatter(@Nullable final DateTimeFormatter offsetDateTimeFormatter) {
			this.offsetDateTimeFormatter = offsetDateTimeFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link OffsetTimeTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param offsetTimeFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withOffsetTimeFormatter(@Nullable final DateTimeFormatter offsetTimeFormatter) {
			this.offsetTimeFormatter = offsetTimeFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link YearMonthTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param yearMonthFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withYearMonthFormatter(@Nullable final DateTimeFormatter yearMonthFormatter) {
			this.yearMonthFormatter = yearMonthFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link YearTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param yearFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withYearFormatter(@Nullable final DateTimeFormatter yearFormatter) {
			this.yearFormatter = yearFormatter;
			return this;
		}

		/**
		 * Sets a date/time formatter to be used in {@link ZonedDateTimeTypeAdapterFactory#get(DateTimeFormatter)}.
		 *
		 * @param zonedDateTimeFormatter Date formatter
		 *
		 * @return Self.
		 */
		public Builder withZonedDateTimeFormatter(@Nullable final DateTimeFormatter zonedDateTimeFormatter) {
			this.zonedDateTimeFormatter = zonedDateTimeFormatter;
			return this;
		}

		/**
		 * @return A new module instance.
		 */
		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Stream.of(
					DayOfWeekTypeAdapterFactory.getDefaultInstance(),
					DurationTypeAdapterFactory.getDefaultInstance(),
					InstantTypeAdapterFactory.getDefaultInstance(),
					LocalDateTimeTypeAdapterFactory.get(localDateTimeFormatter),
					LocalDateTypeAdapterFactory.get(localDateFormatter),
					LocalTimeTypeAdapterFactory.get(localTimeFormatter),
					MonthDayTypeAdapterFactory.get(monthDayFormatter),
					MonthTypeAdapterFactory.getDefaultInstance(),
					OffsetDateTimeTypeAdapterFactory.get(offsetDateTimeFormatter),
					OffsetTimeTypeAdapterFactory.get(offsetTimeFormatter),
					PeriodTypeAdapterFactory.getDefaultInstance(),
					YearMonthTypeAdapterFactory.get(yearMonthFormatter),
					YearTypeAdapterFactory.get(yearFormatter),
					ZonedDateTimeTypeAdapterFactory.get(zonedDateTimeFormatter)
			)
					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
			return new Java8TimeModule(typeAdapterFactories);
		}

	}

}
