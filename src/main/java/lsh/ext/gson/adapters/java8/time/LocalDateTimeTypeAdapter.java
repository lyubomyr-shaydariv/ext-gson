package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class LocalDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDateTime> {

	private static final TypeAdapter<LocalDateTime> instance = new LocalDateTimeTypeAdapter(null);

	private LocalDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<LocalDateTime> get() {
		return instance;
	}

	public static TypeAdapter<LocalDateTime> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected LocalDateTime doFromString(@Nonnull final String string) {
		return LocalDateTime.parse(string);
	}

	@Nonnull
	@Override
	protected LocalDateTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return LocalDateTime.parse(string, formatter);
	}

}
