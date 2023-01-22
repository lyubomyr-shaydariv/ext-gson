package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Collection;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter that can convert a single value to a collection or keep an existing collection of multiple elements.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoercedCollectionTypeAdapter<E, C extends Collection<E>>
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
	 * @return An instance of {@link CoercedCollectionTypeAdapter}.
	 */
	public static <E, C extends Collection<E>> TypeAdapter<C> getInstance(final TypeAdapter<E> elementTypeAdapter, final IFactory<? extends E, C> collectionFactory) {
		return new CoercedCollectionTypeAdapter<>(elementTypeAdapter, collectionFactory);
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
			throw new AssertionError("Must never happen for nulls");
		case NAME:
		case END_ARRAY:
		case END_OBJECT:
		case END_DOCUMENT:
			throw new MalformedJsonException("Unexpected token: " + token);
		default:
			throw new AssertionError("Must never happen: " + token);
		}
		return collection;
	}

}
