package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class DurationTypeAdapter
		extends TypeAdapter<Duration> {

	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter();

	private DurationTypeAdapter() {
	}

	public static TypeAdapter<Duration> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final Duration value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public Duration read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
