package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalDouble;
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
public final class OptionalDoubleTypeAdapter
		extends TypeAdapter<OptionalDouble> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<OptionalDouble> instance = new OptionalDoubleTypeAdapter()
			.nullSafe();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public void write(final JsonWriter out, @Nullable final OptionalDouble optional)
			throws IOException {
		if ( optional == null || optional.isEmpty() ) {
			out.nullValue();
			return;
		}
		final double value = optional.getAsDouble();
		out.value(value);
	}

	@Override
	public OptionalDouble read(final JsonReader in)
			throws IOException {
		if ( in.peek() == JsonToken.NULL ) {
			in.nextNull();
			return OptionalDouble.empty();
		}
		return OptionalDouble.of(in.nextDouble());
	}

}
