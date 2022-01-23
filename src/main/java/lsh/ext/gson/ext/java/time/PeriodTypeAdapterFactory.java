package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Period}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class PeriodTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Period> {

	private static final TypeAdapterFactory instance = new PeriodTypeAdapterFactory();

	private PeriodTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link PeriodTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Period.class;
	}

	@Override
	protected TypeAdapter<Period> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return PeriodTypeAdapter.getInstance();
	}

}
