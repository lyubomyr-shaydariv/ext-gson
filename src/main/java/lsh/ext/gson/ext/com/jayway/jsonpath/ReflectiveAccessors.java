package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ReflectiveAccessors {

	@Getter
	private static final Function<TypeToken<?>, Collection<IAccessor<Object, Object>>> declaredFieldAccessors = typeToken -> {
		final Field[] declaredFields = typeToken.getRawType()
				.getDeclaredFields();
		if ( declaredFields.length == 0 ) {
			return Collections.emptyList();
		}
		for ( final Field declaredField : declaredFields ) {
			declaredField.setAccessible(true);
		}
		return fromFields(declaredFields);
	};

	public static Collection<IAccessor<? super Object, ? super Object>> fromFields(final Field... fields) {
		if ( fields.length == 0 ) {
			return Collections.emptyList();
		}
		@Nullable
		Collection<IAccessor<? super Object, ? super Object>> accessors = null;
		for ( final Field field : fields ) {
			@Nullable
			final JsonPathExpression annotation = field.getAnnotation(JsonPathExpression.class);
			if ( annotation == null ) {
				continue;
			}
			if ( accessors == null ) {
				accessors = new ArrayList<>();
			}
			final IAccessor<Object, Object> accessor = new AbstractAccessor<>(JsonPath.compile(annotation.value()), field.getGenericType()) {
				@Override
				public void assignFound(final Object outerValue, final Object innerValue) {
					try {
						field.set(outerValue, innerValue);
					} catch ( final IllegalAccessException ex ) {
						throw new RuntimeException(ex);
					}
				}
			};
			accessors.add(accessor);
		}
		if ( accessors == null ) {
			return Collections.emptyList();
		}
		return accessors;

	}

	@Getter
	private static final Function<TypeToken<?>, Collection<IAccessor<Object, Object>>> methodAccessors = typeToken -> {
		final Method[] methods = typeToken.getRawType()
				.getMethods();
		if ( methods.length == 0 ) {
			return Collections.emptyList();
		}
		return fromMethods(methods);
	};

	public static Collection<IAccessor<? super Object, ? super Object>> fromMethods(final Method... methods) {
		if ( methods.length == 0 ) {
			return Collections.emptyList();
		}
		@Nullable
		Collection<IAccessor<? super Object, ? super Object>> accessors = null;
		for ( final Method method : methods ) {
			@Nullable
			final JsonPathExpression annotation = method.getAnnotation(JsonPathExpression.class);
			if ( annotation == null ) {
				continue;
			}
			if ( method.getParameterCount() != 1 ) {
				throw new IllegalArgumentException(method + " must accept one argument");
			}
			if ( accessors == null ) {
				accessors = new ArrayList<>();
			}
			final IAccessor<Object, Object> accessor = new AbstractAccessor<>(JsonPath.compile(annotation.value()), method.getGenericParameterTypes()[0]) {
				@Override
				public void assignFound(final Object outerValue, final Object innerValue) {
					try {
						method.invoke(outerValue, innerValue);
					} catch ( final IllegalAccessException | InvocationTargetException ex ) {
						throw new RuntimeException(ex);
					}
				}
			};
			accessors.add(accessor);
		}
		if ( accessors == null ) {
			return Collections.emptyList();
		}
		return accessors;
	}

}
