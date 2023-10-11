package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Instant}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstantTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Instant> {

	/**
	 * An instance of {@link InstantTypeAdapterFactory}.
	 */
	@Getter
	private static final TypeAdapterFactory instance = new InstantTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Instant.class;
	}

	@Override
	protected TypeAdapter<Instant> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return InstantTypeAdapter.getInstance();
	}

}
