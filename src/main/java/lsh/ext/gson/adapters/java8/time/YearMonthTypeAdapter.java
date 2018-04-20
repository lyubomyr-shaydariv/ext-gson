package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class YearMonthTypeAdapter
		extends TypeAdapter<YearMonth> {

	private static final TypeAdapter<YearMonth> instance = new YearMonthTypeAdapter();

	private YearMonthTypeAdapter() {
	}

	public static TypeAdapter<YearMonth> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final YearMonth value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public YearMonth read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
