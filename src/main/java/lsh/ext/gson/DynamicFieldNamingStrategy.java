package lsh.ext.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DynamicFieldNamingStrategy
		implements FieldNamingStrategy {

	private final UnaryOperator<String> translateName;
	private final FieldNamingStrategy fieldNamingStrategy;

	public static FieldNamingStrategy getInstance(final UnaryOperator<String> translateName) {
		return getInstance(translateName, FieldNamingPolicy.IDENTITY);
	}

	public static FieldNamingStrategy getInstance(final UnaryOperator<String> translateName, final FieldNamingStrategy fieldNamingStrategy) {
		return new DynamicFieldNamingStrategy(translateName, fieldNamingStrategy);
	}

	@Override
	public String translateName(final Field field) {
		@Nullable
		final As as = field.getAnnotation(As.class);
		if ( as == null ) {
			return fieldNamingStrategy.translateName(field);
		}
		@Nullable
		final String name = translateName.apply(as.value());
		if ( name == null ) {
			return fieldNamingStrategy.translateName(field);
		}
		return name;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface As {

		String value();

	}

}
