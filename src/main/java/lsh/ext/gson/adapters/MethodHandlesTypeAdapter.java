package lsh.ext.gson.adapters;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
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
import lsh.ext.gson.Iterators;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MethodHandlesTypeAdapter<T>
		extends TypeAdapter<T> {

	private final Class<T> klass;
	private final InstanceCreator<T> instanceCreator;
	private final Map<String, Element<T>> elementIndex;

	public static <T> TypeAdapter<T> getInstance(final Class<T> klass, final InstanceCreator<T> instanceCreator, final Gson gson) {
		return getInstance(klass, instanceCreator, gson, Gson::getAdapter);
	}

	@SuppressWarnings("UnnecessaryCodeBlock")
	public static <T> TypeAdapter<T> getInstance(
			final Class<T> klass,
			final InstanceCreator<T> instanceCreator,
			final Gson gson,
			final TypeAdapterFactory typeAdapterFactory
	) {
		final Map<String, Element<T>> elementIndex = new HashMap<>();
		for ( final Iterator<Class<?>> classIterator = Iterators.forHierarchyDown(klass); classIterator.hasNext(); ) {
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
					throw new IllegalArgumentException(String.format("Method %s must only have one parameter", method));
				}
				final Type setterType = genericParameterTypes[0];
				try {
					final BiConsumer<T, Object> biConsumer = createBiConsumer(method);
					@SuppressWarnings("unchecked")
					final TypeToken<Object> typeToken = (TypeToken<Object>) TypeToken.get(setterType);
					final TypeAdapter<Object> typeAdapter = typeAdapterFactory.create(gson, typeToken);
					final Element<T> element = new Element<>(biConsumer, typeAdapter);
					{
						final String name = serializedName.value();
						elementIndex.putIfAbsent(name, element);
					}
					for ( final String alternateName : serializedName.alternate() ) {
						elementIndex.putIfAbsent(alternateName, element);
					}
				} catch ( final LambdaConversionException | NoSuchMethodException | IllegalAccessException ex ) {
					throw new RuntimeException(ex);
				}
			}
		}
		return new MethodHandlesTypeAdapter<T>(klass, instanceCreator, elementIndex)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter jsonWriter, final T t)
			throws IOException {
		throw new AssertionError("TODO"); // TODO
	}

	@Override
	public T read(final JsonReader jsonReader)
			throws IOException {
		jsonReader.beginObject();
		final T object = instanceCreator.createInstance(klass);
		while ( jsonReader.hasNext() ) {
			final String name = jsonReader.nextName();
			@Nullable
			final Element<T> element = elementIndex.get(name);
			if ( element == null ) {
				jsonReader.skipValue();
				continue;
			}
			final Object value = element.typeAdapter.read(jsonReader);
			element.biConsumer.accept(object, value);
		}
		jsonReader.endObject();
		return object;
	}

	private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

	private static final MethodType biConsumerMethodType = MethodType.methodType(BiConsumer.class);
	private static final MethodType biConsumerSignatureMethodType = MethodType.methodType(void.class, Object.class, Object.class);

	private static <T, U> BiConsumer<T, U> createBiConsumer(final Method method)
			throws NoSuchMethodException, IllegalAccessException, LambdaConversionException {
		final Class<?>[] parameterTypes = method.getParameterTypes();
		final Class<?> methodClass = method.getDeclaringClass();
		final Class<?> methodReturnType = method.getReturnType();
		final Class<?> parameterType = parameterTypes[0];
		final CallSite callSite = LambdaMetafactory.metafactory(
				lookup,
				"accept",
				biConsumerMethodType,
				biConsumerSignatureMethodType,
				lookup.findVirtual(methodClass, method.getName(), MethodType.methodType(methodReturnType, parameterType)),
				MethodType.methodType(methodReturnType, methodClass, parameterType)
		);
		final MethodHandle callSiteTarget = callSite.getTarget();
		try {
			@SuppressWarnings("unchecked")
			final BiConsumer<T, U> invoke = (BiConsumer<T, U>) callSiteTarget.invoke();
			return invoke;
		} catch ( final Throwable ex ) {
			throw new RuntimeException(ex);
		}
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Element<T> {

		private final BiConsumer<T, Object> biConsumer;
		private final TypeAdapter<Object> typeAdapter;

	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	private static Class<?> boxedTypeOf(final Class<?> klass) {
		if ( !klass.isPrimitive() || klass == void.class ) {
			return klass;
		}
		if ( klass == boolean.class ) {
			return Boolean.class;
		}
		if ( klass == byte.class ) {
			return Byte.class;
		}
		if ( klass == short.class ) {
			return Short.class;
		}
		if ( klass == int.class ) {
			return Integer.class;
		}
		if ( klass == long.class ) {
			return Long.class;
		}
		if ( klass == float.class ) {
			return Float.class;
		}
		if ( klass == double.class ) {
			return Double.class;
		}
		if ( klass == char.class ) {
			return Character.class;
		}
		throw new AssertionError(klass);
	}

}
