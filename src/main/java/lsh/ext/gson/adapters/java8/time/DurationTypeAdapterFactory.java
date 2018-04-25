package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Duration}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class DurationTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Duration> {

	private static final TypeAdapterFactory instance = new DurationTypeAdapterFactory();

	private DurationTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link DurationTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Duration.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Duration> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return DurationTypeAdapter.get();
	}

}
