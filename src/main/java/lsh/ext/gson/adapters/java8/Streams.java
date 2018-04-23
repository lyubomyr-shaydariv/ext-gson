package lsh.ext.gson.adapters.java8;

import java.io.IOException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lsh.ext.gson.ICloseableIterator;

final class Streams {

	private Streams() {
	}

	static <E> Stream<E> from(final ICloseableIterator<E> iterator) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
				.onClose(() -> {
					try {
						iterator.close();
					} catch ( final IOException ex ) {
						throw new RuntimeException(ex);
					}
				});
	}

}