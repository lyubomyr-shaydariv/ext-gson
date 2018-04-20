package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.NotImplementedYetException;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

public final class LocalDateTypeAdapterFactory
		extends AbstractTypeAdapterFactory<LocalDate> {

	private static final TypeAdapterFactory instance = new LocalDateTypeAdapterFactory();

	private LocalDateTypeAdapterFactory() {
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == LocalDate.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDate> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		throw NotImplementedYetException.create();
	}

}
