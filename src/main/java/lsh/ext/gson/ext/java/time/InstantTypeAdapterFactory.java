package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Instant}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstantTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Instant> {

	private static final TypeAdapterFactory instance = new InstantTypeAdapterFactory();

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
