package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Represents the epoch to {@link Date} and vice versa type adapter.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class EpochDateTypeAdapter
		extends TypeAdapter<Date> {

	private static final TypeAdapter<Date> epochDateTypeAdapter = new EpochDateTypeAdapter();

	private EpochDateTypeAdapter() {
	}

	/**
	 * @return An instance of {@link EpochDateTypeAdapter}.
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapter<Date> getEpochDateTypeAdapter() {
		return epochDateTypeAdapter;
	}

	@Override
	@Nullable
	public Date read(final JsonReader in)
			throws IOException {
		return in.peek() != JsonToken.NULL ? new Date(in.nextLong()) : null;
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final Date value)
			throws IOException {
		if ( value == null ) {
			out.nullValue();
		} else {
			out.value(value.getTime());
		}
	}

}
