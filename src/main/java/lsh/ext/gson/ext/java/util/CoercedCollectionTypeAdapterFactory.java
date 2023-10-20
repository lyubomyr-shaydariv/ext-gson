package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

/**
 * Represents a type adapter factory for single values that can be converted to a collection or keep an existing collection of multiple elements.
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
	private final Adapter.IFactory<? extends E, C> collectionFactory;

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
			final Adapter.IFactory<? extends E, C> collectionFactory) {
		return new CoercedCollectionTypeAdapterFactory<>(baseCollectionType, collectionFactory);
	}

	@Override
	protected TypeAdapter<C> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !baseCollectionType.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(Object.class));
		return Adapter.getInstance(elementTypeAdapter, collectionFactory);
	}

	/**
	 * Represents a type adapter that can convert a single value to a collection or keep an existing collection of multiple elements.
	 *
	 * @param <E>
	 * 		Element type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E, C extends Collection<E>>
			extends TypeAdapter<C> {

		public interface IFactory<E, C extends Collection<E>> {

			C createCollection();

		}

		private final TypeAdapter<E> elementTypeAdapter;
		private final IFactory<? extends E, ? extends C> collectionFactory;

		/**
		 * @param elementTypeAdapter
		 * 		Element type adapter for every list element
		 * @param collectionFactory
		 * 		A factory to create a new collection
		 * @param <E>
		 * 		Element type
		 *
		 * @return An instance of {@link Adapter}.
		 */
		public static <E, C extends Collection<E>> TypeAdapter<C> getInstance(final TypeAdapter<E> elementTypeAdapter, final IFactory<? extends E, C> collectionFactory) {
			return new Adapter<>(elementTypeAdapter, collectionFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final C collection)
				throws IOException {
			switch ( collection.size() ) {
			case 0:
				out.beginArray();
				out.endArray();
				break;
			case 1:
				elementTypeAdapter.write(out, collection.iterator().next());
				break;
			default:
				out.beginArray();
				for ( final E element : collection ) {
					elementTypeAdapter.write(out, element);
				}
				out.endArray();
				break;
			}
		}

		@Override
		public C read(final JsonReader in)
				throws IOException {
			final C collection = collectionFactory.createCollection();
			final JsonToken token = in.peek();
			switch ( token ) {
			case BEGIN_ARRAY:
				in.beginArray();
				while ( in.hasNext() ) {
					collection.add(elementTypeAdapter.read(in));
				}
				in.endArray();
				break;
			case BEGIN_OBJECT:
			case STRING:
			case NUMBER:
			case BOOLEAN:
				collection.add(elementTypeAdapter.read(in));
				break;
			case NULL:
				throw new AssertionError();
			case NAME:
			case END_ARRAY:
			case END_OBJECT:
			case END_DOCUMENT:
				throw new MalformedJsonException("Unexpected token: " + token);
			default:
				throw new AssertionError(token);
			}
			return collection;
		}

	}

}
