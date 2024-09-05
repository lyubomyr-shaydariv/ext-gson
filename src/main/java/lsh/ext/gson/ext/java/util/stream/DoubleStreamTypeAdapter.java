package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractCursorTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;
import lsh.ext.gson.JsonReaders;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoubleStreamTypeAdapter
		extends AbstractCursorTypeAdapter<DoubleStream, PrimitiveIterator.OfDouble> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<DoubleStream> instance = new DoubleStreamTypeAdapter()
			.nullSafe();

	@Override
	protected DoubleStream toCursor(final JsonReader jsonReader) {
		return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(JsonReaders.asDoubleIterator(jsonReader), Spliterator.IMMUTABLE), false);
	}

	@Override
	protected PrimitiveIterator.OfDouble toElementCursor(final DoubleStream cursor) {
		return cursor.iterator();
	}

	@Override
	protected boolean hasNext(final PrimitiveIterator.OfDouble elementCursor) {
		return elementCursor.hasNext();
	}

	@Override
	protected void writeNext(final JsonWriter out, final PrimitiveIterator.OfDouble elementCursor)
			throws IOException {
		out.value(elementCursor.next());
	}

	public static final class Factory
			extends AbstractCursorTypeAdapter.AbstractFactory<DoubleStream> {

		@Getter
		private static final ITypeAdapterFactory<? extends DoubleStream> instance = new Factory(DoubleStreamTypeAdapter.instance);

		private Factory(final TypeAdapter<DoubleStream> typeAdapter) {
			super(DoubleStream.class, typeAdapter);
		}

	}

}
