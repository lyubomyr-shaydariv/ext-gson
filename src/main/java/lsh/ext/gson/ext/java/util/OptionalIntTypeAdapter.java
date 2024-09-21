package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalInt;
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
public final class OptionalIntTypeAdapter
		extends TypeAdapter<OptionalInt> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<OptionalInt> instance = new OptionalIntTypeAdapter()
			.nullSafe();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public void write(final JsonWriter out, @Nullable final OptionalInt optional)
			throws IOException {
		if ( optional == null || optional.isEmpty() ) {
			out.nullValue();
			return;
		}
		final int value = optional.getAsInt();
		out.value(value);
	}

	@Override
	public OptionalInt read(final JsonReader in)
			throws IOException {
		if ( in.peek() == JsonToken.NULL ) {
			in.nextNull();
			return OptionalInt.empty();
		}
		return OptionalInt.of(in.nextInt());
	}

}
