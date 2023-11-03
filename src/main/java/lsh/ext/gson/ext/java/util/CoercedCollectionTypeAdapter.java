package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.CollectionBuilder;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IFactory0;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapter<E>
		extends TypeAdapter<Collection<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends IBuilder1<? super E, ? extends Collection<E>>> collectionBuilderFactory;

	public static <E> TypeAdapter<Collection<E>> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Collection<E>>> collectionBuilderFactory
	) {
		return new CoercedCollectionTypeAdapter<E>(elementTypeAdapter, collectionBuilderFactory)
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
		final IBuilder1<? super E, ? extends Collection<E>> builder = collectionBuilderFactory.get();
		final JsonToken token = in.peek();
		switch ( token ) {
		case BEGIN_ARRAY:
			in.beginArray();
			while ( in.hasNext() ) {
				builder.accept(elementTypeAdapter.read(in));
			}
			in.endArray();
			break;
		case BEGIN_OBJECT:
		case STRING:
		case NUMBER:
		case BOOLEAN:
			builder.accept(elementTypeAdapter.read(in));
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
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<Collection<E>> {

		private final Class<? extends Collection<E>> baseCollectionType;
		private final TypeToken<E> elementTypeToken;
		private final IBuilder1.IFactory<? super E, ? extends Collection<E>> collectionBuilderFactory;

		public static <E> TypeAdapterFactory getInstance(
				final TypeToken<E> elementTypeToken
		) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Class<? extends Collection<E>> o = (Class) Collection.class;
			return getInstance(o, elementTypeToken, (IFactory0.IFactory<Collection<E>>) typeToken -> ArrayList::new);
		}

		public static <E> TypeAdapterFactory getInstance(
				final Class<? extends Collection<E>> baseCollectionType,
				final TypeToken<E> elementTypeToken,
				final IFactory0.IFactory<Collection<E>> factoryFactory
		) {
			return getInstance(baseCollectionType, elementTypeToken, (IBuilder1.IFactory<E, Collection<E>>) typeToken -> builder(factoryFactory.create(typeToken)));
		}

		public static <E> TypeAdapterFactory getInstance(
				final Class<? extends Collection<E>> baseCollectionType,
				final TypeToken<E> elementTypeToken,
				final IBuilder1.IFactory<? super E, ? extends Collection<E>> collectionBuilderFactory
		) {
			return new Factory<>(baseCollectionType, elementTypeToken, collectionBuilderFactory);
		}

		public static <E> IBuilder1<E, Collection<E>> builder(
				final IFactory0<? extends Collection<E>> factory
		) {
			return CollectionBuilder.getInstance(factory.create());
		}

		@Override
		protected TypeAdapter<Collection<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !baseCollectionType.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			final TypeAdapter<E> elementTypeAdapter = gson.getAdapter(elementTypeToken);
			@SuppressWarnings("unchecked")
			final TypeToken<Collection<E>> castTypeToken = (TypeToken<Collection<E>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder1.IFactory<E, Collection<E>> castCollectionBuilderFactory = (IBuilder1.IFactory<E, Collection<E>>) collectionBuilderFactory;
			return CoercedCollectionTypeAdapter.getInstance(elementTypeAdapter, () -> castCollectionBuilderFactory.create(castTypeToken));
		}

	}

}
