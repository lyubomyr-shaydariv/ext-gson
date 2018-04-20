package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class OffsetDateTimeTypeAdapter
		extends TypeAdapter<OffsetDateTime> {

	private static final TypeAdapter<OffsetDateTime> instance = new OffsetDateTimeTypeAdapter();

	private OffsetDateTimeTypeAdapter() {
	}

	public static TypeAdapter<OffsetDateTime> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final OffsetDateTime value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public OffsetDateTime read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
