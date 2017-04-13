package lsh.ext.gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * Provides miscellaneous {@link TypeAdapter} utility methods.
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class TypeAdapters {

	private static final Iterable<Class<?>> supportedTypeAdapterClasses = unmodifiableList(asList(
			TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class, InstanceCreator.class
	));

	private static final Iterable<Class<?>> supportedTypeHierarchyAdapterClasses = unmodifiableList(asList(
			TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class
	));

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
	 * @throws IllegalArgumentException  If the given class instance does not meet its parameter expectations
	 * @throws IllegalAccessException    A rethrown exception
	 * @throws InstantiationException    A rethrown exception
	 * @throws NoSuchMethodException     A rethrown exception
	 * @throws InvocationTargetException A rethrown exception
	 * @see #tryGetTypeAdapterOf(Class)
	 * @since 0-SNAPSHOT
	 */
	public static <T> T getTypeAdapterOf(@Nonnull final Class<?> clazz)
			throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalArgumentException {
		if ( TypeAdapter.class.isAssignableFrom(clazz)
				|| JsonSerializer.class.isAssignableFrom(clazz)
				|| JsonDeserializer.class.isAssignableFrom(clazz)
				|| InstanceCreator.class.isAssignableFrom(clazz) ) {
			return newInstance(clazz);
		}
		throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeAdapterClasses);
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
	 * @throws IllegalArgumentException  If the given class instance does not meet its parameter expectations
	 * @throws InvocationTargetException A rethrown exception
	 * @throws NoSuchMethodException     A rethrown exception
	 * @throws InstantiationException    A rethrown exception
	 * @throws IllegalAccessException    A rethrown exception
	 * @see #tryGetTypeHierarchyAdapterOf(Class)
	 * @since 0-SNAPSHOT
	 */
	public static <T> T getTypeHierarchyAdapterOf(final Class<?> clazz)
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		if ( TypeAdapter.class.isAssignableFrom(clazz)
				|| JsonSerializer.class.isAssignableFrom(clazz)
				|| JsonDeserializer.class.isAssignableFrom(clazz) ) {
			return newInstance(clazz);
		}
		throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeHierarchyAdapterClasses);
	}

	/**
	 * Checked exceptions-free alternative of {@link #getTypeAdapterOf(Class)}.
	 *
	 * @param clazz A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer}
	 *              or {@link InstanceCreator}.
	 * @param <T>   Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 *
	 * @throws RuntimeException An exception to wrap any checked exception occuring in {@link #getTypeAdapterOf(Class)}
	 * @see #getTypeAdapterOf(Class)
	 * @since 0-SNAPSHOT
	 */
	public static <T> T tryGetTypeAdapterOf(@Nonnull final Class<?> clazz)
			throws RuntimeException {
		try {
			return getTypeAdapterOf(clazz);
		} catch ( final IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex ) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Checked exceptions-free alternative of {@link #getTypeHierarchyAdapterOf(Class)}.
	 *
	 * @param clazz A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer}
	 *              or {@link InstanceCreator}.
	 * @param <T>   Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 *
	 * @throws RuntimeException An exception to wrap any checked exception occuring in {@link #getTypeHierarchyAdapterOf(Class)}
	 * @see #getTypeAdapterOf(Class)
	 * @since 0-SNAPSHOT
	 */
	public static <T> T tryGetTypeHierarchyAdapterOf(@Nonnull final Class<?> clazz)
			throws RuntimeException {
		try {
			return getTypeAdapterOf(clazz);
		} catch ( final IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex ) {
			throw new RuntimeException(ex);
		}
	}

	private static <T> T newInstance(final Class<?> clazz)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		final Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
		defaultConstructor.setAccessible(true);
		@SuppressWarnings("unchecked")
		final T instance = (T) defaultConstructor.newInstance();
		return instance;
	}

}
