package lsh.ext.gson.ext.java.util.stream;

import java.util.Iterator;
import java.util.stream.Stream;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

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

	public static final class Adapter<E>
			extends AbstractAdapter<Stream<E>, E> {

		private Adapter(final TypeAdapter<E> elementTypeAdapter) {
			super(elementTypeAdapter);
		}

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
