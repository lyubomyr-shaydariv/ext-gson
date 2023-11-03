package lsh.ext.gson.ext.java.util.stream;

import java.util.Iterator;
import java.util.stream.Stream;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class StreamTypeAdapter<E>
		extends AbstractCursorTypeAdapter<Stream<? extends E>, E> {

	private StreamTypeAdapter(final TypeAdapter<E> elementTypeAdapter) {
		super(elementTypeAdapter);
	}

	public static <E> TypeAdapter<Stream<? extends E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
		return new StreamTypeAdapter<>(elementTypeAdapter)
				.nullSafe();
	}

	@Override
	protected Iterator<E> toIterator(final Stream<? extends E> stream) {
		@SuppressWarnings("unchecked")
		final Iterator<E> castIterator = (Iterator<E>) stream.iterator();
		return castIterator;
	}

	@Override
	protected Stream<E> fromIterator(final Iterator<? extends E> iterator) {
		return Streams.from(iterator);
	}

	public static final class Factory<E>
			extends AbstractCursorTypeAdapter.AbstractFactory<E> {

		@Getter
		private static final ITypeAdapterFactory<? extends Stream<?>> instance = new Factory<>();

		private Factory() {
			super(Stream.class);
		}

		@Override
		protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
			return StreamTypeAdapter.getInstance(elementTypeAdapter);
		}

	}

}
