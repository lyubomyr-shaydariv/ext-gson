package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class DayOfWeekTypeAdapter
		extends TypeAdapter<DayOfWeek> {

	private static final TypeAdapter<DayOfWeek> instance = new DayOfWeekTypeAdapter();

	private DayOfWeekTypeAdapter() {
	}

	public static TypeAdapter<DayOfWeek> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final DayOfWeek value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public DayOfWeek read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
