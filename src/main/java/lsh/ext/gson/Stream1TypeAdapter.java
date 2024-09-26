package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Stream1TypeAdapter<S, E>
		extends TypeAdapter<S> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final boolean useBeginEnd;
	private final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator;
	private final Function<? super S, ? extends Iterator<E>> toWriteIterator;

	public static <S, E> TypeAdapter<S> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final boolean useBeginEnd,
			final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator,
			final Function<? super S, ? extends Iterator<E>> toWriteIterator
	) {
		return new Stream1TypeAdapter<S, E>(elementTypeAdapter, useBeginEnd, toReadIterator, toWriteIterator)
				.nullSafe();
	}

	public static <S, E> TypeAdapter<S> forArray(
			final TypeAdapter<E> elementTypeAdapter,
			final boolean useBeginEnd,
			final BiFunction<? super JsonReader, ? super Iterator<E>, ? extends S> toReadIterator,
			final Function<? super S, ? extends Iterator<E>> toWriteIterator
	) {
		return getInstance(elementTypeAdapter, useBeginEnd, toReadIterator, toWriteIterator);
	}

	@Override
	public void write(final JsonWriter out, final S stream)
			throws IOException {
		if ( useBeginEnd ) {
			out.beginArray();
		}
		final Iterator<E> iterator = toWriteIterator.apply(stream);
		while ( iterator.hasNext() ) {
			elementTypeAdapter.write(out, iterator.next());
		}
		if ( useBeginEnd ) {
			out.endArray();
		}
	}

	@Override
	public S read(final JsonReader in)
			throws IOException {
		if ( useBeginEnd ) {
			in.beginArray();
		}
		return toReadIterator.apply(in, new Iterator<>() {
			@Override
			public boolean hasNext() {
				try {
					if ( !in.hasNext() ) {
						if ( useBeginEnd ) {
							in.endArray();
						}
						return false;
					}
					return true;
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public E next() {
				try {
					return elementTypeAdapter.read(in);
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

}
