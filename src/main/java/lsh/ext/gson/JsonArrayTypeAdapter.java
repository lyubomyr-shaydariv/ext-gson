package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public final class JsonArrayTypeAdapter<A, E>
		extends TypeAdapter<A> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final IFunction1<? super A, ? extends Iterable<E>> toIterable;
	private final IFactory<? extends IBuilder1<? super E, ? extends A>> createBuilder;

	public static <A, E> TypeAdapter<A> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final IFunction1<? super A, ? extends Iterable<E>> toIterable,
			final IFactory<? extends IBuilder1<? super E, ? extends A>> createBuilder
	) {
		return new JsonArrayTypeAdapter<>(elementTypeAdapter, toIterable, createBuilder);
	}

	@Override
	public void write(final JsonWriter out, final A array)
			throws IOException {
		out.beginArray();
		for ( final E e : toIterable.apply(array) ) {
			elementTypeAdapter.write(out, e);
		}
		out.endArray();
	}

	@Override
	public A read(final JsonReader in)
			throws IOException {
		final IBuilder1<? super E, ? extends A> builder = createBuilder.create();
		in.beginArray();
		while ( in.hasNext() ) {
			final E e = elementTypeAdapter.read(in);
			builder.accept(e);
		}
		in.endArray();
		return builder.build();
	}

}
