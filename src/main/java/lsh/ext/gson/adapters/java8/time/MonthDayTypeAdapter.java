package lsh.ext.gson.adapters.java8.time;

import java.time.MonthDay;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class MonthDayTypeAdapter
		extends TypeAdapter<MonthDay> {

	private static final TypeAdapter<MonthDay> instance = new MonthDayTypeAdapter();

	private MonthDayTypeAdapter() {
	}

	public static TypeAdapter<MonthDay> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final MonthDay value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public MonthDay read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
