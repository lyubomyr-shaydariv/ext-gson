package lsh.ext.gson;

import java.io.IOException;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPrimitiverCursorTypeAdapter<C, PC>
		extends TypeAdapter<C> {

	protected abstract C toCursor(JsonReader jsonReader);

	protected abstract PC toPrimitiveCursor(C cursor);

	protected abstract boolean hasNext(PC primitiveCursor);

	protected abstract void writeNext(JsonWriter out, PC primitiveCursor)
			throws IOException;

	@Override
	public final void write(final JsonWriter out, final C cursor)
			throws IOException {
		final PC primitiveCursor = toPrimitiveCursor(cursor);
		while ( hasNext(primitiveCursor) ) {
			writeNext(out, primitiveCursor);
		}
	}

	@Override
	public final C read(final JsonReader in)
			throws IOException {
		return toCursor(in);
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	public abstract static class AbstractFactory<C>
			extends AbstractTypeAdapterFactory<C> {

		private final Class<C> cursorClass;

		protected abstract TypeAdapter<C> createCursorTypeAdapter();

		@Override
		@Nullable
		protected final TypeAdapter<C> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
			if ( !cursorClass.isAssignableFrom(typeToken.getRawType()) ) {
				return null;
			}
			return createCursorTypeAdapter();
		}

	}

}
