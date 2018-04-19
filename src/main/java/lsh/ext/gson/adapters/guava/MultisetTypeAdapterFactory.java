package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractBoundTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Multiset} from Google Guava.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see MultisetTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class MultisetTypeAdapterFactory<E>
		extends AbstractBoundTypeAdapterFactory<Multiset<E>> {

	private static final TypeAdapterFactory instance = new MultisetTypeAdapterFactory<>();

	private MultisetTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MultisetTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Multiset.class.isAssignableFrom(typeToken.getRawType());
	}

	@Nonnull
	@Override
	protected TypeAdapter<Multiset<E>> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type elementType = typeArguments[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		return MultisetTypeAdapter.get(elementTypeAdapter);
	}

}
