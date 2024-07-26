package lsh.ext.gson;

import java.io.IOException;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class AbstractElementCursorTypeAdapterTest<C>
		extends AbstractTypeAdapterTest<C, List<?>> {

	@Override
	protected final void initialize(final JsonReader jsonReader)
			throws IOException {
		super.initialize(jsonReader);
		jsonReader.beginArray();
	}

	@Override
	protected final void finalize(final JsonReader jsonReader)
			throws IOException {
		jsonReader.endArray();
		super.finalize(jsonReader);
	}

	@Override
	protected final void initialize(final JsonWriter jsonWriter)
			throws IOException {
		super.initialize(jsonWriter);
		jsonWriter.beginArray();
	}

	@Override
	protected final void finalize(final JsonWriter jsonWriter)
			throws IOException {
		jsonWriter.endArray();
		super.finalize(jsonWriter);
	}

}
