package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonArrayStreamTypeAdapter;

@UtilityClass
public final class StreamTypeAdapter {

	public static <E> TypeAdapter<Stream<E>> forStream(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd, final boolean autoClose) {
		return JsonArrayStreamTypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (in, iterator) -> {
			final Stream<E> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE), false);
			if ( !autoClose ) {
				return stream;
			}
			return stream
					.onClose(() -> {
						try {
							in.close();
						} catch ( final IOException ex ) {
							throw new RuntimeException(ex);
						}
					});
		}, BaseStream::iterator);
	}

}
