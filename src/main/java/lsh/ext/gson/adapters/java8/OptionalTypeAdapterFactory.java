package lsh.ext.gson.adapters.java8;

import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractBoundTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Optional} from Google Guava.
 *
 * @author Lyubomyr Shaydariv
 * @see OptionalTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class OptionalTypeAdapterFactory<T>
		extends AbstractBoundTypeAdapterFactory<Optional<T>> {

	private static final TypeAdapterFactory instance = new OptionalTypeAdapterFactory<>();

	private OptionalTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link OptionalTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Optional.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<Optional<T>> createTypeAdapter(final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type parameterType = typeArguments[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(parameterType));
		return OptionalTypeAdapter.get(typeAdapter);
	}

}
