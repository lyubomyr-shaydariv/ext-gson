package lsh.ext.gson.adapters;

import java.lang.reflect.Modifier;

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
public final class AbstractTypeTypeAdapterFactory
		implements TypeAdapterFactory {

	private final String typePropertyName;
	private final String valuePropertyName;

	private AbstractTypeTypeAdapterFactory(final String typePropertyName, final String valuePropertyName) {
		this.typePropertyName = typePropertyName;
		this.valuePropertyName = valuePropertyName;
	}

	/**
	 * @param typePropertyName  Type property name
	 * @param valuePropertyName Value property name
	 *
	 * @return An instance of {@link AbstractTypeTypeAdapterFactory}
	 *
	 * @since 0-SNAPSHOT
	 */
	public static TypeAdapterFactory getAbstractTypeTypeAdapterFactory(final String typePropertyName, final String valuePropertyName) {
		return new AbstractTypeTypeAdapterFactory(typePropertyName, valuePropertyName);
	}

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
		if ( Modifier.isAbstract(typeToken.getRawType().getModifiers()) ) {
			return TypeAwareTypeAdapter.getTypeAwareTypeAdapter(gson, typePropertyName, valuePropertyName);
		}
		return null;
	}

}
