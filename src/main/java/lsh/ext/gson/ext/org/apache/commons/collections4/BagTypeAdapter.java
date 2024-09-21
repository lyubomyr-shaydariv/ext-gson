package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.ParameterizedTypes;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapter<E>
		extends TypeAdapter<Bag<E>> {

	private final Supplier<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory;
	private final Function<? super E, String> toString;
	private final Function<? super String, ? extends E> fromString;

	public static <E> TypeAdapter<Bag<E>> getInstance(
			final Supplier<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory,
			final Function<? super E, String> keyMapper,
			final Function<? super String, ? extends E> keyReverseMapper
	) {
		return new BagTypeAdapter<>(builderFactory, keyMapper, keyReverseMapper)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Bag<E> bag)
			throws IOException {
		out.beginObject();
		for ( final E e : bag ) {
			out.name(toString.apply(e));
			out.value(bag.getCount(e));
		}
		out.endObject();
	}

	@Override
	public Bag<E> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super E, ? super Integer, ? extends Bag<E>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			builder.accept(fromString.apply(in.nextName()), in.nextInt());
		}
		in.endObject();
		return builder.build();
	}

	public static final class Factory<E>
			extends AbstractRawClassHierarchyTypeAdapterFactory<Bag<E>> {

		private static final ITypeAdapterFactory<?> instance = new Factory<>(Factory::defaultBuilderFactory, Factory::defaultCreateToString, Factory::defaultCreateFromString);

		private final IBuilder2.ILookup<? super E, ? super Integer, ? extends Bag<E>> builderLookup;
		private final Function<? super TypeToken<E>, ? extends Function<? super E, String>> createToString;
		private final Function<? super TypeToken<E>, ? extends Function<? super String, ? extends E>> createFromString;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Factory(
				final IBuilder2.ILookup<? super E, ? super Integer, ? extends Bag<E>> builderLookup,
				final Function<? super TypeToken<E>, ? extends Function<? super E, String>> createToString,
				final Function<? super TypeToken<E>, ? extends Function<? super String, ? extends E>> createFromString
		) {
			super((Class) Bag.class);
			this.builderLookup = builderLookup;
			this.createToString = createToString;
			this.createFromString = createFromString;
		}

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance() {
			@SuppressWarnings("unchecked")
			final ITypeAdapterFactory<Bag<E>> castInstance = (ITypeAdapterFactory<Bag<E>>) instance;
			return castInstance;
		}

		public static <E> ITypeAdapterFactory<Bag<E>> getInstance(
				final IBuilder2.ILookup<? super E, ? super Integer, ? extends Bag<E>> builderLookup,
				final Function<? super TypeToken<E>, ? extends Function<? super E, String>> createToString,
				final Function<? super TypeToken<E>, ? extends Function<? super String, ? extends E>> createFromString
		) {
			return new Factory<>(builderLookup, createToString, createFromString);
		}

		// TODO handle all known implementations
		public static <E> Supplier<IBuilder2<E, Integer, Bag<E>>> defaultBuilderFactory(final TypeToken<? super Bag<E>> typeToken) {
			@SuppressWarnings("unchecked")
			final Class<? super Bag<?>> rawType = (Class<? super Bag<?>>) typeToken.getRawType();
			if ( HashBag.class.isAssignableFrom(rawType) ) {
				return () -> builder(new HashBag<>());
			}
			return () -> builder(new HashBag<>());
		}

		public static <T> Function<? super T, String> defaultCreateToString(final TypeToken<? super String> typeToken) {
			final Class<? super String> rawType = typeToken.getRawType();
			if ( rawType != String.class ) {
				@SuppressWarnings("unchecked")
				final Function<T, String> castToStringOrFail = (Function<T, String>) toStringOrFail;
				return castToStringOrFail;
			}
			@SuppressWarnings("unchecked")
			final Function<? super T, String> castStringFromString = (Function<? super T, String>) Function.<String>identity();
			return castStringFromString;
		}

		public static <T> Function<? super String, ? extends T> defaultCreateFromString(final TypeToken<? super String> typeToken) {
			final Class<? super String> rawType = typeToken.getRawType();
			if ( rawType != String.class ) {
				@SuppressWarnings("unchecked")
				final Function<String, T> castFailFromString = (Function<String, T>) failFromString;
				return castFailFromString;
			}
			@SuppressWarnings("unchecked")
			final Function<? super String, ? extends T> castStringFromString = (Function<? super String, ? extends T>) Function.<String>identity();
			return castStringFromString;
		}

		@Override
		protected TypeAdapter<Bag<E>> createTypeAdapter(final Gson gson, final TypeToken<? super Bag<E>> typeToken) {
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			@SuppressWarnings("unchecked")
			final TypeToken<E> elementTypeToken = (TypeToken<E>) TypeToken.get(elementType);
			@SuppressWarnings("unchecked")
			final IBuilder2.ILookup<E, Integer, Bag<E>> castBuilderLookup = (IBuilder2.ILookup<E, Integer, Bag<E>>) builderLookup;
			return BagTypeAdapter.getInstance(castBuilderLookup.lookup(typeToken), createToString.apply(elementTypeToken), createFromString.apply(elementTypeToken));
		}

		private static <E, B extends Bag<E>> IBuilder2<E, Integer, B> builder(final B bag) {
			return new IBuilder2<>() {
				@Override
				public void accept(final E e, final Integer n) {
					bag.add(e, n);
				}

				@Override
				public B build() {
					return bag;
				}
			};
		}

		private static final Function<?, String> toStringOrFail = o -> {
			if ( o == null ) {
				throw new NullPointerException("No argument to parse provided");
			}
			return o.toString();
		};

		private static final Function<String, ?> failFromString = o -> {
			throw new UnsupportedOperationException("Can't parse " + o);
		};

	}

}
