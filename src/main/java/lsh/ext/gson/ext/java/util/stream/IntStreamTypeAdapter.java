package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

public final class IntStreamTypeAdapter
		extends AbstractCursorTypeAdapter<IntStream, PrimitiveIterator.OfInt> {

	@Getter
	private static final TypeAdapter<IntStream> instance = new IntStreamTypeAdapter();

	@Override
	protected IntStream toCursor(final JsonReader jsonReader) {
		return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(JsonReaders.asIntIterator(jsonReader), Spliterator.IMMUTABLE), false);
	}

	@Override
	protected PrimitiveIterator.OfInt toElementCursor(final IntStream cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final PrimitiveIterator.OfInt elementCursor) {
		return elementCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final PrimitiveIterator.OfInt elementCursor)
			throws IOException {
		out.value(elementCursor.next());
	}

	public static final class Factory
			extends AbstractFactory<IntStream> {

		@Getter
		private static final ITypeAdapterFactory<IntStream> instance = new Factory();

		private Factory() {
			super(IntStream.class);
		}

		@Override
		protected TypeAdapter<IntStream> createCursorTypeAdapter() {
			return IntStreamTypeAdapter.instance;
		}

	}

}
