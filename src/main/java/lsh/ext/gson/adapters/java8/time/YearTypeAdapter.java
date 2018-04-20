package lsh.ext.gson.adapters.java8.time;

import java.time.Year;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class YearTypeAdapter
		extends TypeAdapter<Year> {

	private static final TypeAdapter<Year> instance = new YearTypeAdapter();

	private YearTypeAdapter() {
	}

	public static TypeAdapter<Year> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final Year value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public Year read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
