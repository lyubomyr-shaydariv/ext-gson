package lsh.ext.gson.adapters;

import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter factory for {@code abstract} classes. {@link TypeAwareTypeAdapter} is used behind the scenes.
 *
 * @author Lyubomyr Shaydariv
 * @see TypeAwareTypeAdapter
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class AbstractClassTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<T> {

	private final String typePropertyName;
	private final String valuePropertyName;

	/**
	 * @param typePropertyName
	 * 		Type property name
	 * @param valuePropertyName
	 * 		Value property name
	 *
	 * @return An instance of {@link AbstractClassTypeAdapterFactory}
	 */
	public static TypeAdapterFactory getInstance(final String typePropertyName, final String valuePropertyName) {
		return new AbstractClassTypeAdapterFactory<>(typePropertyName, valuePropertyName);
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return Modifier.isAbstract(typeToken.getRawType().getModifiers());
	}

	@Override
	protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return TypeAwareTypeAdapter.getInstance(gson, typePropertyName, valuePropertyName);
	}

}
