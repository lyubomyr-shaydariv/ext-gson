package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class OffsetTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetTime> {

	private static final TypeAdapter<OffsetTime> instance = new OffsetTimeTypeAdapter(null);

	private OffsetTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<OffsetTime> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected OffsetTime doFromString(@Nonnull final String string) {
		return OffsetTime.parse(string);
	}

	@Nonnull
	@Override
	protected OffsetTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return OffsetTime.parse(string, formatter);
	}

}
