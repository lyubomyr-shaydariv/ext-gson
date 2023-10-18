package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
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

	@Getter
	@SuppressWarnings("deprecation")
	private static final IModule instance = builder(
			DualLinkedHashBidiMap::new, Function.identity(), Function.identity(),
			HashMultiSet::new,
			MultiValueMap::new, Function.identity(), Function.identity(),
			ArrayListValuedHashMap::new, Function.identity(), Function.identity(),
			HashBag::new, Function.identity(), Function.identity()
	)
			.build();

	private ApacheCommonsCollections4Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static <BMK, BMV, MSE, MMK, MMV, MMMK, MMMV, BE> Builder<BMK, BMV, MSE, MMK, MMV, MMMK, MMMV, BE> builder(
			final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory, final Function<? super BMK, String> bidiMapKeyMapper, final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper,
			final Supplier<? extends MultiSet<MSE>> newMultiSetFactory,
			@SuppressWarnings("deprecation") final Supplier<? extends MultiMap<MMK, MMV>> newMultiMapFactory, final Function<? super MMK, String> multiMapKeyMapper, final Function<? super String, ? extends MMK> multiMapKeyReverseMapper,
			final Supplier<? extends MultiValuedMap<MMMK, MMMV>> newMultiValuedMapFactory, final Function<? super MMMK, String> multiValuedMapKeyMapper, final Function<? super String, ? extends MMMK> multiValuedMapKeyReverseMapper,
			final Supplier<? extends Bag<BE>> newBagFactory, final Function<? super BE, String> keyMapper, final Function<? super String, ? extends BE> keyReverseMapper
	) {
		return new Builder<>(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper, newMultiSetFactory, newMultiMapFactory, multiMapKeyMapper, multiMapKeyReverseMapper, newMultiValuedMapFactory, multiValuedMapKeyMapper, multiValuedMapKeyReverseMapper, newBagFactory, keyMapper, keyReverseMapper);
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder<BMK, BMV, MSE, MMK, MMV, MMMK, MMMV, BE> {

		private final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory;
		private final Function<? super BMK, String> bidiMapKeyMapper;
		private final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper;
		private final Supplier<? extends MultiSet<MSE>> newMultiSetFactory;
		@SuppressWarnings("deprecation")
		private final Supplier<? extends MultiMap<MMK, MMV>> newMultiMapFactory;
		private final Function<? super MMK, String> multiMapKeyMapper;
		private final Function<? super String, ? extends MMK> multiMapKeyReverseMapper;
		private final Supplier<? extends MultiValuedMap<MMMK, MMMV>> newMultiValuedMapFactory;
		private final Function<? super MMMK, String> multiValuedMapKeyMapper;
		private final Function<? super String, ? extends MMMK> multiValuedMapKeyReverseMapper;
		private final Supplier<? extends Bag<BE>> newBagFactory;
		private final Function<? super BE, String> keyMapper;
		private final Function<? super String, ? extends BE> keyReverseMapper;

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new ApacheCommonsCollections4Module(UnmodifiableIterable.copyOf(
					BagTypeAdapterFactory.getInstance(newBagFactory, keyMapper, keyReverseMapper),
					BidiMapTypeAdapterFactory.getInstance(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper),
					MultiSetTypeAdapterFactory.getInstance(newMultiSetFactory),
					MultiMapTypeAdapterFactory.getInstance(newMultiMapFactory, multiMapKeyMapper, multiMapKeyReverseMapper),
					MultiValuedMapTypeAdapterFactory.getInstance(newMultiValuedMapFactory, multiValuedMapKeyMapper, multiValuedMapKeyReverseMapper)
			));
		}

	}

}
