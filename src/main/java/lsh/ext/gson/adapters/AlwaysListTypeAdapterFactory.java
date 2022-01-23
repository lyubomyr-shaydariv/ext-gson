package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for single values that can be converted to a list or keep an existing list of multiple elements.
 *
 * @author Lyubomyr Shaydariv
 */
public final class AlwaysListTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<List<E>> {

	private static final TypeAdapterFactory defaultInstance = new AlwaysListTypeAdapterFactory<>();

	private AlwaysListTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link AlwaysListTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return List.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<List<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type elementType = ParameterizedTypes.getTypeArguments(typeToken.getType())[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		return AlwaysListTypeAdapter.get(elementTypeAdapter);
	}

}
