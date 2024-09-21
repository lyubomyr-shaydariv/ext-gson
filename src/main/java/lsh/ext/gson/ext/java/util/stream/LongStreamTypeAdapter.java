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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractCursorTypeAdapter;
import lsh.ext.gson.JsonReaders;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LongStreamTypeAdapter
		extends AbstractCursorTypeAdapter<LongStream, PrimitiveIterator.OfLong> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<LongStream> instance = new LongStreamTypeAdapter()
			.nullSafe();

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

}
