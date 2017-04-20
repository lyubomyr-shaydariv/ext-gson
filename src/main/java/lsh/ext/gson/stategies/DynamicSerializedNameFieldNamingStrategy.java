package lsh.ext.gson.stategies;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import lsh.ext.gson.annotations.DynamicSerializedName;

/**
 * Represents a field naming strategy where field names must be evaluated dynamically. If the backing {@link IFieldNamingResolver} returns {@code code}, the
 * name is resolved using {@link DynamicSerializedNameFieldNamingStrategy#defaultFieldNamingStrategy} that is currently set to {@link FieldNamingPolicy#IDENTITY}.
 *
 * @author Lyubomyr Shaydariv
 * @see DynamicSerializedName
 * @since 0-SNAPSHOT
 */
public final class DynamicSerializedNameFieldNamingStrategy
		implements FieldNamingStrategy {

	private static final FieldNamingStrategy defaultFieldNamingStrategy = FieldNamingPolicy.IDENTITY;

	private final IFieldNamingResolver fieldNamingResolver;

	private DynamicSerializedNameFieldNamingStrategy(final IFieldNamingResolver fieldNamingResolver) {
		this.fieldNamingResolver = fieldNamingResolver;
	}

	/**
	 * @param fieldNamingResolver A strategy to resolve JSON property names dynamically.
	 *
	 * @return A {@link DynamicSerializedNameFieldNamingStrategy} instance.
	 */
	public static FieldNamingStrategy getDynamicSerializedNameFieldNamingStrategy(final IFieldNamingResolver fieldNamingResolver) {
		return new DynamicSerializedNameFieldNamingStrategy(fieldNamingResolver);
	}

	@Override
	public String translateName(final Field field) {
		final DynamicSerializedName annotation = field.getAnnotation(DynamicSerializedName.class);
		if ( annotation == null ) {
			return defaultFieldNamingStrategy.translateName(field);
		}
		final String resolvedName = fieldNamingResolver.resolveName(annotation.value());
		if ( resolvedName == null ) {
			return defaultFieldNamingStrategy.translateName(field);
		}
		return resolvedName;
	}

}
