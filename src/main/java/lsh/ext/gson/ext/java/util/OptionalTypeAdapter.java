package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalTypeAdapter<T>
		extends TypeAdapter<Optional<T>> {

	private final TypeAdapter<T> valueTypeAdapter;

	public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
		return new OptionalTypeAdapter<>(valueTypeAdapter)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Optional<T> optional)
			throws IOException {
		@Nullable
		final T value = optional.orElse(null);
		if ( value == null ) {
			out.nullValue();
			return;
		}
		valueTypeAdapter.write(out, value);
	}

	@Override
	@SuppressWarnings("OptionalOfNullableMisuse")
	public Optional<T> read(final JsonReader in)
			throws IOException {
		@Nullable
		final T value = valueTypeAdapter.read(in);
		return Optional.ofNullable(value);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<T>
			extends AbstractTypeAdapterFactory<Optional<T>> {

		@Getter
		private static final ITypeAdapterFactory<? extends Optional<?>> instance = new Factory<>();

		@Override
		@Nullable
		protected TypeAdapter<Optional<T>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( typeToken.getRawType() != Optional.class ) {
				return null;
			}
			@Nullable
			final Type parameterType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert parameterType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(parameterType));
			return OptionalTypeAdapter.getInstance(typeAdapter);
		}

	}

}
