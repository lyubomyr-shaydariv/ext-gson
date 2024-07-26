package lsh.ext.gson.ext.com.jayway.jsonpath;

import java.lang.reflect.Field;
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
		final Collection<IAccessor<? super Object, ? super Object>> accessors = new ArrayList<>();
		for ( final Field field : fields ) {
			@Nullable
			final JsonPathExpression jsonPathExpression = field.getAnnotation(JsonPathExpression.class);
			if ( jsonPathExpression == null ) {
				continue;
			}
			final JsonPath jsonPath = JsonPath.compile(jsonPathExpression.value());
			accessors.add(new FieldAccessor<>(jsonPath, field.getGenericType(), field));
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

}
