package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

public final class DurationTypeAdapter
		extends AbstractToStringStringTypeAdapter<Duration> {

	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter();

	private DurationTypeAdapter() {
	}

	public static TypeAdapter<Duration> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected Duration fromString(@Nonnull final String string) {
		return Duration.parse(string);
	}

}
