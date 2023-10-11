package lsh.ext.gson;

import java.lang.reflect.Field;
import javax.annotation.Nullable;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DynamicSerializedNameFieldNamingStrategy
		implements FieldNamingStrategy {

	private final IFieldNamingResolver fieldNamingResolver;
	private final FieldNamingStrategy fallbackFieldNamingStrategy;

	public static FieldNamingStrategy getInstance(final IFieldNamingResolver fieldNamingResolver) {
		return new DynamicSerializedNameFieldNamingStrategy(fieldNamingResolver, FieldNamingPolicy.IDENTITY);
	}

	public static FieldNamingStrategy getInstance(final IFieldNamingResolver fieldNamingResolver,
			final FieldNamingStrategy fallbackFieldNamingStrategy) {
		return new DynamicSerializedNameFieldNamingStrategy(fieldNamingResolver, fallbackFieldNamingStrategy);
	}

	@Override
	public String translateName(final Field field) {
		@Nullable
		final DynamicSerializedName annotation = field.getAnnotation(DynamicSerializedName.class);
		if ( annotation == null ) {
			return fallbackFieldNamingStrategy.translateName(field);
		}
		@Nullable
		final String resolvedName = fieldNamingResolver.resolveName(annotation.value());
		if ( resolvedName == null ) {
			return fallbackFieldNamingStrategy.translateName(field);
		}
		return resolvedName;
	}

}
