package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class LocalDateTimeTypeAdapter
		extends TypeAdapter<LocalDateTime> {

	private static final TypeAdapter<LocalDateTime> instance = new LocalDateTimeTypeAdapter();

	private LocalDateTimeTypeAdapter() {
	}

	public static TypeAdapter<LocalDateTime> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final LocalDateTime value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public LocalDateTime read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
