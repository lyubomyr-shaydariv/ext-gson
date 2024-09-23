package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder1;
import org.apache.commons.collections4.MultiSet;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiSetTypeAdapter<E>
		extends TypeAdapter<MultiSet<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends IBuilder1<? super E, ? extends MultiSet<E>>> builderFactory;

	public static <E> TypeAdapter<MultiSet<E>> getInstance(
			final TypeAdapter<E> elementTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends MultiSet<E>>> builderFactory
	) {
		return new MultiSetTypeAdapter<>(elementTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final MultiSet<E> multiSet)
			throws IOException {
		out.beginArray();
		for ( final MultiSet.Entry<E> e : multiSet.entrySet() ) {
			final E element = e.getElement();
			final int count = e.getCount();
			for ( int i = 0; i < count; i++ ) {
				elementTypeAdapter.write(out, element);
			}
		}
		out.endArray();
	}

	@Override
	public MultiSet<E> read(final JsonReader in)
			throws IOException {
		in.beginArray();
		final IBuilder1<? super E, ? extends MultiSet<E>> builder = builderFactory.get();
		while ( in.peek() != JsonToken.END_ARRAY ) {
			final E element = elementTypeAdapter.read(in);
			builder.accept(element);
		}
		in.endArray();
		return builder.build();
	}

}
