package lsh.ext.gson;

import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractElementCursorTypeAdapter<C, EC>
		extends AbstractCursorTypeAdapter<C, EC> {

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	public abstract static class AbstractElementTypeAdapterFactory<E>
			extends AbstractTypeAdapterFactory<E> {

		private final Class<?> cursorClass;

		protected abstract TypeAdapter<?> createCursorTypeAdapter(TypeAdapter<?> elementTypeAdapter);

		@Override
		@Nullable
		protected final TypeAdapter<E> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !cursorClass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			@Nullable
			final Type elementType = ParameterizedTypes.getTypeArgument(typeToken.getType(), 0);
			assert elementType != null;
			final TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
			@SuppressWarnings("unchecked")
			final TypeAdapter<E> castTypeAdapter = (TypeAdapter<E>) createCursorTypeAdapter(elementTypeAdapter);
			return castTypeAdapter;
		}

	}

}
