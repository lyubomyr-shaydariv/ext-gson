package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class LocalDateTypeAdapter
		extends TypeAdapter<LocalDate> {

	private static final TypeAdapter<LocalDate> instance = new LocalDateTypeAdapter();

	private LocalDateTypeAdapter() {
	}

	public static TypeAdapter<LocalDate> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final LocalDate value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public LocalDate read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
