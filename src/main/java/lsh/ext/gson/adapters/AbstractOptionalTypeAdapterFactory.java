package lsh.ext.gson.adapters;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;

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
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type parameterType = typeArguments[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) gson.getAdapter(TypeToken.get(parameterType));
		return from(typeAdapter);
	}

}
