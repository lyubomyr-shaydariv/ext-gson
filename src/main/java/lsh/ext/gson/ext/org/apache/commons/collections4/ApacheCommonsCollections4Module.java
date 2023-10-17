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
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Implements an Apache Commons Collections 4 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link BidiMapTypeAdapterFactory}</li>
 * <li>{@link MultiMapTypeAdapterFactory}</li>
 * <li>{@link MultiValuedMapTypeAdapterFactory}</li>
 * </ul>
 */
public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	@Getter
	@SuppressWarnings("deprecation")
	private static final IModule instance = builder(
			DualLinkedHashBidiMap::new, Function.identity(), Function.identity(),
			MultiValueMap::new, Function.identity(), Function.identity(),
			ArrayListValuedHashMap::new, Function.identity(), Function.identity()
	)
			.build();

	private ApacheCommonsCollections4Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static <BMK, BMV, MMK, MMV, MMMK, MMMV> Builder<BMK, BMV, MMK, MMV, MMMK, MMMV> builder(
			final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory, final Function<? super BMK, String> bidiMapKeyMapper, final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper,
			@SuppressWarnings("deprecation") final Supplier<? extends MultiMap<MMK, MMV>> newMultiMapFactory, final Function<? super MMK, String> multiMapKeyMapper, final Function<? super String, ? extends MMK> multiMapKeyReverseMapper,
			final Supplier<? extends MultiValuedMap<MMMK, MMMV>> newMultiValuedMapFactory, final Function<? super MMMK, String> multiValuedMapKeyMapper, final Function<? super String, ? extends MMMK> multiValuedMapKeyReverseMapper
	) {
		return new Builder<>(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper, newMultiMapFactory, multiMapKeyMapper, multiMapKeyReverseMapper, newMultiValuedMapFactory, multiValuedMapKeyMapper, multiValuedMapKeyReverseMapper);
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder<BMK, BMV, MMK, MMV, MMMK, MMMV> {

		private final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory;
		private final Function<? super BMK, String> bidiMapKeyMapper;
		private final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper;
		@SuppressWarnings("deprecation")
		private final Supplier<? extends MultiMap<MMK, MMV>> newMultiMapFactory;
		private final Function<? super MMK, String> multiMapKeyMapper;
		private final Function<? super String, ? extends MMK> multiMapKeyReverseMapper;
		private final Supplier<? extends MultiValuedMap<MMMK, MMMV>> newMultiValuedMapFactory;
		private final Function<? super MMMK, String> multiValuedMapKeyMapper;
		private final Function<? super String, ? extends MMMK> multiValuedMapKeyReverseMapper;

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new ApacheCommonsCollections4Module(UnmodifiableIterable.copyOf(
					BidiMapTypeAdapterFactory.getInstance(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper),
					MultiMapTypeAdapterFactory.getInstance(newMultiMapFactory, multiMapKeyMapper, multiMapKeyReverseMapper),
					MultiValuedMapTypeAdapterFactory.getInstance(newMultiValuedMapFactory, multiValuedMapKeyMapper, multiValuedMapKeyReverseMapper)
			));
		}

	}

}
