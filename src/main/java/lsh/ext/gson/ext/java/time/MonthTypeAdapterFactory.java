package lsh.ext.gson.ext.java.time;

import java.time.Month;

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

	private static final TypeAdapterFactory instance = new MonthTypeAdapterFactory();

	private MonthTypeAdapterFactory() {
	}

	/**
	 * @return An instance of {@link MonthTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	@Override
	protected boolean isSupported(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Month.class;
	}

	@Override
	protected TypeAdapter<Month> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return MonthTypeAdapter.getInstance();
	}

}
