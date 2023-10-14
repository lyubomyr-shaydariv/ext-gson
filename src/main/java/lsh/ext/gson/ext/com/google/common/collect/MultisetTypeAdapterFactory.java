package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Multiset} from Google Guava.
 *
 * @param <E>
 * 		Element type
 *
 * @author Lyubomyr Shaydariv
 * @see Adapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultisetTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Multiset<E>> {

	@Getter
	private static final TypeAdapterFactory instance = new MultisetTypeAdapterFactory<>(null);

	@Nullable
	@SuppressWarnings("Guava")
	private final Supplier<? extends Multiset<E>> newMultisetFactory;

	/**
	 * @param newMultisetFactory
	 * 		Multiset factory
	 * @param <E>
	 * 		Element type
	 *
	 * @return An instance of {@link MultisetTypeAdapterFactory} with a custom new {@link Multiset} factory.
	 */
	public static <E> TypeAdapterFactory getInstance(@Nullable @SuppressWarnings("Guava") final Supplier<? extends Multiset<E>> newMultisetFactory) {
		if ( newMultisetFactory == null ) {
			return instance;
		}
		return new MultisetTypeAdapterFactory<>(newMultisetFactory);
	}

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return Multiset.class.isAssignableFrom(typeToken.getRawType());
	}

	@Override
	protected TypeAdapter<Multiset<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		final Type[][] typeArguments = ParameterizedTypes.getTypeArguments(typeToken.getType());
		final Type elementType = typeArguments[0][0];
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		if ( newMultisetFactory == null ) {
			return Adapter.getInstance(elementTypeAdapter);
		}
		return Adapter.getInstance(elementTypeAdapter, newMultisetFactory);
	}

	/**
	 * Represents a type adapter for {@link Multiset} from Google Guava.
	 *
	 * @param <E>
	 * 		Element type
	 *
	 * @author Lyubomyr Shaydariv
	 * @see MultisetTypeAdapterFactory
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Multiset<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multiset<E>> newMultisetFactory;

		/**
		 * @param elementTypeAdapter
		 * 		Multiset element type adapter
		 * @param <E>
		 * 		Multiset element type
		 *
		 * @return A {@link Adapter} instance whose multiset factory is {@link LinkedHashMultiset#create()}.
		 *
		 * @see #getInstance(TypeAdapter, Supplier)
		 */
		public static <E> TypeAdapter<Multiset<E>> getInstance(final TypeAdapter<E> elementTypeAdapter) {
			return getInstance(elementTypeAdapter, (Supplier<? extends Multiset<E>>) LinkedHashMultiset::create);
		}

		/**
		 * @param valueTypeAdapter
		 * 		Multiset value type adapter
		 * @param newMultisetFactory
		 * 		A {@link Multiset} factory to create instance used while deserialization
		 * @param <V>
		 * 		Multiset element type
		 *
		 * @return A {@link Adapter} instance.
		 *
		 * @see #getInstance(TypeAdapter)
		 */
		public static <V> TypeAdapter<Multiset<V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				@SuppressWarnings("Guava") final Supplier<? extends Multiset<V>> newMultisetFactory) {
			return new Adapter<>(valueTypeAdapter, newMultisetFactory);
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

}
