package lsh.ext.gson.adapters;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Provides closeable iterators utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @see IteratorTypeAdapter
 * @see IteratorTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class CloseableIterators {

	private CloseableIterators() {
	}

	/**
	 * Performs an action for each iterator element and tries to close the iterator using {@link #tryClose(Object)}.
	 *
	 * @param iterator Iterator elements to iterate over
	 * @param consumer An action to be performed for each element
	 * @param <E>      Iterator element type
	 *
	 * @throws Exception If an exception during {@link AutoCloseable#close()} occurs.
	 */
	public static <E> void forEachAndTryClose(final Iterator<? extends E> iterator, final Consumer<? super E> consumer)
			throws Exception {
		try {
			while ( iterator.hasNext() ) {
				consumer.accept(iterator.next());
			}
		} finally {
			tryClose(iterator);
		}
	}

	/**
	 * Tries to close the given object if it's a {@link AutoCloseable}.
	 *
	 * @param object Any object. If the object is {@link AutoCloseable}, {@link AutoCloseable#close()} is invoked upon the object. Otherwise ignored.
	 *
	 * @throws Exception If an exception during {@link AutoCloseable#close()} occurs.
	 */
	public static void tryClose(final Object object)
			throws Exception {
		if ( object instanceof AutoCloseable ) {
			((AutoCloseable) object).close();
		}
	}

}
