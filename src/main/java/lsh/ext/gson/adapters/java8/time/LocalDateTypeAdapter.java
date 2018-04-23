package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class LocalDateTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDate> {

	private static final TypeAdapter<LocalDate> instance = new LocalDateTypeAdapter(null);

	private LocalDateTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<LocalDate> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected LocalDate doFromString(@Nonnull final String string) {
		return LocalDate.parse(string);
	}

	@Nonnull
	@Override
	protected LocalDate doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return LocalDate.parse(string, formatter);
	}

}
