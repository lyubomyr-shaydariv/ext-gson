package lsh.ext.gson.adapters;

import java.lang.reflect.Modifier;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Represents a type adapter factory for {@code abstract} classes. {@link TypeAwareTypeAdapter} is used behind the scenes.
 *
 * @author Lyubomyr Shaydariv
 * @see TypeAwareTypeAdapter
 * @since 0-SNAPSHOT
 */
public final class AbstractClassTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<T> {

	private final String typePropertyName;
	private final String valuePropertyName;

	private AbstractClassTypeAdapterFactory(final String typePropertyName, final String valuePropertyName) {
		this.typePropertyName = typePropertyName;
		this.valuePropertyName = valuePropertyName;
	}

	/**
	 * @param typePropertyName  Type property name
	 * @param valuePropertyName Value property name
	 *
	 * @return An instance of {@link AbstractClassTypeAdapterFactory}
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory get(final String typePropertyName, final String valuePropertyName) {
		return new AbstractClassTypeAdapterFactory(typePropertyName, valuePropertyName);
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return Modifier.isAbstract(typeToken.getRawType().getModifiers());
	}

	@Nonnull
	@Override
	protected TypeAdapter<T> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return TypeAwareTypeAdapter.get(gson, typePropertyName, valuePropertyName);
	}

}
