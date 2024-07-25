package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractPrimitiverCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

public final class LongStreamTypeAdapter
		extends AbstractPrimitiverCursorTypeAdapter<LongStream, PrimitiveIterator.OfLong> {

	@Getter
	private static final TypeAdapter<LongStream> instance = new LongStreamTypeAdapter();

	@Override
	protected LongStream toCursor(final JsonReader jsonReader) {
		return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(JsonReaders.asLongIterator(jsonReader), 0), false);
	}

	@Override
	protected PrimitiveIterator.OfLong toPrimitiveCursor(final LongStream cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final PrimitiveIterator.OfLong primitiveCursor) {
		return primitiveCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final PrimitiveIterator.OfLong primitiveCursor)
			throws IOException {
		out.value(primitiveCursor.next());
	}

	public static final class Factory
			extends AbstractFactory<LongStream> {

		@Getter
		private static final ITypeAdapterFactory<LongStream> instance = new Factory();

		private Factory() {
			super(LongStream.class);
		}

		@Override
		protected TypeAdapter<LongStream> createCursorTypeAdapter() {
			return LongStreamTypeAdapter.instance;
		}

	}

}
