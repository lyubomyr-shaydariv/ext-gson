package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lsh.ext.gson.AbstractPrimitiverCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

public final class DoubleStreamTypeAdapter
		extends AbstractPrimitiverCursorTypeAdapter<DoubleStream, PrimitiveIterator.OfDouble> {

	@Getter
	private static final TypeAdapter<DoubleStream> instance = new DoubleStreamTypeAdapter();

	@Override
	protected DoubleStream toCursor(final JsonReader jsonReader) {
		return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(JsonReaders.asDoubleIterator(jsonReader), 0), false);
	}

	@Override
	protected PrimitiveIterator.OfDouble toPrimitiveCursor(final DoubleStream cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final PrimitiveIterator.OfDouble primitiveCursor) {
		return primitiveCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final PrimitiveIterator.OfDouble primitiveCursor)
			throws IOException {
		out.value(primitiveCursor.next());
	}

	public static final class Factory
			extends AbstractFactory<DoubleStream> {

		@Getter
		private static final ITypeAdapterFactory<DoubleStream> instance = new Factory();

		private Factory() {
			super(DoubleStream.class);
		}

		@Override
		protected TypeAdapter<DoubleStream> createCursorTypeAdapter() {
			return DoubleStreamTypeAdapter.instance;
		}

	}

}
