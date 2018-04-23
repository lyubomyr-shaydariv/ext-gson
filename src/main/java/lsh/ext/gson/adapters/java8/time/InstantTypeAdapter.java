package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

public final class InstantTypeAdapter
		extends AbstractToStringStringTypeAdapter<Instant> {

	private static final TypeAdapter<Instant> instance = new InstantTypeAdapter();

	private InstantTypeAdapter() {
	}

	public static TypeAdapter<Instant> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected Instant fromString(@Nonnull final String string) {
		return Instant.parse(string);
	}

}
