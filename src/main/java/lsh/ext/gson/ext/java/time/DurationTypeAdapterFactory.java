package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Duration}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Duration> {

	@Getter
	private static final TypeAdapterFactory instance = new DurationTypeAdapterFactory();

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Duration.class;
	}

	@Override
	protected TypeAdapter<Duration> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return DurationTypeAdapter.getInstance();
	}

}
