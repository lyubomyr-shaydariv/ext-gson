package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.ext.java.ICloseableIterator;

/**
 * Represents a base type adapter factory for cursor classes like iterators and streams.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCursorTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<E> {

	private final Class<?> cursorClass;

	protected abstract TypeAdapter<?> createCursorTypeAdapter(TypeAdapter<?> elementTypeAdapter);

	@Override
	protected final boolean supports(final TypeToken<?> typeToken) {
		return cursorClass.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected final TypeAdapter<E> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		@Nullable
		final Type typeArgument = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
		assert typeArgument != null;
		final TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(typeArgument));
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> castTypeAdapter = (TypeAdapter<E>) createCursorTypeAdapter(elementTypeAdapter);
		return castTypeAdapter;
	}

	/**
	 * Represents a base type adapter for cursor classes like iterators and streams.
	 *
	 * @param <C>
	 * 		Cursor type
	 * @param <E>
	 * 		Element type
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	protected abstract static class Adapter<C, E>
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

}
