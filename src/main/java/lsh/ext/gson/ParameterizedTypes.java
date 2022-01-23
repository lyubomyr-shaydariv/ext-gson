package lsh.ext.gson;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.experimental.UtilityClass;

/**
 * Provides miscellaneous {@link ParameterizedType} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class ParameterizedTypes {

	private static final Type[] emptyType1dArray = {};
	private static final Type[][] emptyType2dArray = {};

	/**
	 * @param type Type to inspect. If {@code type} is {@link ParameterizedType}, then {@link ParameterizedType#getActualTypeArguments()} is returned.
	 *             If {@code type} is just {@link GenericDeclaration}, type bounds are extracted.
	 *
	 * @return A two-dimensional array where each element is an array of a {@link Type} instance or {@code Object.class} but never {@code null}, otherwise an
	 * empty array if no type parameters are available.
	 */
	public static Type[][] getTypeArguments(final Type type) {
		if ( type instanceof ParameterizedType ) {
			final ParameterizedType parameterizedType = (ParameterizedType) type;
			final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			final int length = actualTypeArguments.length;
			final Type[][] resultTypeArguments = new Type[length][];
			for ( int i = 0; i < length; i++ ) {
				resultTypeArguments[i] = new Type[]{ actualTypeArguments[i] };
			}
			return resultTypeArguments;
		}
		if ( type instanceof GenericDeclaration ) {
			final GenericDeclaration genericDeclaration = (GenericDeclaration) type;
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

	/**
	 * @param elementType List element type
	 *
	 * @return Parameterized type for generic lists {@code List<E>}.
	 *
	 * @see #setOf(Type)
	 * @see #mapOf(Type, Type)
	 */
	public static ParameterizedType listOf(final Type elementType) {
		return new ListParameterizedType(elementType);
	}

	/**
	 * @param elementType Set element type
	 *
	 * @return Parameterized type for generic sets {@code Set<E>}.
	 *
	 * @see #listOf(Type)
	 * @see #mapOf(Type, Type)
	 */
	public static ParameterizedType setOf(final Type elementType) {
		return new SetParameterizedType(elementType);
	}

	/**
	 * @param keyType   Map key type
	 * @param valueType Map value type
	 *
	 * @return parameterized type for generic maps {@code Map<K, V>}.
	 *
	 * @see #listOf(Type)
	 * @see #setOf(Type)
	 */
	public static ParameterizedType mapOf(final Type keyType, final Type valueType) {
		return new MapParameterizedType(keyType, valueType);
	}

	@EqualsAndHashCode
	private abstract static class AbstractParameterizedType
			implements ParameterizedType {

		private final Type rawType;
		private final Type[] actualTypeArguments;

		private AbstractParameterizedType(final Type rawType, final Type... actualTypeArguments) {
			this.rawType = rawType;
			this.actualTypeArguments = actualTypeArguments;
		}

		@Override
		public final Type getRawType() {
			return rawType;
		}

		@Override
		public final Type[] getActualTypeArguments() {
			return actualTypeArguments.length != 0
					? actualTypeArguments.clone()
					: emptyType1dArray;
		}

		@Override
		@Nullable
		public final Type getOwnerType() {
			return null;
		}

	}

	private static final class ListParameterizedType
			extends AbstractParameterizedType {

		private ListParameterizedType(final Type elementType) {
			super(List.class, elementType);
		}

	}

	private static final class SetParameterizedType
			extends AbstractParameterizedType {

		private SetParameterizedType(final Type elementType) {
			super(Set.class, elementType);
		}

	}

	private static final class MapParameterizedType
			extends AbstractParameterizedType {

		private MapParameterizedType(final Type keyType, final Type valueType) {
			super(Map.class, keyType, valueType);
		}

	}

}
