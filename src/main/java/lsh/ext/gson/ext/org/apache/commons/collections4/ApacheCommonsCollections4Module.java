package lsh.ext.gson.ext.org.apache.commons.collections4;

import com.google.gson.TypeAdapterFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;

public final class ApacheCommonsCollections4Module
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private ApacheCommonsCollections4Module(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = BagTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, ?>> bidiMapTypeAdapterFactory = BidiMapTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = MultiSetTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, ?>> multiValuedMapTypeAdapterFactory = MultiValuedMapTypeAdapter.Factory.getInstance();

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new ApacheCommonsCollections4Module(
					bagTypeAdapterFactory,
					bidiMapTypeAdapterFactory,
					multiSetTypeAdapterFactory,
					multiValuedMapTypeAdapterFactory
			);
		}

	}

}
