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
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForBag;

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, ?>> bidiMapTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForBidiMap;

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForMultiSet;

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, ?>> multiValuedMapTypeAdapterFactory = ApacheCommonsCollections4TypeAdapterFactory.defaultForMultiValueMap;

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
