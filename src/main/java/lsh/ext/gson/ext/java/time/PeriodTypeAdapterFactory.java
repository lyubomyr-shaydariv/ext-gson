package lsh.ext.gson.ext.java.time;

import java.time.Period;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Period}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class PeriodTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Period> {

	private static final TypeAdapterFactory defaultInstance = new PeriodTypeAdapterFactory();

	private PeriodTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link PeriodTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Period.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<Period> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		return PeriodTypeAdapter.getDefaultInstance();
	}

}
