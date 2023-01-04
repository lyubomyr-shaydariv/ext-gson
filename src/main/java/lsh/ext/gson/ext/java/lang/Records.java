package lsh.ext.gson.ext.java.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
final class Records {

	static <T extends Record> Constructor<T> lookupConstructor(final Class<T> recordClass)
			throws NoSuchMethodException {
		return recordClass.getDeclaredConstructor(lookupParameterTypes(recordClass));
	}

	private static Class<?>[] lookupParameterTypes(final Class<? extends Record> recordClass)
			throws IllegalArgumentException {
		@Nullable
		final RecordComponent[] recordComponents = recordClass.getRecordComponents();
		if ( recordComponents == null ) {
			throw new IllegalArgumentException(String.format("%s is not a record class", recordClass));
		}
		return Stream.of(recordComponents)
				.map(RecordComponent::getType)
				.toArray(Class<?>[]::new);
	}

}
