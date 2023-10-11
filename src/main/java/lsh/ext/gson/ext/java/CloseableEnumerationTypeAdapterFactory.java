package lsh.ext.gson.ext.java;

import java.util.Enumeration;
import java.util.Iterator;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapterFactory;

/**
 * Represents a type adapter factory for {@link Iterator}.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 * @see Adapter
 */
public final class CloseableEnumerationTypeAdapterFactory<E>
		extends AbstractCursorTypeAdapterFactory<E> {

	@Getter
	private static final TypeAdapterFactory instance = new CloseableEnumerationTypeAdapterFactory<>();

	private CloseableEnumerationTypeAdapterFactory() {
		super(Enumeration.class);
	}

	@Override
	protected TypeAdapter<?> createCursorTypeAdapter(final TypeAdapter<?> elementTypeAdapter) {
		return CloseableEnumerationTypeAdapterFactory.Adapter.getInstance(elementTypeAdapter);
	}

	/**
	 * Type adapter for {@link ICloseableEnumeration}. Enumerations are supposed to read and write JSON arrays only.
	 *
	 * <p>
	 * <b>CAUTION</b> Note that the {@link #read(JsonReader)} method returns a closeable iterator that must be closed manually.
	 * </p>
	 *
	 * @author Lyubomyr Shaydariv
	 * @see CloseableEnumerationTypeAdapterFactory
	 */
	public static final class Adapter<E>
			extends AbstractCursorTypeAdapterFactory.Adapter<ICloseableEnumeration<E>, E> {

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
		public static <E> TypeAdapter<ICloseableEnumeration<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return new Adapter<>(elementTypeAdapter);
		}

		@Override
		protected Iterator<E> toIterator(final ICloseableEnumeration<E> enumeration) {
			return CloseableIterators.from(enumeration);
		}

		@Override
		protected ICloseableEnumeration<E> fromIterator(final ICloseableIterator<E> iterator) {
			return CloseableEnumerations.from(iterator);
		}

	}

}
