package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

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
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapter<E>
		extends TypeAdapter<Collection<? extends E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final IFactory<? extends IBuilder1<? super E, ? extends Collection<? extends E>>> builderFactory;

	public static <E> TypeAdapter<Collection<? extends E>> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final IFactory<? extends IBuilder1<? super E, ? extends Collection<? extends E>>> builderFactory
	) {
		return new CoercedCollectionTypeAdapter<E>(elementTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Collection<? extends E> collection)
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
	public Collection<? extends E> read(final JsonReader in)
			throws IOException {
		final IBuilder1<? super E, ? extends Collection<? extends E>> builder = builderFactory.create();
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
			extends AbstractTypeAdapterFactory<Collection<? extends E>> {

		private final TypeToken<E> elementTypeToken;
		private final IBuilder1.ILookup<? super E, ? extends Collection<E>> builderFactory;

		public static <E> TypeAdapterFactory getInstance(
				final TypeToken<E> elementTypeToken
		) {
			return new Factory<>(elementTypeToken, Factory::defaultBuilderFactory);
		}

		public static <E> TypeAdapterFactory getInstance(
				final TypeToken<E> elementTypeToken,
				final IBuilder1.ILookup<? super E, ? extends Collection<E>> builderFactory
		) {
			return new Factory<>(elementTypeToken, builderFactory);
		}

		public static <E> IFactory<IBuilder1<E, Collection<E>>> defaultBuilderFactory(final TypeToken<? super Collection<E>> typeToken) {
			final Class<? super Collection<E>> rawType = typeToken.getRawType();
			if ( rawType.isAssignableFrom(List.class) ) {
				return () -> IBuilder1.of(new ArrayList<>());
			}
			if ( rawType.isAssignableFrom(Set.class) ) {
				return () -> IBuilder1.of(new HashSet<>());
			}
			return () -> IBuilder1.of(new ArrayList<>());
		}

		@Override
		@Nullable
		protected TypeAdapter<Collection<? extends E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !Collection.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			final TypeAdapter<E> elementTypeAdapter = gson.getAdapter(elementTypeToken);
			@SuppressWarnings("unchecked")
			final TypeToken<Collection<E>> castTypeToken = (TypeToken<Collection<E>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder1.ILookup<E, Collection<E>> castBuilderLookup = (IBuilder1.ILookup<E, Collection<E>>) builderFactory;
			return CoercedCollectionTypeAdapter.getInstance(elementTypeAdapter, castBuilderLookup.lookup(castTypeToken));
		}

	}

}
