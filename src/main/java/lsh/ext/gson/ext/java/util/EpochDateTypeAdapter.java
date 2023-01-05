package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents the epoch to {@link Date} and vice versa type adapter.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EpochDateTypeAdapter
		extends TypeAdapter<Date> {

	private static final TypeAdapter<Date> instance = new EpochDateTypeAdapter()
			.nullSafe();

	/**
	 * @return An instance of {@link EpochDateTypeAdapter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Date> getInstance() {
		return instance;
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
