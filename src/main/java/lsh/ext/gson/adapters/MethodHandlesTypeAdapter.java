package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.internal.Classes;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MethodHandlesTypeAdapter<T>
		extends TypeAdapter<T> {

	private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

	private final Class<T> klass;
	private final InstanceCreator<T> instanceCreator;
	private final Map<String, Item> setterIndex;
	private final Map<String, Item> getterIndex;

	public static <T> TypeAdapter<T> getInstance(final Class<T> klass, final InstanceCreator<T> instanceCreator, final Gson gson) {
		return getInstance(klass, instanceCreator, gson, Gson::getAdapter);
	}

	public static <T> TypeAdapter<T> getInstance(final Class<T> klass, final InstanceCreator<T> instanceCreator, final Gson gson, final TypeAdapterFactory typeAdapterFactory) {
		final Map<String, Item> setterIndex = createSetterIndex(lookup, klass, gson, typeAdapterFactory);
		final Map<String, Item> getterIndex = createGetterIndex(lookup, klass, gson, typeAdapterFactory);
		return new MethodHandlesTypeAdapter<T>(klass, instanceCreator, setterIndex, getterIndex)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter jsonWriter, final T t)
			throws IOException {
		jsonWriter.beginObject();
		for ( final Map.Entry<String, Item> e : getterIndex.entrySet() ) {
			final String name = e.getKey();
			jsonWriter.name(name);
			try {
				final Item item = e.getValue();
				final Object value = item.callSiteTarget.invoke(t);
				item.typeAdapter.write(jsonWriter, value);
			} catch ( final Throwable ex ) {
				throw new RuntimeException(ex);
			}
		}
		jsonWriter.endObject();
	}

	@Override
	public T read(final JsonReader jsonReader)
			throws IOException {
		jsonReader.beginObject();
		final T object = instanceCreator.createInstance(klass);
		while ( jsonReader.hasNext() ) {
			final String name = jsonReader.nextName();
			@Nullable
			final Item item = setterIndex.get(name);
			if ( item == null ) {
				jsonReader.skipValue();
				continue;
			}
			try {
				final Object value = item.typeAdapter.read(jsonReader);
				item.callSiteTarget.invoke(object, value);
			} catch ( final Throwable ex ) {
				throw new RuntimeException(ex);
			}
		}
		jsonReader.endObject();
		return object;
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Item {

		private final MethodHandle callSiteTarget;
		private final TypeAdapter<Object> typeAdapter;

	}

	@SuppressWarnings("UnnecessaryCodeBlock")
	private static Map<String, Item> createSetterIndex(final MethodHandles.Lookup lookup, final Class<?> klass, final Gson gson, final TypeAdapterFactory typeAdapterFactory) {
		final Map<String, Item> itemIndex = new HashMap<>(); // TODO inject desired-order map
		for ( final Iterator<Class<?>> classIterator = Classes.forHierarchyDown(klass); classIterator.hasNext(); ) {
			final Class<?> c = classIterator.next();
			final Method[] methods = c.getDeclaredMethods();
			for ( final Method method : methods ) {
				@Nullable
				final SerializedName serializedName = method.getAnnotation(SerializedName.class);
				if ( serializedName == null ) {
					continue;
				}
				final Type[] genericParameterTypes = method.getGenericParameterTypes();
				if ( genericParameterTypes.length != 1 ) {
					continue;
				}
				final Type setterType = genericParameterTypes[0];
				try {
					final MethodHandle callSiteTarget = lookup.unreflect(method);
					@SuppressWarnings("unchecked")
					final TypeToken<Object> typeToken = (TypeToken<Object>) TypeToken.get(setterType);
					final TypeAdapter<Object> typeAdapter = typeAdapterFactory.create(gson, typeToken);
					final Item item = new Item(callSiteTarget, typeAdapter);
					{
						final String name = serializedName.value();
						itemIndex.putIfAbsent(name, item);
					}
					for ( final String alternateName : serializedName.alternate() ) {
						itemIndex.putIfAbsent(alternateName, item);
					}
				} catch ( final IllegalAccessException ex ) {
					throw new RuntimeException(ex);
				}
			}
		}
		return itemIndex;
	}

	private static Map<String, Item> createGetterIndex(final MethodHandles.Lookup lookup, final Class<?> klass, final Gson gson, final TypeAdapterFactory typeAdapterFactory) {
		final Map<String, Item> itemIndex = new HashMap<>();
		for ( final Iterator<Class<?>> classIterator = Classes.forHierarchyDown(klass); classIterator.hasNext(); ) {
			final Class<?> c = classIterator.next();
			final Method[] methods = c.getDeclaredMethods();
			for ( final Method method : methods ) {
				@Nullable
				final SerializedName serializedName = method.getAnnotation(SerializedName.class);
				if ( serializedName == null || method.getParameterCount() != 0 ) {
					continue;
				}
				final Type getterType = method.getGenericReturnType();
				try {
					final MethodHandle callSiteTarget = lookup.unreflect(method);
					@SuppressWarnings("unchecked")
					final TypeToken<Object> typeToken = (TypeToken<Object>) TypeToken.get(getterType);
					final TypeAdapter<Object> typeAdapter = typeAdapterFactory.create(gson, typeToken);
					final String name = serializedName.value();
					final Item item = new Item(callSiteTarget, typeAdapter);
					itemIndex.putIfAbsent(name, item);
				} catch ( final IllegalAccessException ex ) {
					throw new RuntimeException(ex);
				}
			}
		}
		return itemIndex;
	}

}
