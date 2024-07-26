package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
final class Sources {

	@SuppressWarnings("Java9CollectionFactory")
	static List<Field> toDeclaredFields(final TypeToken<?> typeToken) {
		final Field[] declaredFields = typeToken.getRawType()
				.getDeclaredFields();
		for ( final Field declaredField : declaredFields ) {
			declaredField.setAccessible(true);
		}
		return Collections.unmodifiableList(Arrays.asList(declaredFields));
	}

}
