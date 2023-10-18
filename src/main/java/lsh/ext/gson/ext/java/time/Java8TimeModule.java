package lsh.ext.gson.ext.java.time;

import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.UnmodifiableIterable;

/**
 * Implements a Java 8 Time API module registering the following type adapter factories:
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
 */
public final class Java8TimeModule
		extends AbstractModule {

	private Java8TimeModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
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

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new Java8TimeModule(UnmodifiableIterable.copyOf(
					DayOfWeekTypeAdapterFactory.getInstance(),
					DurationTypeAdapterFactory.getInstance(),
					InstantTypeAdapterFactory.getInstance(),
					MonthTypeAdapterFactory.getInstance(),
					PeriodTypeAdapterFactory.getInstance(),
					LocalDateTimeTypeAdapterFactory.getInstance(localDateTimeFormatter),
					LocalDateTypeAdapterFactory.getInstance(localDateFormatter),
					LocalTimeTypeAdapterFactory.getInstance(localTimeFormatter),
					MonthDayTypeAdapterFactory.getInstance(monthDayFormatter),
					OffsetDateTimeTypeAdapterFactory.getInstance(offsetDateTimeFormatter),
					OffsetTimeTypeAdapterFactory.getInstance(offsetTimeFormatter),
					YearMonthTypeAdapterFactory.getInstance(yearMonthFormatter),
					YearTypeAdapterFactory.getInstance(yearFormatter),
					ZonedDateTimeTypeAdapterFactory.getInstance(zonedDateTimeFormatter)
			));
		}

	}

}
