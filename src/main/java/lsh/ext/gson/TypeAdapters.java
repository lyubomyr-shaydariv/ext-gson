package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.internal.ConstructorConstructors;

/**
 * Provides miscellaneous {@link TypeAdapter} utility methods.
 *
 * @author Lyubomyr Shaydariv
 */
@UtilityClass
public final class TypeAdapters {

	private static final Iterable<Class<?>> supportedTypeAdapterClasses = List.of(
					TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class, InstanceCreator.class
			);

	private static final Iterable<Class<?>> supportedTypeHierarchyAdapterClasses = List.of(
					TypeAdapter.class, JsonSerializer.class, JsonDeserializer.class
			);

	private static final ConstructorConstructor constructorConstructor = ConstructorConstructors.create(Collections.emptyMap());

	/**
	 * Creates an instance of an object that can be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}. As of Gson 2.8.0 that
	 * method does not allow to register {@link Class} instances, however {@link com.google.gson.annotations.JsonAdapter} can accept {@link Class} only.
	 *
	 * @param clazz
	 * 		A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer} or {@link
	 *        InstanceCreator}.
	 * @param <T>
	 * 		Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 */
	public static <T> T ofConcrete(final Class<?> clazz) {
		for ( final Class<?> expectedClass : supportedTypeAdapterClasses ) {
			if ( expectedClass.isAssignableFrom(clazz) ) {
				return newInstance(clazz);
			}
		}
		throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeAdapterClasses);
	}

	/**
	 * Creates an instance of an object that can be registered in {@link com.google.gson.GsonBuilder#registerTypeHierarchyAdapter(Class, Object)}. As of Gson
	 * 2.8.0 that method does not allow to register {@link Class} instances, however {@link com.google.gson.annotations.JsonAdapter} can accept {@link Class}
	 * only.
	 *
	 * @param clazz
	 * 		A class representing a class that implements any of {@link TypeAdapter}, {@link JsonSerializer}, {@link JsonDeserializer} or {@link
	 *        InstanceCreator}.
	 * @param <T>
	 * 		Type the resolved type adapter should be cast to.
	 *
	 * @return An instance ready to be registered in {@link com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)}
	 */
	public static <T> T ofHierarchy(final Class<?> clazz) {
		for ( final Class<?> expectedClass : supportedTypeHierarchyAdapterClasses ) {
			if ( expectedClass.isAssignableFrom(clazz) ) {
				return newInstance(clazz);
			}
		}
		throw new IllegalArgumentException(clazz + " is not one of the supported classes: " + supportedTypeHierarchyAdapterClasses);
	}

	private static <T> T newInstance(final Class<?> clazz) {
		@SuppressWarnings("unchecked")
		final ObjectConstructor<T> objectConstructor = (ObjectConstructor<T>) constructorConstructor.get(TypeToken.get(clazz));
		return objectConstructor.construct();
	}

}
