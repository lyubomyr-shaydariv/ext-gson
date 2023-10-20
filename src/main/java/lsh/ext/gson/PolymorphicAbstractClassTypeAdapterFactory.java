package lsh.ext.gson;

import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a type adapter factory for {@code abstract} classes. {@link TypeAwareTypeAdapter} is used behind the scenes.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PolymorphicAbstractClassTypeAdapterFactory<T>
		extends AbstractTypeAdapterFactory<T> {

	private final String typePropertyName;
	private final String valuePropertyName;

	/**
	 * @param typePropertyName
	 * 		Type property name
	 * @param valuePropertyName
	 * 		Value property name
	 *
	 * @return An instance of {@link PolymorphicAbstractClassTypeAdapterFactory}
	 */
	public static TypeAdapterFactory getInstance(final String typePropertyName, final String valuePropertyName) {
		return new PolymorphicAbstractClassTypeAdapterFactory<>(typePropertyName, valuePropertyName);
	}

	@Override
	protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Modifier.isAbstract(typeToken.getRawType().getModifiers()) ) {
			return null;
		}
		return TypeAwareTypeAdapter.getInstance(gson, typePropertyName, valuePropertyName);
	}

}