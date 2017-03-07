package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import static lsh.ext.gson.ParameterizedTypes.resolveTypeArguments;
import static lsh.ext.gson.adapters.IteratorTypeAdapter.getIteratorTypeAdapter;

/**
 * Represents a type adapter factory for {@link Iterator} .
 *
 * @author Lyubomyr Shaydariv
 * @see IteratorTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class IteratorTypeAdapterFactory
		implements TypeAdapterFactory {

	private static final TypeAdapterFactory iteratorTypeAdapterFactory = new IteratorTypeAdapterFactory();

	private IteratorTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link IteratorTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getIteratorTypeAdapterFactory() {
		return iteratorTypeAdapterFactory;
	}

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( Iterator.class.isAssignableFrom(typeToken.getRawType()) ) {
			final Type[][] typeArguments = resolveTypeArguments(typeToken.getType());
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) getIteratorTypeAdapter(typeArguments[0][0], gson);
			return castTypeAdapter;
		}
		return null;
	}

}
