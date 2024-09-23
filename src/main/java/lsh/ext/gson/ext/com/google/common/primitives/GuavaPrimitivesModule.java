package lsh.ext.gson.ext.com.google.common.primitives;

import com.google.common.primitives.ImmutableDoubleArray;
import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.ImmutableLongArray;
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

public final class GuavaPrimitivesModule
		extends AbstractModule {

	@Getter
	private static final IModule instance = Builder.create()
			.build();

	private GuavaPrimitivesModule(final TypeAdapterFactory... typeAdapterFactories) {
		super(typeAdapterFactories);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	public static final class Builder
			implements IBuilder0<IModule> {

		@Setter
		private ITypeAdapterFactory<ImmutableDoubleArray> immutableDoubleArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableDoubleArray;

		@Setter
		private ITypeAdapterFactory<ImmutableIntArray> immutableIntArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableIntegerArray;

		@Setter
		private ITypeAdapterFactory<ImmutableLongArray> immutableLongArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableLongArray;

		public static Builder create() {
			return new Builder();
		}

		@Override
		public IModule build() {
			return new GuavaPrimitivesModule(
					immutableDoubleArrayTypeAdapterFactory,
					immutableIntArrayTypeAdapterFactory,
					immutableLongArrayTypeAdapterFactory
			);
		}

	}

}
