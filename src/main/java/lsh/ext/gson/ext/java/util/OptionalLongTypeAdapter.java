package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.OptionalLong;
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
public final class OptionalLongTypeAdapter
		extends TypeAdapter<OptionalLong> {

	@Getter
	private static final TypeAdapter<OptionalLong> instance = new OptionalLongTypeAdapter();

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

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<OptionalLong> {

		@Getter
		private static final ITypeAdapterFactory<OptionalLong> instance = new Factory(OptionalLongTypeAdapter.instance);

		private final TypeAdapter<OptionalLong> typeAdapter;

		private Factory(final TypeAdapter<OptionalLong> typeAdapter) {
			super(OptionalLong.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<OptionalLong> getInstance(final TypeAdapter<OptionalLong> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<OptionalLong> createTypeAdapter(final Gson gson, final TypeToken<OptionalLong> typeToken) {
			return typeAdapter;
		}

	}

}
