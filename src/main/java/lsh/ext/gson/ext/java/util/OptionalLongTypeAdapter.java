package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalLong;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalLongTypeAdapter
		extends TypeAdapter<OptionalLong> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<OptionalLong> instance = new OptionalLongTypeAdapter()
			.nullSafe();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public void write(final JsonWriter out, @Nullable final OptionalLong optional)
			throws IOException {
		if ( optional == null || optional.isEmpty() ) {
			out.nullValue();
			return;
		}
		final long value = optional.getAsLong();
		out.value(value);
	}

	@Override
	public OptionalLong read(final JsonReader in)
			throws IOException {
		if ( in.peek() == JsonToken.NULL ) {
			in.nextNull();
			return OptionalLong.empty();
		}
		return OptionalLong.of(in.nextLong());
	}

}
