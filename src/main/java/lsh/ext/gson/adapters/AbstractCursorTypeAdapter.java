package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Iterator;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.ICloseableIterator;
import lsh.ext.gson.adapters.java8.StreamTypeAdapter;

/**
 * Represents a base type adapter for cursor classes like iterators and streams.
 *
 * @param <C> Cursor type
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see StreamTypeAdapter
 * @since 0-SNAPSHOT
 */
public abstract class AbstractCursorTypeAdapter<C, E>
		extends TypeAdapter<C> {

	private final TypeAdapter<E> elementTypeAdapter;

	protected AbstractCursorTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		this.elementTypeAdapter = elementTypeAdapter;
	}

	@Nonnull
	protected abstract Iterator<? extends E> toIterator(@Nonnull C cursor);

	@Nonnull
	protected abstract C fromIterator(@Nonnull ICloseableIterator<? extends E> iterator);

	@Override
	@SuppressWarnings("resource")
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
		final ICloseableIterator<E> closeableIterator = JsonReaderIterator.get(elementTypeAdapter, in);
		return fromIterator(closeableIterator);
	}

}
