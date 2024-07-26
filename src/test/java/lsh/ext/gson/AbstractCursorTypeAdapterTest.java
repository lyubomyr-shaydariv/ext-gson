package lsh.ext.gson;

import java.io.IOException;
import java.util.List;

import com.google.gson.stream.JsonReader;

public abstract class AbstractCursorTypeAdapterTest<C>
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

}
