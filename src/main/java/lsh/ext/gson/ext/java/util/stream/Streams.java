package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.experimental.UtilityClass;
import lsh.ext.gson.ICloseableIterator;

@UtilityClass
final class Streams {

	static <E> Stream<E> from(final ICloseableIterator<? extends E> iterator) {
		return StreamSupport.<E>stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
				.onClose(() -> {
					try {
						iterator.close();
					} catch ( final IOException ex ) {
						throw new RuntimeException(ex);
					}
				});
	}

}
