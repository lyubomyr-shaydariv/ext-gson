package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.Iterator;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Iterator} .
 *
 * @author Lyubomyr Shaydariv
 * @see AutoCloseableIteratorTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class AutoCloseableIteratorTypeAdapterFactory<T>
		extends AbstractBoundTypeAdapterFactory<T> {

	private static final TypeAdapterFactory instance = new AutoCloseableIteratorTypeAdapterFactory();

	private AutoCloseableIteratorTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link AutoCloseableIteratorTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Iterator.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<T> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) AutoCloseableIteratorTypeAdapter.get(typeArguments[0][0], gson);
		return castTypeAdapter;
	}

}
