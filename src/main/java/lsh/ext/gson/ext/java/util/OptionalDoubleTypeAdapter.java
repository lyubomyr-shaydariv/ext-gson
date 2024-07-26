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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalDoubleTypeAdapter
		extends TypeAdapter<OptionalDouble> {

	@Getter
	private static final TypeAdapter<OptionalDouble> instance = new OptionalDoubleTypeAdapter();

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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			extends AbstractTypeAdapterFactory<OptionalDouble> {

		@Getter
		private static final ITypeAdapterFactory<OptionalDouble> instance = new Factory();

		@Override
		@Nullable
		protected TypeAdapter<OptionalDouble> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( typeToken.getRawType() != OptionalDouble.class ) {
				return null;
			}
			return OptionalDoubleTypeAdapter.getInstance();
		}

	}

}
