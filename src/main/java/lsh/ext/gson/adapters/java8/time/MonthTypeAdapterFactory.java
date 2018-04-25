package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Month}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class MonthTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Month> {

	private static final TypeAdapterFactory instance = new MonthTypeAdapterFactory();

	private MonthTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MonthTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Month.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Month> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return MonthTypeAdapter.get();
	}

}
