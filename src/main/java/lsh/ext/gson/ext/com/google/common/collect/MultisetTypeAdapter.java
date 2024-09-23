package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.util.function.Supplier;

import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder1;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("Guava")
public final class MultisetTypeAdapter<E>
		extends TypeAdapter<Multiset<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends IBuilder1<? super E, ? extends Multiset<E>>> builderFactory;

	public static <E> TypeAdapter<Multiset<E>> getInstance(
			final TypeAdapter<E> valueTypeAdapter,
			final Supplier<? extends IBuilder1<? super E, ? extends Multiset<E>>> builderFactory
	) {
		return new MultisetTypeAdapter<>(valueTypeAdapter, builderFactory)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Multiset<E> multiset)
			throws IOException {
		out.beginArray();
		for ( final Multiset.Entry<E> e : multiset.entrySet() ) {
			final E element = e.getElement();
			final int count = e.getCount();
			for ( int i = 0; i < count; i++ ) {
				elementTypeAdapter.write(out, element);
			}
		}
		out.endArray();
	}

	@Override
	public Multiset<E> read(final JsonReader in)
			throws IOException {
		in.beginArray();
		final IBuilder1<? super E, ? extends Multiset<E>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			final E element = elementTypeAdapter.read(in);
			builder.accept(element);
		}
		in.endArray();
		return builder.build();
	}

}
