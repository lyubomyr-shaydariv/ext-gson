package lsh.ext.gson.adapters.java8.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class MonthTypeAdapter
		extends TypeAdapter<Month> {

	private static final TypeAdapter<Month> instance = new MonthTypeAdapter();

	private MonthTypeAdapter() {
	}

	public static TypeAdapter<Month> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final Month value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public Month read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
