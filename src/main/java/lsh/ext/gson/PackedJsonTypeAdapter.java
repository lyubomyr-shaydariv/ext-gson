package lsh.ext.gson;

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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PackedJsonTypeAdapter
		extends TypeAdapter<String> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<String> instance = new PackedJsonTypeAdapter()
			.nullSafe();

	@Override
	public void write(final JsonWriter out, final String json)
			throws IOException {
		try ( Reader reader = new StringReader(json) ) {
			JsonStreams.copyTo(new JsonReader(reader), out);
		}
	}

	@Override
	public String read(final JsonReader in)
			throws IOException {
		try ( Writer writer = new StringWriter() ) {
			JsonStreams.copyTo(in, new JsonWriter(writer));
			return writer.toString();
		}
	}

}
