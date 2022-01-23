package lsh.ext.gson.adapters;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lsh.ext.gson.JsonStreams;

/**
 * <p>
 * This type adapter is designed to "pack" child JSON properties as strings.
 * For example, suppose the following mapping:
 * </p>
 *
 * <pre>
 * final class Outer {
 *    {@literal @}JsonAdapter(PackedJsonTypeAdapter.class)
 *     final String inner = null;
 * }
 * </pre>
 *
 * <p>
 * and the following JSON document:
 * </p>
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
 * <p>
 * After the {@code inner} field is deserialized, if the whole JSON document is deserialized as {@code Outer}, its value is
 * </p>
 *
 * <pre>
 * "{\"foo\":1,\"bar\":2}"
 * </pre>
 *
 * <p>
 * Thus, the {@code inner} field can hold arbitrary data.
 * </p>
 *
 * @author Lyubomyr Shaydariv
 * @see UnpackedJsonTypeAdapterFactory
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PackedJsonTypeAdapter
		extends TypeAdapter<String> {

	private static final TypeAdapter<String> instance = new PackedJsonTypeAdapter()
			.nullSafe();

	/**
	 * @return An instance of {@link PackedJsonTypeAdapter}.
	 */
	public static TypeAdapter<String> getInstance() {
		return instance;
	}

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
