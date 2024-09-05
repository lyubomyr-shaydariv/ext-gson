package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalDouble;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

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

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<OptionalDouble> {

		@Getter
		private static final ITypeAdapterFactory<OptionalDouble> instance = getInstance(OptionalDoubleTypeAdapter.getInstance());

		private final TypeAdapter<OptionalDouble> typeAdapter;

		private Factory(final TypeAdapter<OptionalDouble> typeAdapter) {
			super(OptionalDouble.class);
			this.typeAdapter = typeAdapter;
		}

		private static ITypeAdapterFactory<OptionalDouble> getInstance(final TypeAdapter<OptionalDouble> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<OptionalDouble> createTypeAdapter(final Gson gson, final TypeToken<? super OptionalDouble> typeToken) {
			return typeAdapter;
		}

	}

}
