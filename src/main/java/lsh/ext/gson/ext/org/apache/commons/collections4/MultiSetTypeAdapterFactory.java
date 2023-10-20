package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.MultiSet;

/**
 * Represents a type adapter factory for {@link MultiSet} from Apache Commons Collection 4.
 *
 * @param <E>
 * 		Element type
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiSetTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<MultiSet<E>> {

	private final Factory<? extends MultiSet<E>> newMultiSetFactory;

	/**
	 * @param newMultiSetFactory
	 * 		MultiSet factory
	 * @param <E>
	 * 		Element type
	 *
	 * @return An instance of {@link MultiSetTypeAdapterFactory} with a custom new {@link MultiSet} factory.
	 */
	public static <E> TypeAdapterFactory getInstance(final Factory<? extends MultiSet<E>> newMultiSetFactory) {
		return new MultiSetTypeAdapterFactory<>(newMultiSetFactory);
	}

	@Override
	protected TypeAdapter<MultiSet<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !MultiSet.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@Nullable
		final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
		assert elementType != null;
		@SuppressWarnings("unchecked")
		final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
		return Adapter.getInstance(elementTypeAdapter, newMultiSetFactory);
	}

	/**
	 * Represents a type adapter for {@link MultiSet} from Apache Commons Collection 4.
	 *
	 * @param <E>
	 * 		Element type
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<MultiSet<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		private final Factory<? extends MultiSet<E>> newMultiSetFactory;

		/**
		 * @param valueTypeAdapter
		 * 		MultiSet value type adapter
		 * @param newMultiSetFactory
		 * 		A {@link MultiSet} factory to create instance used while deserialization
		 * @param <V>
		 * 		MultiSet element type
		 *
		 * @return A {@link Adapter} instance.
		 */
		public static <V> TypeAdapter<MultiSet<V>> getInstance(final TypeAdapter<V> valueTypeAdapter,
				final Factory<? extends MultiSet<V>> newMultiSetFactory) {
			return new Adapter<>(valueTypeAdapter, newMultiSetFactory)
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
			final MultiSet<E> multiSet = newMultiSetFactory.create();
			in.beginArray();
			while ( in.peek() != JsonToken.END_ARRAY ) {
				final E element = elementTypeAdapter.read(in);
				multiSet.add(element);
			}
			in.endArray();
			return multiSet;
		}

	}

}
