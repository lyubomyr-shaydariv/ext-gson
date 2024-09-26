package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.JsonReaders;
import lsh.ext.gson.Stream1TypeAdapter;
import lsh.ext.gson.StreamedTypeAdapter;

@UtilityClass
public final class StreamTypeAdapter {

	public static <E> TypeAdapter<Stream<E>> forStream(final TypeAdapter<E> elementTypeAdapter, final boolean useBeginEnd, final boolean autoClose) {
		return Stream1TypeAdapter.forArray(elementTypeAdapter, useBeginEnd, (in, iterator) -> {
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

	public static final TypeAdapter<DoubleStream> forDoubleStream = StreamedTypeAdapter.getInstance(
			in -> StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(JsonReaders.asDoubleIterator(in), Spliterator.IMMUTABLE), false),
			DoubleStream::iterator,
			PrimitiveIterator.OfDouble::hasNext,
			(out, iterator) -> {
				try {
					out.value(iterator.next());
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
	);

	public static final TypeAdapter<IntStream> forIntStream = StreamedTypeAdapter.getInstance(
			in -> StreamSupport.intStream(Spliterators.spliteratorUnknownSize(JsonReaders.asIntIterator(in), Spliterator.IMMUTABLE), false),
			IntStream::iterator,
			PrimitiveIterator.OfInt::hasNext,
			(out, iterator) -> {
				try {
					out.value(iterator.next());
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
	);

	public static final TypeAdapter<LongStream> forLongStream = StreamedTypeAdapter.getInstance(
			in -> StreamSupport.longStream(Spliterators.spliteratorUnknownSize(JsonReaders.asLongIterator(in), Spliterator.IMMUTABLE), false),
			LongStream::iterator,
			PrimitiveIterator.OfLong::hasNext,
			(out, iterator) -> {
				try {
					out.value(iterator.next());
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
	);

}
