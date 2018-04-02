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
	public static TypeAdapterFactory get() {
		return iteratorTypeAdapterFactory;
	}

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( Iterator.class.isAssignableFrom(typeToken.getRawType()) ) {
			final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
			@SuppressWarnings("unchecked")
			final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) IteratorTypeAdapter.get(typeArguments[0][0], gson);
			return castTypeAdapter;
		}
		return null;
	}

}
