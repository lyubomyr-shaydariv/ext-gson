package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link ParameterizedType} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class ParameterizedTypes {

	private static final Type[][] emptyType2dArray = {};

	/**
	 * @param type
	 * 		Type to inspect. If {@code type} is {@link ParameterizedType}, then {@link ParameterizedType#getActualTypeArguments()} is returned.
	 *
	 * @return A two-dimensional array where each element is an array of a {@link Type} instance or {@code Object.class} but never {@code null}, otherwise an
	 * empty array if no type parameters are available.
	 */
	public static Type[][] getTypeArguments(final Type type) {
		if ( type instanceof final ParameterizedType parameterizedType ) {
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			final Type[][] resultTypeArguments = new Type[actualTypeArguments.length][];
			for ( int i = 0; i < actualTypeArguments.length; i++ ) {
				resultTypeArguments[i] = new Type[] { actualTypeArguments[i] };
			}
			return resultTypeArguments;
		}
		return emptyType2dArray;
	}

}
