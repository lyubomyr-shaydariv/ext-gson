package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class LocalTimeTypeAdapter
		extends TypeAdapter<LocalTime> {

	private static final TypeAdapter<LocalTime> instance = new LocalTimeTypeAdapter();

	private LocalTimeTypeAdapter() {
	}

	public static TypeAdapter<LocalTime> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final LocalTime value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public LocalTime read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
