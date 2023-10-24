package lsh.ext.gson.ext.org.apache.commons.collections4;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IInstanceFactory;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multiset.HashMultiSet;

/**
 * Implements an Apache Commons Collections 4 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link BagTypeAdapterFactory}</li>
 * <li>{@link BidiMapTypeAdapterFactory}</li>
 * <li>{@link MultiMapTypeAdapterFactory}</li>
 * <li>{@link MultiValuedMapTypeAdapterFactory}</li>
 * <li>{@link MultiSetTypeAdapterFactory}</li>
 * </ul>
 */
@SuppressWarnings("deprecation")
public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	/**
	 * Provides a default bag object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<Bag<Object>> defaultBagFactoryProvider = typeToken -> HashBag::new;

	/**
	 * Provides a default bidirectional map object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<BidiMap<String, Object>> defaultBidiMapFactoryProvider = typeToken -> DualLinkedHashBidiMap::new;

	/**
	 * Provides a default multiset object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<MultiSet<Object>> defaultMultiSetFactoryProvider = typeToken -> HashMultiSet::new;

	/**
	 * Provides a default multimap object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<MultiMap<String, Object>> defaultMultiMapFactoryProvider = typeToken -> MultiValueMap::new;

	/**
	 * Provides a default multi-valued map object.
	 */
	// TODO improve
	public static final IInstanceFactory.IProvider<MultiValuedMap<String, Object>> defaultMultiValuedFactoryProvider = typeToken -> ArrayListValuedHashMap::new;

	@Getter
	private static final IModule instance = builder()
			.build();

	private ApacheCommonsCollections4Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = BagTypeAdapterFactory.getInstance(
				defaultBagFactoryProvider,
				new BagTypeAdapterFactory.IKeyMapperFactory<>() {
					@Override
					public Transformer<Object, String> createKeyMapper(final TypeToken<Object> typeToken) {
						throw new UnsupportedOperationException(typeToken.toString());
					}

					@Override
					public Transformer<String, Object> createReverseKeyMapper(final TypeToken<Object> typeToken) {
						throw new UnsupportedOperationException(typeToken.toString());
					}
				}
		);

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, Object>> bidiMapTypeAdapterFactory = BidiMapTypeAdapterFactory.getInstance(defaultBidiMapFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = MultiSetTypeAdapterFactory.getInstance(defaultMultiSetFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends MultiMap<String, ?>> multiMapTypeAdapterFactory = MultiMapTypeAdapterFactory.getInstance(defaultMultiMapFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, Object>> multiValuedMapTypeAdapterFactory = MultiValuedMapTypeAdapterFactory.getInstance(defaultMultiValuedFactoryProvider);

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new ApacheCommonsCollections4Module(UnmodifiableIterable.copyOf(
					bagTypeAdapterFactory,
					bidiMapTypeAdapterFactory,
					multiSetTypeAdapterFactory,
					multiMapTypeAdapterFactory,
					multiValuedMapTypeAdapterFactory
			));
		}

	}

}
