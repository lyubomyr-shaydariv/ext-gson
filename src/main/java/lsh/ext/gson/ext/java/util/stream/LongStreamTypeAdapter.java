package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

public final class LongStreamTypeAdapter
		extends AbstractCursorTypeAdapter<LongStream, PrimitiveIterator.OfLong> {

	@Getter
	private static final TypeAdapter<LongStream> instance = new LongStreamTypeAdapter();

	@Override
	protected LongStream toCursor(final JsonReader jsonReader) {
		return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(JsonReaders.asLongIterator(jsonReader), Spliterator.IMMUTABLE), false);
	}

	@Override
	protected PrimitiveIterator.OfLong toElementCursor(final LongStream cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final PrimitiveIterator.OfLong elementCursor) {
		return elementCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final PrimitiveIterator.OfLong elementCursor)
			throws IOException {
		out.value(elementCursor.next());
	}

	public static final class Factory
			extends AbstractCursorTypeAdapter.AbstractFactory<LongStream> {

		@Getter
		private static final ITypeAdapterFactory<? extends LongStream> instance = new Factory(LongStreamTypeAdapter.instance);

		private Factory(final TypeAdapter<LongStream> typeAdapter) {
			super(LongStream.class, typeAdapter);
		}

	}

}
