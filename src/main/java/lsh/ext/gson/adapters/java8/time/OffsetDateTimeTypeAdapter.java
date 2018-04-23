package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class OffsetDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetDateTime> {

	private static final TypeAdapter<OffsetDateTime> instance = new OffsetDateTimeTypeAdapter(null);

	private OffsetDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<OffsetDateTime> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected OffsetDateTime doFromString(@Nonnull final String string) {
		return OffsetDateTime.parse(string);
	}

	@Nonnull
	@Override
	protected OffsetDateTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return OffsetDateTime.parse(string, formatter);
	}

}
