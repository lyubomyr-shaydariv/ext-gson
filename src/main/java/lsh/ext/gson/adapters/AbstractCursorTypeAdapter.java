package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ext.java.ICloseableIterator;
import lsh.ext.gson.ext.java.util.stream.StreamTypeAdapter;

/**
 * Represents a base type adapter for cursor classes like iterators and streams.
 *
 * @param <C>
 * 		Cursor type
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCursorTypeAdapter<C, E>
		extends TypeAdapter<C> {

	private final TypeAdapter<E> elementTypeAdapter;

	protected abstract Iterator<E> toIterator(C cursor);

	protected abstract C fromIterator(ICloseableIterator<E> iterator);

	@Override
	public final void write(final JsonWriter out, final C cursor)
			throws IOException {
		out.beginArray();
		final Iterator<? extends E> iterator = toIterator(cursor);
		while ( iterator.hasNext() ) {
			final E element = iterator.next();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public final C read(final JsonReader in) {
		final ICloseableIterator<E> closeableIterator = JsonReaderIterator.getInstance(elementTypeAdapter, in);
		return fromIterator(closeableIterator);
	}

}
