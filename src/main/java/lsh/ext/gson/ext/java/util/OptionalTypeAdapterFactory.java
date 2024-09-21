package lsh.ext.gson.ext.java.util;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.ITypeAdapterFactory;

@UtilityClass
public final class OptionalTypeAdapterFactory {

	@Getter
	private static final ITypeAdapterFactory<OptionalDouble> defaultForOptionalDouble = forOptionalDouble(OptionalDoubleTypeAdapter.getInstance());

	public static ITypeAdapterFactory<OptionalDouble> forOptionalDouble(final TypeAdapter<OptionalDouble> typeAdapter) {
		return ITypeAdapterFactory.forClass(OptionalDouble.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<OptionalInt> defaultForOptionalInt = forOptionalInt(OptionalIntTypeAdapter.getInstance());

	public static ITypeAdapterFactory<OptionalInt> forOptionalInt(final TypeAdapter<OptionalInt> typeAdapter) {
		return ITypeAdapterFactory.forClass(OptionalInt.class, () -> typeAdapter);
	}

	@Getter
	private static final ITypeAdapterFactory<OptionalLong> defaultForOptionalLong = forOptionalLong(OptionalLongTypeAdapter.getInstance());

	public static ITypeAdapterFactory<OptionalLong> forOptionalLong(final TypeAdapter<OptionalLong> typeAdapter) {
		return ITypeAdapterFactory.forClass(OptionalLong.class, () -> typeAdapter);
	}

}
