package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractJsonArrayTypeAdapter<A, E>
		extends TypeAdapter<A> {

	private final TypeAdapter<E> elementTypeAdapter;

	protected abstract Iterable<? extends E> toIterable(A array);

	protected abstract IBuilder1<? super E, ? extends A> createBuilder();

	@Override
	public final void write(final JsonWriter out, final A array)
			throws IOException {
		out.beginArray();
		for ( final E e : toIterable(array) ) {
			elementTypeAdapter.write(out, e);
		}
		out.endArray();
	}

	@Override
	public final A read(final JsonReader in)
			throws IOException {
		final IBuilder1<? super E, ? extends A> builder = createBuilder();
		in.beginArray();
		while ( in.hasNext() ) {
			final E e = elementTypeAdapter.read(in);
			builder.accept(e);
		}
		in.endArray();
		return builder.build();
	}

}
