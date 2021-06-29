package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Represents the epoch to {@link Date} and vice versa type adapter.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class EpochDateTypeAdapter
		extends TypeAdapter<Date> {

	private static final TypeAdapter<Date> defaultInstance = new EpochDateTypeAdapter()
			.nullSafe();

	private EpochDateTypeAdapter() {
	}

	/**
	 * @return An instance of {@link EpochDateTypeAdapter}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapter<Date> getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	public Date read(final JsonReader in)
			throws IOException {
		return new Date(in.nextLong() * 1000);
	}

	@Override
	public void write(final JsonWriter out, final Date value)
			throws IOException {
		out.value(value.getTime() / 1000);
	}

}
