package lsh.ext.gson.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ParameterizedTypes {

	@Nullable
	public static Type getTypeArgument(final Type type, final int index) {
		if ( !(type instanceof final ParameterizedType parameterizedType) ) {
			return null;
		}
		final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		if ( index < 0 || index >= actualTypeArguments.length ) {
			throw new IllegalArgumentException(index + " is out of bounds for " + type + " that has " + actualTypeArguments.length + " type argument(s)");
		}
		return actualTypeArguments[index];
	}

	public static Type getTypeArgumentOrObjectClass(final Type type, final int index) {
		@Nullable
		final Type typeArgument = getTypeArgument(type, index);
		if ( typeArgument == null ) {
			return Object.class;
		}
		return typeArgument;
	}

}
