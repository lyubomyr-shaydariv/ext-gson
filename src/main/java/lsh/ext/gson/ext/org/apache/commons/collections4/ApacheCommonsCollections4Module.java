package lsh.ext.gson.ext.org.apache.commons.collections4;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.AbstractModule;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IBuilder1;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.Transformer;

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

		private static <I, O> Transformer<TypeToken<I>, Transformer<? super I, O>> unsupported() {
			return typeToken -> {
				throw new UnsupportedOperationException(typeToken.toString());
			};
		}

		@Setter
		private ITypeAdapterFactory<? extends Bag<?>> bagTypeAdapterFactory = BagTypeAdapter.Factory.getInstance(IBuilder2.IFactory.unsupported(), unsupported(), unsupported());

		@Setter
		private ITypeAdapterFactory<? extends BidiMap<String, ?>> bidiMapTypeAdapterFactory = BidiMapTypeAdapter.Factory.getInstance(IBuilder2.IFactory.unsupported());

		@Setter
		private ITypeAdapterFactory<? extends MultiSet<?>> multiSetTypeAdapterFactory = MultiSetTypeAdapter.Factory.getInstance(IBuilder1.IFactory.unsupported());

		@Setter
		private ITypeAdapterFactory<? extends MultiValuedMap<String, ?>> multiValuedMapTypeAdapterFactory = MultiValuedMapTypeAdapter.Factory.getInstance(IBuilder2.IFactory.unsupported());

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
