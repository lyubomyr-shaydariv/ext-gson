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
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;

/**
 * Implements an Apache Commons Collections 4 module registering the following type adapter factories:
 *
 * <ul>
 * <li>{@link BidiMapTypeAdapterFactory}</li>
 * </ul>
 */
public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	@Getter
	private static final IModule instance = builder(
			DualLinkedHashBidiMap::new, Function.identity(), Function.identity()
	)
			.build();

	private ApacheCommonsCollections4Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	/**
	 * @return A builder to build a new instance of the module.
	 */
	public static <BMK, BMV> Builder<BMK, BMV> builder(
			final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory, final Function<? super BMK, String> bidiMapKeyMapper, final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper
	) {
		return new Builder<>(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper);
	}

	/**
	 * A builder to configure a new module instance.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	public static final class Builder<BMK, BMV> {

		private final Supplier<? extends BidiMap<BMK, BMV>> newBidiMapFactory;
		private final Function<? super BMK, String> bidiMapKeyMapper;
		private final Function<? super String, ? extends BMK> bidiMapKeyReverseMapper;

		/**
		 * @return A new module instance.
		 */
		public IModule build() {
			return new ApacheCommonsCollections4Module(UnmodifiableIterable.copyOf(
					BidiMapTypeAdapterFactory.getInstance(newBidiMapFactory, bidiMapKeyMapper, bidiMapKeyReverseMapper)
			));
		}

	}

}
