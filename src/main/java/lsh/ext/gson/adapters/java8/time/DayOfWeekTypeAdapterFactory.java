package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link DayOfWeek}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class DayOfWeekTypeAdapterFactory
		extends AbstractTypeAdapterFactory<DayOfWeek> {

	private static final TypeAdapterFactory instance = new DayOfWeekTypeAdapterFactory();

	private DayOfWeekTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link DayOfWeekTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == DayOfWeek.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<DayOfWeek> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return DayOfWeekTypeAdapter.get();
	}

}
