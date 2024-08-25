package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractElementCursorTypeAdapter<C, EC, E>
		extends AbstractCursorTypeAdapter<C, EC> {

	private final TypeAdapter<E> elementTypeAdapter;

	protected abstract C toCursor(JsonReader jsonReader, TypeAdapter<E> elementTypeAdapter);

	protected abstract void writeNext(JsonWriter out, EC elementCursor, TypeAdapter<E> elementTypeAdapter)
			throws IOException;

	@Override
	protected final C toCursor(final JsonReader jsonReader) {
		return toCursor(jsonReader, elementTypeAdapter);
	}

	@Override
	protected final void writeNext(final JsonWriter out, final EC elementCursor)
			throws IOException {
		writeNext(out, elementCursor, elementTypeAdapter);
	}

	public abstract static class AbstractElementTypeAdapterFactory<E>
			extends AbstractHierarchyTypeAdapterFactory<E> {

		@SuppressWarnings("unchecked")
		protected AbstractElementTypeAdapterFactory(final Class<?> cursorClass) {
			super((Class<E>) cursorClass);
		}

		protected abstract TypeAdapter<?> createCursorTypeAdapter(TypeAdapter<?> elementTypeAdapter);

		@Override
		protected final TypeAdapter<E> createTypeAdapter(final Gson gson, final TypeToken<? super E> typeToken) {
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
