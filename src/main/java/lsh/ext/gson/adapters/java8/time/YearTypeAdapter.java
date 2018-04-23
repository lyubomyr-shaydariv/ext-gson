package lsh.ext.gson.adapters.java8.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class YearTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Year> {

	private static final TypeAdapter<Year> instance = new YearTypeAdapter(null);

	private YearTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<Year> get() {
		return instance;
	}

	public static TypeAdapter<Year> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapter (dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected Year doFromString(@Nonnull final String string) {
		return Year.parse(string);
	}

	@Nonnull
	@Override
	protected Year doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return Year.parse(string, formatter);
	}

}
