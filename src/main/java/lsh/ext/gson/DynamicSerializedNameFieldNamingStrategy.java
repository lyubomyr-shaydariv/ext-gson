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

	private final INamingStrategy namingStrategy;
	private final FieldNamingStrategy fieldNamingStrategy;

	public static FieldNamingStrategy getInstance(final INamingStrategy namingStrategy) {
		return getInstance(namingStrategy, FieldNamingPolicy.IDENTITY);
	}

	public static FieldNamingStrategy getInstance(final INamingStrategy namingStrategy, final FieldNamingStrategy fieldNamingStrategy) {
		return new DynamicSerializedNameFieldNamingStrategy(namingStrategy, fieldNamingStrategy);
	}

	@Override
	@SuppressWarnings("ConstantValue")
	public String translateName(final Field field) {
		@Nullable
		final DynamicSerializedName dynamicSerializedName = field.getAnnotation(DynamicSerializedName.class);
		if ( dynamicSerializedName == null ) {
			return fieldNamingStrategy.translateName(field);
		}
		@Nullable
		final String name = namingStrategy.translateName(dynamicSerializedName.value());
		if ( name == null ) {
			return fieldNamingStrategy.translateName(field);
		}
		return name;
	}

}
