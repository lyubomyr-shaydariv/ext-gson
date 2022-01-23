package lsh.ext.gson.ext.java.time;

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
 */
public final class MonthTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Month> {

	private static final TypeAdapterFactory defaultInstance = new MonthTypeAdapterFactory();

	private MonthTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MonthTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Month.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Month> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return MonthTypeAdapter.getDefaultInstance();
	}

}
