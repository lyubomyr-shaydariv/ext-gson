package lsh.ext.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCursorTypeAdapter<C, E>
		extends TypeAdapter<C> {

	private final TypeAdapter<E> elementTypeAdapter;

	protected abstract Iterator<E> toIterator(C cursor);

	protected abstract C fromIterator(Iterator<? extends E> iterator);

	@Override
	public final void write(final JsonWriter out, final C cursor)
			throws IOException {
		out.beginArray();
		final Iterator<? extends E> iterator = toIterator(cursor);
		while ( iterator.hasNext() ) {
			final E element = iterator.next();
			elementTypeAdapter.write(out, element);
		}
		out.endArray();
	}

	@Override
	public final C read(final JsonReader in) {
		return fromIterator(JsonReaders.asIterator(in, elementTypeAdapter));
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	public abstract static class AbstractFactory<E>
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
