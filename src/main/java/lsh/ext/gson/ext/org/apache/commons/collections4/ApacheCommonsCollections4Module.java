package lsh.ext.gson.ext.org.apache.commons.collections4;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.UnmodifiableIterable;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

@SuppressWarnings("deprecation")
public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	@Getter
	private static final IModule instance = builder()
			.build();

	private ApacheCommonsCollections4Module(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	public static Builder builder() {
		return new Builder();
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder {

		@Setter
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = BagTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, Object>> bidiMapTypeAdapterFactory = BidiMapTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = MultiSetTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends MultiMap<String, ?>> multiMapTypeAdapterFactory = MultiMapTypeAdapterFactory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, Object>> multiValuedMapTypeAdapterFactory = MultiValuedMapTypeAdapterFactory.getInstance();

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
