package lsh.ext.gson;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class StreamedTypeAdapter<S, I>
		extends TypeAdapter<S> {

	private final Function<? super JsonReader, ? extends S> toStream;
	private final Function<? super S, ? extends I> toIterator;
	private final Predicate<? super I> hasNext;
	private final BiConsumer<? super JsonWriter, ? super I> writeNext;

	public static <S, I> TypeAdapter<S> getInstance(
			final Function<? super JsonReader, ? extends S> toStream,
			final Function<? super S, ? extends I> toIterator,
			final Predicate<? super I> hasNext,
			final BiConsumer<? super JsonWriter, ? super I> writeNext
	) {
		return new StreamedTypeAdapter<>(toStream, toIterator, hasNext, writeNext);
	}

	@Override
	public void write(final JsonWriter out, final S cursor) {
		final I iterator = toIterator.apply(cursor);
		while ( hasNext.test(iterator) ) {
			writeNext.accept(out, iterator);
		}
	}

	@Override
	public S read(final JsonReader in) {
		return toStream.apply(in);
	}

}
