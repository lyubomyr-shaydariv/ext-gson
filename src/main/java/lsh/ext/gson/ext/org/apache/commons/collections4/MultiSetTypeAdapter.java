package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.UnmodifiableMultiSet;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MultiSetTypeAdapter<E>
		extends TypeAdapter<MultiSet<E>> {

	private final TypeAdapter<E> elementTypeAdapter;
	private final org.apache.commons.collections4.Factory<? extends IBuilder1<? super E, ? extends MultiSet<E>>> builderFactory;

	public static <E> TypeAdapter<MultiSet<E>> getInstance(
			final TypeAdapter<E> valueTypeAdapter,
			final org.apache.commons.collections4.Factory<? extends IBuilder1<? super E, ? extends MultiSet<E>>> builderFactory
	) {
		return new MultiSetTypeAdapter<>(valueTypeAdapter, builderFactory)
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
		final IBuilder1<? super E, ? extends MultiSet<E>> builder = builderFactory.create();
		while ( in.peek() != JsonToken.END_ARRAY ) {
			final E element = elementTypeAdapter.read(in);
			builder.accept(element);
		}
		in.endArray();
		return builder.build();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Factory<E>
			extends AbstractTypeAdapterFactory<MultiSet<E>> {

		private final IBuilder1.IFactory<? super E, ? extends MultiSet<E>> builderFactory;

		public static <E> ITypeAdapterFactory<MultiSet<E>> getInstance(
				final IBuilder1.IFactory<? super E, ? extends MultiSet<E>> builderFactory
		) {
			return new Factory<>(builderFactory);
		}

		public static <E> ITypeAdapterFactory<MultiSet<E>> getDefaultBuilderInstance(
				final IBuilder0.IFactory<? extends MultiSet<E>> builderFactory
		) {
			return getInstance(typeToken -> defaultBuilder(typeToken, builderFactory));
		}

		public static <E> IBuilder1<E, MultiSet<E>> defaultBuilder(
				final TypeToken<? super MultiSet<E>> typeToken,
				final IBuilder0.IFactory<? extends MultiSet<E>> builderFactory
		) {
			@SuppressWarnings("LawOfDemeter")
			final MultiSet<E> multiSet = builderFactory.create(typeToken)
					.build();
			@SuppressWarnings("unchecked")
			final Class<? extends MultiSet<E>> rawType = (Class<? extends MultiSet<E>>) typeToken.getRawType();
			if ( UnmodifiableMultiSet.class.isAssignableFrom(rawType) ) {
				return unmodifiableBuilder(multiSet);
			}
			return IBuilder1.of(multiSet);
		}

		public static <E> IBuilder1<E, MultiSet<E>> unmodifiableBuilder(final MultiSet<E> multiSet) {
			return new IBuilder1<>() {
				@Override
				public void accept(final E e) {
					multiSet.add(e);
				}

				@Override
				public MultiSet<E> build() {
					return UnmodifiableMultiSet.unmodifiableMultiSet(multiSet);
				}
			};
		}

		@Override
		@Nullable
		protected TypeAdapter<MultiSet<E>> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !MultiSet.class.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			@SuppressWarnings("unchecked")
			final TypeAdapter<E> elementTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(elementType));
			@SuppressWarnings("unchecked")
			final TypeToken<MultiSet<E>> castTypeToken = (TypeToken<MultiSet<E>>) typeToken;
			@SuppressWarnings("unchecked")
			final IBuilder1.IFactory<E, MultiSet<E>> castBuilderFactory = (IBuilder1.IFactory<E, MultiSet<E>>) builderFactory;
			return MultiSetTypeAdapter.getInstance(elementTypeAdapter, () -> castBuilderFactory.create(castTypeToken));
		}

	}

}
