package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ParameterizedTypes {

	@Nullable
	public static Type getTypeArgument(final Type type, final int index) {
		if ( type instanceof final ParameterizedType parameterizedType ) {
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			if ( actualTypeArguments.length == 0 ) {
				return null;
			}
			return actualTypeArguments[index];
		}
		return null;
	}

}
