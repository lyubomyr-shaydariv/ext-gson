package lsh.ext.gson.ext.java.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class RecordShadowHandler<T extends Record> {

	// TODO is it thread-safe?
	private static final ByteBuddy byteBuddy = new ByteBuddy();

	private final Constructor<? extends T> recordClassConstructor;
	private final Class<?> shadowClass;
	private final Constructor<?> shadowClassConstructor;
	private final Meta[] metadata;

	static <T extends Record> RecordShadowHandler<T> from(final Class<T> recordClass)
			throws IllegalArgumentException, NoSuchMethodException {
		if ( !recordClass.isRecord() ) {
			throw new IllegalArgumentException(recordClass + " is not record class");
		}
		final RecordComponent[] recordComponents = recordClass.getRecordComponents();
		final Class<?> shadowClass = buildShadowClass(recordClass);
		final Meta[] metadata = buildMetadata(recordClass, recordComponents, shadowClass);
		final Constructor<?> shadowClassConstructor = lookupOnlyDeclaredConstructor(shadowClass);
		return new RecordShadowHandler<>(Records.lookupConstructor(recordClass), shadowClass, shadowClassConstructor, metadata);
	}

	TypeAdapter<Object> getShadowClassTypeAdapter(final Gson gson) {
		@SuppressWarnings("unchecked")
		final TypeAdapter<Object> shadowClassTypeAdapter = gson.getAdapter((Class<Object>) shadowClass);
		return shadowClassTypeAdapter;
	}

	Object toShadow(final T record)
			throws InvocationTargetException, InstantiationException, IllegalAccessException {
		final Object shadow = shadowClassConstructor.newInstance();
		for ( final Meta meta : metadata ) {
			final Object value = meta.recordValueMethod.invoke(record);
			meta.shadowValueField.set(shadow, value);
		}
		return shadow;
	}

	T toRecord(final Object shadow)
			throws InvocationTargetException, InstantiationException, IllegalAccessException {
		final Object[] args = new Object[metadata.length];
		for ( int i = 0; i < metadata.length; i++ ) {
			final Meta meta = metadata[i];
			args[i] = meta.shadowValueField.get(shadow);
		}
		return recordClassConstructor.newInstance(args);
	}

	private static record Meta(
			Method recordValueMethod,
			Field shadowValueField
	) {
	}

	private static <T extends Record> Class<?> buildShadowClass(final Class<T> recordClass) {
		DynamicType.Builder<Object> builder = byteBuddy.subclass(Object.class)
				.annotateType(recordClass.getAnnotations());
		for ( final Field declaredField : recordClass.getDeclaredFields() ) {
			// TODO use record components to make fields out of the record components, not the backing fields
			// currently it does not work in some cases
			// [OK] recordComponent.getName()
			// [OK] recordComponent.getGenericType()
			// [n/a] there is no way to get the backend fields modifiers, but the look like they all have Modifier.FINAL | Modifier.PRIVATE (0x10010 - this is the current modifiers at my JDK/JRE)
			// [FAIL] neither recordComponent.getAnnotations() nor recordComponent.getDeclaredAnnotations() work
			builder = builder.defineField(declaredField.getName(), declaredField.getGenericType(), declaredField.getModifiers())
					.annotateField(declaredField.getDeclaredAnnotations())
					.noNestMate();
		}
		final DynamicType.Unloaded<?> unloaded = builder.make();
		final ClassLoader classLoader = recordClass.getClassLoader();
		@SuppressWarnings("unchecked")
		final DynamicType.Loaded<? extends Record> load = (DynamicType.Loaded<? extends Record>) unloaded.load(classLoader);
		return load.getLoaded();
	}

	private static Meta[] buildMetadata(final Class<? extends Record> recordClass, final RecordComponent[] recordComponents, final Class<?> shadowClass)
			throws NoSuchMethodException {
		final Meta[] metadata = new Meta[recordComponents.length];
		final Field[] shadowDeclaredFields = shadowClass.getDeclaredFields();
		for ( int i = 0; i < recordComponents.length; i++ ) {
			final RecordComponent recordComponent = recordComponents[i];
			Method recordValueMethod = recordClass.getMethod(recordComponent.getName());
			final Field shadowValueField = shadowDeclaredFields[i];
			shadowValueField.setAccessible(true);
			metadata[i] = new Meta(recordValueMethod, shadowValueField);
		}
		return metadata;
	}

	private static Constructor<?> lookupOnlyDeclaredConstructor(final Class<?> shadowClass) {
		final Constructor<?>[] declaredConstructors = shadowClass.getDeclaredConstructors();
		if ( declaredConstructors.length != 1 ) {
			throw new AssertionError(String.format(
					"%s must have single constructor, but found %s ",
					shadowClass,
					Arrays.toString(declaredConstructors)
			));
		}
		return declaredConstructors[0];
	}

}
