package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nullable;

import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Accessors {

	public static Collection<IAccessor<? super Object, ? super Object>> getFieldAccessors(@SuppressWarnings("TypeParameterExtendsFinalClass") final Iterable<? extends Field> fields) {
		@Nullable
		Collection<IAccessor<? super Object, ? super Object>> accessors = null;
		for ( final Field field : fields ) {
			@Nullable
			final JsonPathExpression jsonPathExpression = field.getAnnotation(JsonPathExpression.class);
			if ( jsonPathExpression == null ) {
				continue;
			}
			if ( accessors == null ) {
				accessors = new ArrayList<>();
			}
			final JsonPath jsonPath = JsonPath.compile(jsonPathExpression.value());
			accessors.add(new FieldAccessor<>(jsonPath, field.getGenericType(), field));
		}
		if ( accessors == null ) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableCollection(accessors);
	}

	public static Collection<IAccessor<? super Object, ? super Object>> getMethodsAccessors(@SuppressWarnings("TypeParameterExtendsFinalClass") final Iterable<? extends Method> methods) {
		@Nullable
		Collection<IAccessor<? super Object, ? super Object>> accessors = null;
		for ( final Method method : methods ) {
			@Nullable
			final JsonPathExpression jsonPathExpression = method.getAnnotation(JsonPathExpression.class);
			if ( jsonPathExpression == null ) {
				continue;
			}
			if ( method.getParameterCount() != 1 ) {
				throw new IllegalArgumentException(method + " must accept one argument");
			}
			if ( accessors == null ) {
				accessors = new ArrayList<>();
			}
			final JsonPath jsonPath = JsonPath.compile(jsonPathExpression.value());
			accessors.add(new MethodAccessor<>(jsonPath, method.getGenericParameterTypes()[0], method));
		}
		if ( accessors == null ) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableCollection(accessors);
	}

	private static final class FieldAccessor<T>
			extends AbstractAccessor<Object, T> {

		private final Field field;

		private FieldAccessor(final JsonPath jsonPath, final Type type, final Field field) {
			super(jsonPath, type);
			this.field = field;
		}

		@Override
		public void assign(final Object superValue, final Object innerValue) {
			try {
				field.set(superValue, innerValue);
			} catch ( final IllegalAccessException ex ) {
				throw new RuntimeException(ex);
			}
		}

	}

	private static final class MethodAccessor<T>
			extends AbstractAccessor<Object, T> {

		private final Method method;

		private MethodAccessor(final JsonPath jsonPath, final Type type, final Method method) {
			super(jsonPath, type);
			this.method = method;
		}

		@Override
		public void assign(final Object superValue, final Object innerValue) {
			try {
				method.invoke(superValue, innerValue);
			} catch ( final IllegalAccessException | InvocationTargetException ex ) {
				throw new RuntimeException(ex);
			}
		}

	}

}
