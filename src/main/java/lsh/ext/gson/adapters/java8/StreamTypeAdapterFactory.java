package lsh.ext.gson.adapters.java8;

import java.lang.reflect.Type;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Stream}.
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class StreamTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<T> {

	private static final TypeAdapterFactory instance = new StreamTypeAdapterFactory<>();

	private StreamTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link StreamTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Stream.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<T> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(typeArguments[0][0]));
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) StreamTypeAdapter.get(elementTypeAdapter);
		return castTypeAdapter;
	}

}
