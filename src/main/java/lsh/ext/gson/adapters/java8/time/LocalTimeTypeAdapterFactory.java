package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.NotImplementedYetException;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

public final class LocalTimeTypeAdapterFactory
		extends AbstractTypeAdapterFactory<LocalTime> {

	private static final TypeAdapterFactory instance = new LocalTimeTypeAdapterFactory();

	private LocalTimeTypeAdapterFactory() {
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == LocalTime.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalTime> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		throw NotImplementedYetException.create();
	}

}
