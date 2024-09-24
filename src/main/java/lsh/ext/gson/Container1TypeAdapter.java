package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Container1TypeAdapter<E1, C>
		extends TypeAdapter<C> {

	private final TypeAdapter<E1> typeAdapter;
	private final Function<? super C, ? extends Iterator<? extends E1>> toIterator;
	private final Supplier<? extends IBuilder1<? super E1, ? extends C>> createBuilder;

	public static <E1, C extends Iterable<E1>> TypeAdapter<C> getInstance(
			final TypeAdapter<E1> typeAdapter,
			final Supplier<? extends IBuilder1<? super E1, ? extends C>> createBuilder
	) {
		return getInstance(typeAdapter, Iterable::iterator, createBuilder);
	}

	public static <E1, C> TypeAdapter<C> getInstance(
			final TypeAdapter<E1> typeAdapter,
			final Function<? super C, ? extends Iterator<? extends E1>> toIterator,
			final Supplier<? extends IBuilder1<? super E1, ? extends C>> createBuilder
	) {
		return new Container1TypeAdapter<E1, C>(typeAdapter, toIterator, createBuilder)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final C container)
			throws IOException {
		out.beginArray();
		final Iterator<? extends E1> iterator = toIterator.apply(container);
		while ( iterator.hasNext() ) {
			final E1 e1 = iterator.next();
			typeAdapter.write(out, e1);
		}
		out.endArray();
	}

	@Override
	public C read(final JsonReader in)
			throws IOException {
		in.beginArray();
		final IBuilder1<? super E1, ? extends C> builder = createBuilder.get();
		while ( in.hasNext() ) {
			final E1 e1 = typeAdapter.read(in);
			builder.accept(e1);
		}
		return builder.build();
	}

}
