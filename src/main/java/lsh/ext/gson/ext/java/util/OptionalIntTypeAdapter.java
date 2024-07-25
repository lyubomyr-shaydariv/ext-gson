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
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalIntTypeAdapter
		extends TypeAdapter<OptionalInt> {

	public static TypeAdapter<OptionalInt> getInstance() {
		return new OptionalIntTypeAdapter();
	}

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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory
			extends AbstractTypeAdapterFactory<OptionalInt> {

		@Getter
		private static final ITypeAdapterFactory<OptionalInt> instance = new Factory();

		@Override
		@Nullable
		protected TypeAdapter<OptionalInt> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( typeToken.getRawType() != OptionalInt.class ) {
				return null;
			}
			return OptionalIntTypeAdapter.getInstance();
		}

	}

}
