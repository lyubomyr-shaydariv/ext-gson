package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.hash;

/**
 * Provides miscellaneous {@link ParameterizedType} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class ParameterizedTypes {

	private ParameterizedTypes() {
	}

	/**
	 * @param elementType List element type
	 *
	 * @return Parameterized type for generic lists {@code List<E>}.
	 *
	 * @see #setType(Type)
	 * @see #mapType(Type, Type)
	 * @since 0-SNAPSHOT
	 */
	public static ParameterizedType listType(final Type elementType) {
		return new ListParameterizedType(elementType);
	}

	/**
	 * @param elementType Set element type
	 *
	 * @return Parameterized type for generic sets {@code Set<E>}.
	 *
	 * @see #listType(Type)
	 * @see #mapType(Type, Type)
	 * @since 0-SNAPSHOT
	 */
	public static ParameterizedType setType(final Type elementType) {
		return new SetParameterizedType(elementType);
	}

	/**
	 * @param keyType   Map key type
	 * @param valueType Map value type
	 *
	 * @return parameterized type for generic maps {@code Map<K, V>}.
	 *
	 * @see #listType(Type)
	 * @see #setType(Type)
	 * @since 0-SNAPSHOT
	 */
	public static ParameterizedType mapType(final Type keyType, final Type valueType) {
		return new MapParameterizedType(keyType, valueType);
	}

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
					: emptyTypeArray;
		}

		@Override
		public final Type getOwnerType() {
			return null;
		}

		@Override
		public final boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final AbstractParameterizedType that = (AbstractParameterizedType) o;
			return Objects.equals(rawType, that.rawType) &&
					Arrays.equals(actualTypeArguments, that.actualTypeArguments);
		}

		@Override
		public final int hashCode() {
			return hash(rawType, actualTypeArguments);
		}

		private static final Type[] emptyTypeArray = new Type[0];

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
