package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.Iterator;

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
public final class AutoCloseableIteratorTypeAdapterFactory
		implements TypeAdapterFactory {

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
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( Iterator.class.isAssignableFrom(typeToken.getRawType()) ) {
			final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) AutoCloseableIteratorTypeAdapter.get(typeArguments[0][0], gson);
			return castTypeAdapter;
		}
		return null;
	}

}
