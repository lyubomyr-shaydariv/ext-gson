package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;

import com.google.common.base.Supplier;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Represents a type adapter for {@link Multiset} from Google Guava.
 *
 * @param <E> Element type
 *
 * @author Lyubomyr Shaydariv
 * @see MultisetTypeAdapterFactory
 * @since 0-SNAPSHOT
 */
public final class MultisetTypeAdapter<E>
		extends TypeAdapter<Multiset<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final Supplier<? extends Multiset<E>> newMultisetFactory;

	private MultisetTypeAdapter(final TypeAdapter<E> elementTypeAdapter, final Supplier<? extends Multiset<E>> newMultisetFactory) {
		this.elementTypeAdapter = elementTypeAdapter;
		this.newMultisetFactory = newMultisetFactory;
	}

	/**
	 * @param elementTypeAdapter Multiset element type adapter
	 * @param <E>                Multiset element type
	 *
	 * @return A {@link MultisetTypeAdapter} instance whose multiset factory is {@link LinkedHashMultiset#create()}.
	 *
	 * @see #create(TypeAdapter, Supplier)
	 * @since 0-SNAPSHOT
	 */
	public static <E> TypeAdapter<Multiset<E>> create(final TypeAdapter<E> elementTypeAdapter) {
		return create(elementTypeAdapter, (Supplier<? extends Multiset<E>>) LinkedHashMultiset::create);
	}

	/**
	 * @param valueTypeAdapter   Multiset value type adapter
	 * @param newMultisetFactory A {@link Multiset} factory to create instance used while deserialization
	 * @param <V>                Multiset element type
	 *
	 * @return A {@link MultisetTypeAdapter} instance.
	 *
	 * @see #create(TypeAdapter)
	 * @since 0-SNAPSHOT
	 */
	public static <V> TypeAdapter<Multiset<V>> create(final TypeAdapter<V> valueTypeAdapter,
			final Supplier<? extends Multiset<V>> newMultisetFactory) {
		return new MultisetTypeAdapter<>(valueTypeAdapter, newMultisetFactory)
				.nullSafe();
	}

	@Override
	@SuppressWarnings("resource")
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
		final Multiset<E> multiset = newMultisetFactory.get();
		in.beginArray();
		while ( in.peek() != JsonToken.END_ARRAY ) {
			final E element = elementTypeAdapter.read(in);
			multiset.add(element);
		}
		in.endArray();
		return multiset;
	}

}
