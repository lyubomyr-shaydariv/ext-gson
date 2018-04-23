package lsh.ext.gson.adapters.java8.time;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		private Builder() {
		}

		public IModule done() {
			final Iterable<? extends TypeAdapterFactory> typeAdapterFactories = Stream.of(
					DayOfWeekTypeAdapterFactory.get(),
					DurationTypeAdapterFactory.get(),
					InstantTypeAdapterFactory.get(),
					LocalDateTimeTypeAdapterFactory.get(),
					LocalDateTypeAdapterFactory.get(),
					LocalTimeTypeAdapterFactory.get(),
					MonthDayTypeAdapterFactory.get(),
					MonthTypeAdapterFactory.get(),
					OffsetDateTimeTypeAdapterFactory.get(),
					OffsetTimeTypeAdapterFactory.get(),
					PeriodTypeAdapterFactory.get(),
					YearMonthTypeAdapterFactory.get(),
					YearTypeAdapterFactory.get(),
					ZonedDateTimeTypeAdapterFactory.get()
			)
					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
			return new Java8TimeModule(typeAdapterFactories);
		}

	}

}