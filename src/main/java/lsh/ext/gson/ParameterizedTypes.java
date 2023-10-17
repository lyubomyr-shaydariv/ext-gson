package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link ParameterizedType} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class ParameterizedTypes {

	private static final Type[] emptyTypeArray = {};

	/**
	 * @param type
	 * 		Type to inspect. If {@code type} is {@link ParameterizedType}, then {@link ParameterizedType#getActualTypeArguments()} is returned.
	 *
	 * @return A two-dimensional array where each element is an array of a {@link Type} instance or {@code Object.class} but never {@code null}, otherwise an
	 * empty array if no type parameters are available.
	 */
	public static Type[] getTypeArguments(final Type type) {
		if ( type instanceof final ParameterizedType parameterizedType ) {
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			if ( actualTypeArguments.length == 0 ) {
				return emptyTypeArray;
			}
			final Type[] resultTypeArguments = new Type[actualTypeArguments.length];
			System.arraycopy(actualTypeArguments, 0, resultTypeArguments, 0, actualTypeArguments.length);
			return resultTypeArguments;
		}
		if ( type instanceof final Class<?> klass ) {
			final TypeVariable<?>[] typeParameters = klass.getTypeParameters();
			if ( typeParameters.length == 0 ) {
				return emptyTypeArray;
			}
			final Type[] resultTypeArguments = new Type[typeParameters.length];
			Arrays.fill(resultTypeArguments, Object.class);
			return resultTypeArguments;
		}
		return emptyTypeArray;
	}

}
