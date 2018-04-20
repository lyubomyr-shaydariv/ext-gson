package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.NotImplementedYetException;

public final class ZonedDateTimeTypeAdapter
		extends TypeAdapter<ZonedDateTime> {

	private static final TypeAdapter<ZonedDateTime> instance = new ZonedDateTimeTypeAdapter();

	private ZonedDateTimeTypeAdapter() {
	}

	public static TypeAdapter<ZonedDateTime> get() {
		return instance;
	}

	@Override
	public void write(final JsonWriter out, final ZonedDateTime value) {
		throw NotImplementedYetException.create();
	}

	@Override
	public ZonedDateTime read(final JsonReader in) {
		throw NotImplementedYetException.create();
	}

}
