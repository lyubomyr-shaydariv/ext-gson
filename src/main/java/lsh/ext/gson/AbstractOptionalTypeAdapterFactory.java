package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents an abstract type adapter factory for <i>optional</i> wrappers.
 *
 * @param <O>
 * 		Optional type
 * @param <T>
 * 		Value held by optional type
 *
 * @author Lyubomyr Shaydariv
 */
public abstract class AbstractOptionalTypeAdapterFactory<O, T>
		extends AbstractTypeAdapterFactory<O> {

	/**
	 * @param valueTypeAdapter
	 * 		Value type adapter
	 *
	 * @return A type adapter for the optional values that can be held by the given type adapter
	 */
	protected abstract TypeAdapter<O> from(TypeAdapter<T> valueTypeAdapter);

	@Override
	protected final TypeAdapter<O> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type parameterType = typeArguments[0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(parameterType));
		return from(typeAdapter);
	}

	/**
	 * Represents an abstract type adapter factory for <i>optional</i> wrappers.
	 *
	 * @param <O>
	 * 		Optional type
	 * @param <T>
	 * 		Value held by optional type
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	protected abstract static class Adapter<O, T>
			extends TypeAdapter<O> {

		private final TypeAdapter<T> valueTypeAdapter;

		/**
		 * @param optional
		 * 		Optional to get a value from
		 *
		 * @return A value from the given optional.
		 */
		@Nullable
		protected abstract T fromOptional(O optional);

		/**
		 * @param value
		 * 		Value to get an optional from
		 *
		 * @return An optional from the given value.
		 */
		protected abstract O toOptional(@Nullable T value);

		@Override
		public final void write(final JsonWriter out, final O optional)
				throws IOException {
			@Nullable
			final T value = fromOptional(optional);
			valueTypeAdapter.write(out, value);
		}

		@Override
		public final O read(final JsonReader in)
				throws IOException {
			@Nullable
			final T value = valueTypeAdapter.read(in);
			return toOptional(value);
		}

	}

}
