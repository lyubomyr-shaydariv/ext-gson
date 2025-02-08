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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MethodHandlesTypeAdapter<T>
		extends TypeAdapter<T> {

	public interface IDeserializer {

		<T> T read(Type type, JsonReader jsonReader);

	}

	private final Class<T> klass;
	private final InstanceCreator<T> instanceCreator;
	private final Map<String, BiConsumer<T, Object>> biConsumerIndex;
	private final Map<String, Type> typeIndex;
	private final IDeserializer deserializer;

	@SuppressWarnings("UnnecessaryCodeBlock")
	public static <T> TypeAdapter<T> getInstance(
			final Class<T> klass,
			final InstanceCreator<T> instanceCreator,
			final IDeserializer deserializer
	) {
		final Map<String, BiConsumer<T, Object>> biConsumerIndex = new HashMap<>();
		final Map<String, Type> typeIndex = new HashMap<>();
		for ( final Class<?> c : classHierarchyDown(klass) ) {
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
					{
						final String name = serializedName.value();
						biConsumerIndex.putIfAbsent(name, biConsumer);
						typeIndex.putIfAbsent(name, setterType);
					}
					for ( final String alternateName : serializedName.alternate() ) {
						biConsumerIndex.putIfAbsent(alternateName, biConsumer);
						typeIndex.putIfAbsent(alternateName, setterType);
					}
				} catch ( LambdaConversionException | NoSuchMethodException | IllegalAccessException e ) {
					throw new RuntimeException(e);
				}
			}
		}
		return new MethodHandlesTypeAdapter<T>(klass, instanceCreator, biConsumerIndex, typeIndex, deserializer)
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
			final BiConsumer<T, Object> biConsumer = biConsumerIndex.get(name);
			if ( biConsumer == null ) {
				jsonReader.skipValue();
				continue;
			}
			final Type type = typeIndex.get(name);
			final Object value = deserializer.read(type, jsonReader);
			biConsumer.accept(object, value);
		}
		jsonReader.endObject();
		return object;
	}

	private static Collection<Class<?>> classHierarchyDown(final Class<?> klass) {
		final List<Class<?>> classes = new ArrayList<>();
		for ( Class<?> c = klass; c != null; c = c.getSuperclass() ) {
			classes.add(0, c);
		}
		return Collections.unmodifiableList(classes);
	}

	private static final MethodHandles.Lookup lookup = MethodHandles.lookup();

	private static final MethodType biConsumerMethodType = MethodType.methodType(BiConsumer.class);
	private static final MethodType biConsumerSignatureMethodType = MethodType.methodType(void.class, Object.class, Object.class);
	private static final String BICONSUMER_METHOD_NAME = "accept";

	private static <T, U> BiConsumer<T, U> createBiConsumer(final Method method)
			throws NoSuchMethodException, IllegalAccessException, LambdaConversionException {
		final Class<?>[] parameterTypes = method.getParameterTypes();
		final Class<?> methodClass = method.getDeclaringClass();
		final Class<?> methodReturnType = method.getReturnType();
		final CallSite callSite = LambdaMetafactory.metafactory(
				lookup,
				BICONSUMER_METHOD_NAME,
				biConsumerMethodType,
				biConsumerSignatureMethodType,
				lookup.findVirtual(methodClass, method.getName(), MethodType.methodType(methodReturnType, parameterTypes[0])),
				MethodType.methodType(methodReturnType, methodClass, parameterTypes[0])
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

	private static Class<?> boxedTypeOf(final Class<?> klass) {
		if ( !klass.isPrimitive() ) {
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
