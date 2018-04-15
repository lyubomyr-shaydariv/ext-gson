package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import javax.annotation.Nonnull;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

/**
 * Provides miscellaneous {@link TypeAdapter} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class TypeAdapters {

	private static final Iterable<Class<?>> supportedTypeAdapterClasses = Collections.unmodifiableList(Arrays.asList(
			TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class, InstanceCreator.class
	));

	private static final Iterable<Class<?>> supportedTypeHierarchyAdapterClasses = Collections.unmodifiableList(Arrays.asList(
			TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class
	));

	private static final ConstructorConstructor constructorConstructor = new ConstructorConstructor(Collections.emptyMap());

	private TypeAdapters() {
	}

	/**
	 * <p>
	 * Creates an instance of an object that can be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}.
	 * </p>
	 * <p>
	 * As of Gson 2.8.0 that method does not allow to register {@link Class} instances, however {@link com.google.gson.annotations.JsonAdapter} can accept
	 * {@link Class} only.
	 * </p>
	 *
	 * @param clazz A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer}
	 *              or {@link InstanceCreator}.
	 * @param <T>   Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 *
	 * @throws IllegalArgumentException If the given class instance does not meet its parameter expectations
	 * @since 0-SNAPSHOT
	 */
	@SuppressWarnings("OverlyComplexBooleanExpression")
	public static <T> T ofConcrete(@Nonnull final Class<?> clazz)
			throws IllegalArgumentException {
		if ( !TypeAdapter.class.isAssignableFrom(clazz)
				&& !JsonSerializer.class.isAssignableFrom(clazz)
				&& !JsonDeserializer.class.isAssignableFrom(clazz)
				&& !InstanceCreator.class.isAssignableFrom(clazz) ) {
			throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeAdapterClasses);
		}
		return newInstance(clazz);
	}

	/**
	 * <p>
	 * Creates an instance of an object that can be registered in {@link com.google.gson.GsonBuilder#registerTypeHierarchyAdapter(Class, Object)}.
	 * </p>
	 * <p>
	 * As of Gson 2.8.0 that method does not allow to register {@link Class} instances, however {@link com.google.gson.annotations.JsonAdapter} can accept
	 * {@link Class} only.
	 * </p>
	 *
	 * @param clazz A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer}
	 *              or {@link InstanceCreator}.
	 * @param <T>   Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 *
	 * @throws IllegalArgumentException If the given class instance does not meet its parameter expectations
	 * @since 0-SNAPSHOT
	 */
	@SuppressWarnings("OverlyComplexBooleanExpression")
	public static <T> T ofHierarchy(final Class<?> clazz) {
		if ( !TypeAdapter.class.isAssignableFrom(clazz)
				&& !JsonSerializer.class.isAssignableFrom(clazz)
				&& !JsonDeserializer.class.isAssignableFrom(clazz) ) {
			throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeHierarchyAdapterClasses);
		}
		return newInstance(clazz);
	}

	private static <T> T newInstance(final Class<?> clazz) {
		@SuppressWarnings("unchecked")
		final ObjectConstructor<T> objectConstructor = (ObjectConstructor<T>) constructorConstructor.get(TypeToken.get(clazz));
		return objectConstructor.construct();
	}

}
