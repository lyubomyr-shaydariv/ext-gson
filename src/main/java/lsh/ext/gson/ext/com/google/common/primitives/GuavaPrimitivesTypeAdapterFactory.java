package lsh.ext.gson.ext.com.google.common.primitives;

import com.google.common.primitives.ImmutableDoubleArray;
import com.google.common.primitives.ImmutableIntArray;
import com.google.common.primitives.ImmutableLongArray;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class GuavaPrimitivesTypeAdapterFactory {

	public static final ITypeAdapterFactory<ImmutableDoubleArray> defaultForImmutableDoubleArray = forImmutableDoubleArray(ImmutableDoubleArrayTypeAdapter.getInstance());

	public static ITypeAdapterFactory<ImmutableDoubleArray> forImmutableDoubleArray(final TypeAdapter<ImmutableDoubleArray> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(ImmutableDoubleArray.class, typeResolver -> typeAdapter);
	}

	public static final ITypeAdapterFactory<ImmutableIntArray> defaultForImmutableIntegerArray = forImmutableIntArray(ImmutableIntArrayTypeAdapter.getInstance());

	public static ITypeAdapterFactory<ImmutableIntArray> forImmutableIntArray(final TypeAdapter<ImmutableIntArray> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(ImmutableIntArray.class, typeResolver -> typeAdapter);
	}

	public static final ITypeAdapterFactory<ImmutableLongArray> defaultForImmutableLongArray = forImmutableLongArray(ImmutableLongArrayTypeAdapter.getInstance());

	public static ITypeAdapterFactory<ImmutableLongArray> forImmutableLongArray(final TypeAdapter<ImmutableLongArray> typeAdapter) {
		return ITypeAdapterFactory.forClassHierarchy(ImmutableLongArray.class, typeResolver -> typeAdapter);
	}

}
