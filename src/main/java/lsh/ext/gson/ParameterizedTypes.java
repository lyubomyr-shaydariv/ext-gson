package lsh.ext.gson;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

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
	 * 		Type to inspect. If {@code type} is {@link ParameterizedType}, then {@link ParameterizedType#getActualTypeArguments()} is returned. If {@code
	 * 		type} is just {@link GenericDeclaration}, type bounds are extracted.
	 *
	 * @return A two-dimensional array where each element is an array of a {@link Type} instance or {@code Object.class} but never {@code null}, otherwise an
	 * empty array if no type parameters are available.
	 */
	public static Type[][] getTypeArguments(final Type type) {
		if ( type instanceof final ParameterizedType parameterizedType ) {
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			final int length = actualTypeArguments.length;
			final Type[][] resultTypeArguments = new Type[length][];
			for ( int i = 0; i < length; i++ ) {
				resultTypeArguments[i] = new Type[] { actualTypeArguments[i] };
			}
			return resultTypeArguments;
		}
		if ( type instanceof final GenericDeclaration genericDeclaration ) {
			final TypeVariable<?>[] typeParameters = genericDeclaration.getTypeParameters();
			final int length = typeParameters.length;
			if ( length != 0 ) {
				final Type[][] resultTypeParameters = new Type[length][];
				for ( int i = 0; i < length; i++ ) {
					final TypeVariable<?> typeParameter = typeParameters[i];
					resultTypeParameters[i] = typeParameter.getBounds();
				}
				return resultTypeParameters;
			}
		}
		return emptyType2dArray;
	}

}
