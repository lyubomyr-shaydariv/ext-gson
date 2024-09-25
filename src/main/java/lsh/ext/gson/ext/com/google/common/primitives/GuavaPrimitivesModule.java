package lsh.ext.gson.ext.com.google.common.primitives;

import com.google.common.primitives.ImmutableDoubleArray;
import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.ImmutableLongArray;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lsh.ext.gson.IBuilder0;
import lsh.ext.gson.IModule;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "create")
@Accessors(fluent = true, chain = true)
public final class GuavaPrimitivesModule
		implements IBuilder0<IModule> {

	@Setter
	private ITypeAdapterFactory<ImmutableDoubleArray> immutableDoubleArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableDoubleArray;

	@Setter
	private ITypeAdapterFactory<ImmutableIntArray> immutableIntArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableIntegerArray;

	@Setter
	private ITypeAdapterFactory<ImmutableLongArray> immutableLongArrayTypeAdapterFactory = GuavaPrimitivesTypeAdapterFactory.defaultForImmutableLongArray;

	@Override
	public IModule build() {
		return IModule.from(
				immutableDoubleArrayTypeAdapterFactory,
				immutableIntArrayTypeAdapterFactory,
				immutableLongArrayTypeAdapterFactory
		);
	}

}
