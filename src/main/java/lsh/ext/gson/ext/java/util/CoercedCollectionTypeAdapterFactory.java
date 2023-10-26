package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
public final class CoercedCollectionTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Collection<E>> {

	@Getter
	private static final TypeAdapterFactory instance = new CoercedCollectionTypeAdapterFactory<>(Collection.class, ArrayList::new);

	private final Class<? super Collection<E>> baseCollectionType;
	private final IInstanceFactory<? extends Collection<E>> collectionFactory;

	public static <E> TypeAdapterFactory getInstance(
			final Class<? super Collection<E>> baseCollectionType,
			final IInstanceFactory<? extends Collection<E>> collectionFactory
	) {
		return new CoercedCollectionTypeAdapterFactory<>(baseCollectionType, collectionFactory);
	}

	@Override
	protected TypeAdapter<Collection<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !baseCollectionType.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(Object.class));
		return Adapter.getInstance(elementTypeAdapter, collectionFactory);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Collection<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		private final IInstanceFactory<? extends Collection<E>> collectionFactory;

		public static <E> TypeAdapter<Collection<E>> getInstance(
				final TypeAdapter<E> elementTypeAdapter,
				final IInstanceFactory<? extends Collection<E>> collectionFactory
		) {
			return new Adapter<E>(elementTypeAdapter, collectionFactory)
					.nullSafe();
		}

		@Override
		public void write(final JsonWriter out, final Collection<E> collection)
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
		public Collection<E> read(final JsonReader in)
				throws IOException {
			final Collection<E> collection = collectionFactory.createInstance();
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
