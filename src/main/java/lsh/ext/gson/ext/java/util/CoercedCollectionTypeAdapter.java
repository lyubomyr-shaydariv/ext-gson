package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder1;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapter<E>
		extends TypeAdapter<Collection<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends IBuilder1<? super E, ? extends Collection<E>>> builderFactory;

	public static <E> TypeAdapter<Collection<E>> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Collection<E>>> builderFactory
	) {
		return new CoercedCollectionTypeAdapter<E>(elementTypeAdapter, builderFactory)
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
		final IBuilder1<? super E, ? extends Collection<E>> builder = builderFactory.get();
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

}
