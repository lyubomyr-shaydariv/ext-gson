package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

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

public final class Java8UtilModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private Java8UtilModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<? extends Optional<?>> optionalTypeAdapterFactory = OptionalTypeAdapter.Factory.getInstance();

		@Setter
		private ITypeAdapterFactory<OptionalDouble> optionalDoubleTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalDouble();

		@Setter
		private ITypeAdapterFactory<OptionalInt> optionalIntTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalInt();

		@Setter
		private ITypeAdapterFactory<OptionalLong> optionalLongTypeAdapterFactory = OptionalTypeAdapterFactory.getDefaultForOptionalLong();

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new Java8UtilModule(
					optionalTypeAdapterFactory,
					optionalDoubleTypeAdapterFactory,
					optionalIntTypeAdapterFactory,
					optionalLongTypeAdapterFactory
			);
		}

	}

}
