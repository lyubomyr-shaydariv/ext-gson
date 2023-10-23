package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

/**
 * Represents a type adapter factory for {@link Multiset} from Google Guava.
 *
 * @param <E>
 * 		Element type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultisetTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Multiset<E>>
		implements ITypeAdapterFactory<Multiset<E>> {

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
	public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(@SuppressWarnings("Guava") final Supplier<? extends Multiset<E>> newMultisetFactory) {
		return new MultisetTypeAdapterFactory<>(newMultisetFactory);
	}

	@Override
	protected TypeAdapter<Multiset<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Multiset.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
		assert elementType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		return Adapter.getInstance(elementTypeAdapter, newMultisetFactory);
	}

	/**
	 * Represents a type adapter for {@link Multiset} from Google Guava.
	 *
	 * @param <E>
	 * 		Element type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Multiset<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		@SuppressWarnings("Guava")
		private final Supplier<? extends Multiset<E>> newMultisetFactory;

		/**
		 * @param valueTypeAdapter
		 * 		Multiset value type adapter
		 * @param newMultisetFactory
		 * 		A {@link Multiset} factory to create instance used while deserialization
		 * @param <V>
		 * 		Multiset element type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<Multiset<V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				@SuppressWarnings("Guava") final Supplier<? extends Multiset<V>> newMultisetFactory) {
			return new Adapter<>(valueTypeAdapter, newMultisetFactory)
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
			final Multiset<E> multiset = newMultisetFactory.get();
			in.beginArray();
			while ( in.hasNext() ) {
				final E element = elementTypeAdapter.read(in);
				multiset.add(element);
			}
			in.endArray();
			return multiset;
		}

	}

}
