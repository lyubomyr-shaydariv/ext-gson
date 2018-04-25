package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Instant}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class InstantTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Instant> {

	private static final TypeAdapterFactory instance = new InstantTypeAdapterFactory();

	private InstantTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link InstantTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Instant.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Instant> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return InstantTypeAdapter.get();
	}

}
