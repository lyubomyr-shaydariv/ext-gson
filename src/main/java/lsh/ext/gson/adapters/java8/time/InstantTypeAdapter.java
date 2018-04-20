package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class InstantTypeAdapter
		extends TypeAdapter<Instant> {

	private static final TypeAdapter<Instant> instance = new InstantTypeAdapter();

	private InstantTypeAdapter() {
	}

	public static TypeAdapter<Instant> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final Instant value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public Instant read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
