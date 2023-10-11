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
import lsh.ext.gson.IInstanceFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapterFactory<E, C extends Collection<E>>
		extends AbstractTypeAdapterFactory<C> {

	@Getter
	private static final TypeAdapterFactory instance = new CoercedCollectionTypeAdapterFactory<>(List.class, ArrayList::new);

	private final Class<? super C> baseCollectionType;
	private final IInstanceFactory<? extends C> collectionFactory;

	public static <E, C extends Collection<E>> TypeAdapterFactory getInstance(final Class<? super C> baseCollectionType,
			final IInstanceFactory<? extends C> collectionFactory) {
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

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E, C extends Collection<E>>
			extends TypeAdapter<C> {

		private final TypeAdapter<E> elementTypeAdapter;
		private final IInstanceFactory<? extends C> collectionFactory;

		public static <E, C extends Collection<E>> TypeAdapter<C> getInstance(final TypeAdapter<E> elementTypeAdapter, final IInstanceFactory<? extends C> collectionFactory) {
			return new Adapter<E, C>(elementTypeAdapter, collectionFactory)
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
		@SuppressWarnings("checkstyle:CyclomaticComplexity")
		public C read(final JsonReader in)
				throws IOException {
			final C collection = collectionFactory.createInstance();
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
