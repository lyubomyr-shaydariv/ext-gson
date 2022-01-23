package lsh.ext.gson.ext.com.google.common.collect;

import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.ParameterizedTypes;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Multiset} from Google Guava.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see MultisetTypeAdapter
 */
public final class MultisetTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Multiset<E>> {

	private static final TypeAdapterFactory instance = new MultisetTypeAdapterFactory<>(null);

	@Nullable
	private final Supplier<? extends Multiset<E>> newMultisetFactory;

	private MultisetTypeAdapterFactory(@Nullable final Supplier<? extends Multiset<E>> newMultisetFactory) {
		this.newMultisetFactory = newMultisetFactory;
	}

	/**
	 * @return An instance of {@link MultisetTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param newMultisetFactory Multiset factory
	 * @param <E>                Element type
	 *
	 * @return An instance of {@link MultisetTypeAdapterFactory} with a custom new {@link Multiset} factory.
	 */
	public static <E> TypeAdapterFactory create(@Nullable final Supplier<? extends Multiset<E>> newMultisetFactory) {
		if ( newMultisetFactory == null ) {
			return instance;
		}
		return new MultisetTypeAdapterFactory<>(newMultisetFactory);
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return Multiset.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<Multiset<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type elementType = typeArguments[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		if ( newMultisetFactory == null ) {
			return MultisetTypeAdapter.create(elementTypeAdapter);
		}
		return MultisetTypeAdapter.create(elementTypeAdapter, newMultisetFactory);
	}

}
