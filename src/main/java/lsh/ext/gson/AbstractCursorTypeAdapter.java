package lsh.ext.gson;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractCursorTypeAdapter<C, EC>
		extends TypeAdapter<C> {

	protected abstract C toCursor(JsonReader jsonReader);

	protected abstract EC toElementCursor(C cursor);

	protected abstract boolean hasNext(EC elementCursor);

	protected abstract void writeNext(JsonWriter out, EC elementCursor)
			throws IOException;

	@Override
	public final void write(final JsonWriter out, final C cursor)
			throws IOException {
		final EC elementCursor = toElementCursor(cursor);
		while ( hasNext(elementCursor) ) {
			writeNext(out, elementCursor);
		}
	}

	@Override
	public final C read(final JsonReader in)
			throws IOException {
		return toCursor(in);
	}

	public abstract static class AbstractFactory<C>
			extends AbstractRawClassTypeAdapterFactory<C> {

		private final TypeAdapter<C> typeAdapter;

		protected AbstractFactory(final Class<C> cursorClass, final TypeAdapter<C> typeAdapter) {
			super(cursorClass);
			this.typeAdapter = typeAdapter;
		}

		@Override
		protected final TypeAdapter<C> createTypeAdapter(final Gson gson, final TypeToken<? super C> typeToken) {
			return typeAdapter;
		}

	}

}
