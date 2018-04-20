package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class OffsetTimeTypeAdapter
		extends TypeAdapter<OffsetTime> {

	private static final TypeAdapter<OffsetTime> instance = new OffsetTimeTypeAdapter();

	private OffsetTimeTypeAdapter() {
	}

	public static TypeAdapter<OffsetTime> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final OffsetTime value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public OffsetTime read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
