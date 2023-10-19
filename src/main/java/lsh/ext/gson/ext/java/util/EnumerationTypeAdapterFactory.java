package lsh.ext.gson.ext.java.util;

import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E>
 * 		Element type
 */
public final class EnumerationTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	@Getter
	private static final TypeAdapterFactory instance = new EnumerationTypeAdapterFactory<>();

	private EnumerationTypeAdapterFactory() {
		super(Enumeration.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return Adapter.getInstance(elementTypeAdapter);
	}

	/**
	 * Type adapter for {@link Enumeration}. Enumerations are supposed to read and write JSON arrays only.
	 */
	public static final class Adapter<E>
			extends AbstractCursorTypeAdapterFactory.Adapter<Enumeration<E>, E> {

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
		public static <E> TypeAdapter<Enumeration<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter);
		}

		@Override
		protected Iterator<E> toIterator(final Enumeration<E> enumeration) {
			return Iterators.from(enumeration);
		}

		@Override
		protected Enumeration<E> fromIterator(final Iterator<E> iterator) {
			return Enumerations.from(iterator);
		}

	}

}
