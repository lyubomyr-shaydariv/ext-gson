package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Instant}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class InstantTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Instant> {

	private static final TypeAdapterFactory instance = new InstantTypeAdapterFactory();

	private InstantTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link InstantTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Instant.class;
	}

	@Override
	protected TypeAdapter<Instant> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return InstantTypeAdapter.getInstance();
	}

}
