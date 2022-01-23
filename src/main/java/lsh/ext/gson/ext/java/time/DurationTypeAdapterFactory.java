package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Duration}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class DurationTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Duration> {

	private static final TypeAdapterFactory defaultInstance = new DurationTypeAdapterFactory();

	private DurationTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link DurationTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Duration.class;
	}

	@Override
	protected TypeAdapter<Duration> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return DurationTypeAdapter.getDefaultInstance();
	}

}
