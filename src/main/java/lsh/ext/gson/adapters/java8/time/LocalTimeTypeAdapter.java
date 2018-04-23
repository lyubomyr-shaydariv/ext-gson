package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class LocalTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

	private static final TypeAdapter<LocalTime> instance = new LocalTimeTypeAdapter(null);

	private LocalTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<LocalTime> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected LocalTime doFromString(@Nonnull final String string) {
		return LocalTime.parse(string);
	}

	@Nonnull
	@Override
	protected LocalTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return LocalTime.parse(string, formatter);
	}

}
