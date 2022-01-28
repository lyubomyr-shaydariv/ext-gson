package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link DayOfWeek}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOfWeekTypeAdapterFactory
		extends AbstractTypeAdapterFactory<DayOfWeek> {

	private static final TypeAdapterFactory instance = new DayOfWeekTypeAdapterFactory();

	/**
	 * @return An instance of {@link DayOfWeekTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == DayOfWeek.class;
	}

	@Override
	protected TypeAdapter<DayOfWeek> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return DayOfWeekTypeAdapter.getInstance();
	}

}
