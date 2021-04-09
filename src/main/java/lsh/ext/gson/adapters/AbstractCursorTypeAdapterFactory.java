package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.ext.java.util.stream.StreamTypeAdapter;

/**
 * Represents a base type adapter factory for cursor classes like iterators and streams.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapter
 * @since 0-SNAPSHOT
 */
public abstract class AbstractCursorTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<E> {

	private final Class<?> cursorClass;

	protected AbstractCursorTypeAdapterFactory(final Class<?> cursorClass) {
		this.cursorClass = cursorClass;
	}

	@Nonnull
	protected abstract TypeAdapter<?> createCursorTypeAdapter(@Nonnull TypeAdapter<?> elementTypeAdapter);

	@Override
	protected final boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return cursorClass.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected final TypeAdapter<E> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(typeArguments[0][0]));
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> castTypeAdapter = (TypeAdapter<E>) createCursorTypeAdapter(elementTypeAdapter);
		return castTypeAdapter;
	}

}
