package lsh.ext.gson.ext.java.util.stream;

import java.util.Iterator;
import java.util.stream.Stream;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Stream}.
 *
 * @param <E>
 * 		Element type
 */
public final class StreamTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E>
		implements ITypeAdapterFactory<Stream<E>> {

	@Getter
	private static final ITypeAdapterFactory<? extends Stream<?>> instance = new StreamTypeAdapterFactory<>();

	private StreamTypeAdapterFactory() {
		super(Stream.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return Adapter.getInstance(elementTypeAdapter);
	}

	/**
	 * Type adapter for {@link Stream}. Streams are supposed to read and write JSON arrays only.
	 */
	public static final class Adapter<E>
			extends AbstractCursorTypeAdapterFactory.Adapter<Stream<E>, E> {

		private Adapter(final TypeAdapter<E> elementTypeAdapter) {
			super(elementTypeAdapter);
		}

		/**
		 * @param elementTypeAdapter
		 * 		Element type adapter
		 * @param <E>
		 * 		Iterator element type
		 *
		 * @return An instance of {@link Adapter}.
		 */
		public static <E> TypeAdapter<Stream<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter)
					.nullSafe();
		}

		@Override
		protected Iterator<E> toIterator(final Stream<E> stream) {
			return stream.iterator();
		}

		@Override
		protected Stream<E> fromIterator(final Iterator<E> iterator) {
			return Streams.from(iterator);
		}

	}

}
