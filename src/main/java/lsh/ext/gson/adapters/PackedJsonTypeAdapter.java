package lsh.ext.gson.adapters;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lsh.ext.gson.JsonStreams;

/**
 * This type adapter is designed to "pack" child JSON properties as strings. For example, suppose the following mapping:
 *
 * <pre>
 * final class Outer {
 *    {@literal @}JsonAdapter(PackedJsonTypeAdapter.class)
 *     final String inner = null;
 * }
 * </pre>
 *
 * and the following JSON document:
 *
 * <pre>
 * {
 *     "inner": {
 *         "foo": 1,
 *         "bar": 2
 *     }
 * }
 * </pre>
 *
 * After the {@code inner} field is deserialized, if the whole JSON document is deserialized as {@code Outer}, its value is
 *
 * <pre>
 * "{\"foo\":1,\"bar\":2}"
 * </pre>
 *
 * Thus, the {@code inner} field can hold arbitrary data.
 *
 * @author Lyubomyr Shaydariv
 * @see UnpackedJsonTypeAdapterFactory
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PackedJsonTypeAdapter
		extends TypeAdapter<String> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<String> instance = new PackedJsonTypeAdapter()
			.nullSafe();

	@Override
	public void write(final JsonWriter out, final String json)
			throws IOException {
		try ( final Reader reader = new StringReader(json) ) {
			JsonStreams.copyTo(new JsonReader(reader), out);
		}
	}

	@Override
	public String read(final JsonReader in)
			throws IOException {
		try ( final Writer writer = new StringWriter() ) {
			JsonStreams.copyTo(in, new JsonWriter(writer));
			return writer.toString();
		}
	}

}
