package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

public final class DayOfWeekTypeAdapter
		extends AbstractToStringStringTypeAdapter<DayOfWeek> {

	private static final TypeAdapter<DayOfWeek> instance = new DayOfWeekTypeAdapter();

	private DayOfWeekTypeAdapter() {
	}

	public static TypeAdapter<DayOfWeek> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected DayOfWeek fromString(@Nonnull final String string) {
		return DayOfWeek.valueOf(string);
	}

}
