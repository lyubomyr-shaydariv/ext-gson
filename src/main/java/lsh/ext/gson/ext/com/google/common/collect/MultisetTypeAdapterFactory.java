package lsh.ext.gson.ext.com.google.common.collect;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultisetTypeAdapterFactory<E>
		extends AbstractTypeAdapterFactory<Multiset<E>>
		implements ITypeAdapterFactory<Multiset<E>> {

	private final IInstanceFactory.IProvider<? extends Multiset<E>> newMultimapFactoryProvider;

	public static <E> ITypeAdapterFactory<Multiset<E>> getInstance(final IInstanceFactory.IProvider<? extends Multiset<E>> newMultisetFactory) {
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
		@SuppressWarnings("unchecked")
		final TypeToken<Multiset<E>> castTypeToken = (TypeToken<Multiset<E>>) typeToken;
		@SuppressWarnings("unchecked")
		final IInstanceFactory.IProvider<Multiset<E>> castNewMultisetFactoryProvider = (IInstanceFactory.IProvider<Multiset<E>>) newMultimapFactoryProvider;
		return Adapter.getInstance(elementTypeAdapter, castNewMultisetFactoryProvider.provide(castTypeToken));
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter<E>
			extends TypeAdapter<Multiset<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		private final IInstanceFactory<? extends Multiset<E>> newMultisetInstanceFactory;

		public static <E> TypeAdapter<Multiset<E>> getInstance(final TypeAdapter<E> valueTypeAdapter,
				final IInstanceFactory<? extends Multiset<E>> newMultisetInstanceFactory) {
			return new Adapter<>(valueTypeAdapter, newMultisetInstanceFactory)
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
			final Multiset<E> multiset = newMultisetInstanceFactory.createInstance();
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
