package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalInt;
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
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalIntTypeAdapter
		extends TypeAdapter<OptionalInt> {

	@Getter
	private static final TypeAdapter<OptionalInt> instance = new OptionalIntTypeAdapter();

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

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<OptionalInt> {

		@Getter
		private static final ITypeAdapterFactory<OptionalInt> instance = new Factory(OptionalIntTypeAdapter.instance);

		private final TypeAdapter<OptionalInt> typeAdapter;

		private Factory(final TypeAdapter<OptionalInt> typeAdapter) {
			super(OptionalInt.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<OptionalInt> getInstance(final TypeAdapter<OptionalInt> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<OptionalInt> createTypeAdapter(final Gson gson, final TypeToken<? super OptionalInt> typeToken) {
			return typeAdapter;
		}

	}

}
