package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

/**
 * Represents a type adapter that can convert a single value to a list or keep an existing list of multiple elements.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @since 0.1-SNAPSHOT
 */
public final class AlwaysListTypeAdapter<E>
		extends TypeAdapter<List<E>> {

	private final TypeAdapter<E> elementTypeAdapter;

	private AlwaysListTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	/**
	 * @param elementTypeAdapter Element type adapter for every list element
	 * @param <E>                Element type
	 *
	 * @return An instance of {@link AlwaysListTypeAdapter}.
	 *
	 * @since 0.1-SNAPSHOT
	 */
	public static <E> TypeAdapter<List<E>> getAlwaysListTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		return new AlwaysListTypeAdapter<>(elementTypeAdapter);
	}

	@Override
	@SuppressWarnings("resource")
	public void write(final JsonWriter out, final List<E> list)
			throws IOException {
		if ( list == null ) {
			out.nullValue();
		} else {
			switch ( list.size() ) {
			case 0:
				out.beginArray();
				out.endArray();
				break;
			case 1:
				elementTypeAdapter.write(out, list.iterator().next());
				break;
			default:
				out.beginArray();
				for ( final E element : list ) {
					elementTypeAdapter.write(out, element);
				}
				out.endArray();
				break;
			}
		}
	}

	@Override
	public List<E> read(final JsonReader in)
			throws IOException {
		final List<E> list = new ArrayList<>();
		final JsonToken token = in.peek();
		switch ( token ) {
		case BEGIN_ARRAY:
			in.beginArray();
			while ( in.hasNext() ) {
				list.add(elementTypeAdapter.read(in));
			}
			in.endArray();
			break;
		case BEGIN_OBJECT:
		case STRING:
		case NUMBER:
		case BOOLEAN:
			list.add(elementTypeAdapter.read(in));
			break;
		case NULL:
			return null;
		case NAME:
		case END_ARRAY:
		case END_OBJECT:
		case END_DOCUMENT:
			throw new MalformedJsonException("Unexpected token: " + token);
		default:
			throw new AssertionError("Must never happen: " + token);
		}
		return list;
	}

}
