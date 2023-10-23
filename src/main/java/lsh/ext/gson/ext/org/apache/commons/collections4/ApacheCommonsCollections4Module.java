package lsh.ext.gson.ext.org.apache.commons.collections4;

import com.google.gson.TypeAdapterFactory;
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
public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	// TODO improve
	public static final IInstanceFactory.IProvider<Bag<Object>> defaultBagFactoryProvider = typeToken -> HashBag::new;

	// TODO improve
	public static final IInstanceFactory.IProvider<BidiMap<String, Object>> defaultBidiMapFactoryProvider = typeToken -> DualLinkedHashBidiMap::new;

	// TODO improve
	public static final IInstanceFactory.IProvider<MultiSet<Object>> defaultMultiSetFactoryProvider = typeToken -> HashMultiSet::new;

	// TODO improve
	public static final IInstanceFactory.IProvider<MultiMap<String, Object>> defaultMultiMapFactoryProvider = typeToken -> MultiValueMap::new;

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
	@SuppressWarnings("deprecation")
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = BagTypeAdapterFactory.getInstance(defaultBagFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, Object>> bidiMapTypeAdapterFactory = BidiMapTypeAdapterFactory.getInstance(defaultBidiMapFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = MultiSetTypeAdapterFactory.getInstance(defaultMultiSetFactoryProvider);

		@Setter
		private ITypeAdapterFactory<? extends MultiMap<String, ?>> multiMapTypeAdapterFactory = MultiMapTypeAdapterFactory.getInstance(defaultMultiMapFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, Object>> multiValuedMapTypeAdapterFactory = MultiValuedMapTypeAdapterFactory.getInstance(defaultMultiValuedFactoryProvider, AbstractModule::toStringOrNull, AbstractModule::parseToNullOrFail);

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
