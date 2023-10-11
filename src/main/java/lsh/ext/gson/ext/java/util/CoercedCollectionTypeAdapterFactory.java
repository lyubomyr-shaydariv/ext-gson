package lsh.ext.gson.ext.java.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for single values that can be converted to a collection or keep an existing collection of multiple elements.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapterFactory<E, C extends Collection<E>>
		extends AbstractTypeAdapterFactory<C> {

	/**
	 * An instance of {@link CoercedCollectionTypeAdapterFactory} based on {@link List} and {@link ArrayList}.
	 */
	@Getter
	private static final TypeAdapterFactory instance = new CoercedCollectionTypeAdapterFactory<>(List.class, ArrayList::new);

	private final Class<? super C> baseCollectionType;
	private final CoercedCollectionTypeAdapter.IFactory<? extends E, C> collectionFactory;

	/**
	 * @param baseCollectionType
	 * 		Collection super type to start with
	 * @param collectionFactory
	 * 		Collection factory
	 * @param <E>
	 * 		Collection element type
	 * @param <C>
	 * 		Collection type based on the element type
	 *
	 * @return An instance of {@link CoercedCollectionTypeAdapterFactory} based on {@link List} and {@link ArrayList}.
	 */
	public static <E, C extends Collection<E>> TypeAdapterFactory getInstance(final Class<? super C> baseCollectionType,
			final CoercedCollectionTypeAdapter.IFactory<? extends E, C> collectionFactory) {
		return new CoercedCollectionTypeAdapterFactory<>(baseCollectionType, collectionFactory);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return baseCollectionType.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<C> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type elementType = ParameterizedTypes.getTypeArguments(typeToken.getType())[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		return CoercedCollectionTypeAdapter.getInstance(elementTypeAdapter, collectionFactory);
	}

}
