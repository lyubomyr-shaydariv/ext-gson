package lsh.ext.gson.adapters.java8.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class PeriodTypeAdapter
		extends TypeAdapter<Period> {

	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter();

	private PeriodTypeAdapter() {
	}

	public static TypeAdapter<Period> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final Period value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public Period read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
