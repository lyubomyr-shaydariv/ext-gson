package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class ZonedDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<ZonedDateTime> {

	private static final TypeAdapter<ZonedDateTime> instance = new ZonedDateTimeTypeAdapter(null);

	private ZonedDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<ZonedDateTime> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected ZonedDateTime doFromString(@Nonnull final String string) {
		return ZonedDateTime.parse(string);
	}

	@Nonnull
	@Override
	protected ZonedDateTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return ZonedDateTime.parse(string, formatter);
	}

}
