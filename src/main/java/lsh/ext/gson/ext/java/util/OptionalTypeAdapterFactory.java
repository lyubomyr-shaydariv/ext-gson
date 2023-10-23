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

/**
 * Represents a type adapter factory for {@link Optional}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<Optional<T>>
		implements ITypeAdapterFactory<Optional<T>> {

	@Getter
	private static final ITypeAdapterFactory<? extends Optional<?>> instance = new OptionalTypeAdapterFactory<>();

	@Override
	protected TypeAdapter<Optional<T>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( typeToken.getRawType() != Optional.class ) {
			return null;
		}
		@Nullable
		final Type parameterType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
		assert parameterType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(parameterType));
		return Adapter.getInstance(typeAdapter);
	}

	/**
	 * Represents a type adapter for {@link Optional}.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<T>
			extends TypeAdapter<Optional<T>> {

		private final TypeAdapter<T> valueTypeAdapter;

		/**
		 * @param valueTypeAdapter
		 *        {@link Optional} backed value type adapter
		 * @param <T>
		 * 		Optional value type
		 *
		 * @return An {@link Adapter} instance.
		 */
		public static <T> TypeAdapter<Optional<T>> getInstance(final TypeAdapter<T> valueTypeAdapter) {
			return new Adapter<>(valueTypeAdapter)
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

	}

}
