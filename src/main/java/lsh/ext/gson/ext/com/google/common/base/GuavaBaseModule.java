package lsh.ext.gson.ext.com.google.common.base;

import com.google.common.base.Optional;
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
import lsh.ext.gson.UnmodifiableIterable;

public final class GuavaBaseModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private GuavaBaseModule(final Iterable<? extends TypeAdapterFactory> typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends Optional<?>> optionalTypeAdapterFactory = OptionalTypeAdapter.Factory.getInstance();

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new GuavaBaseModule(UnmodifiableIterable.copyOf(
					optionalTypeAdapterFactory
			));
		}

	}

}
