package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.NotImplementedYetException;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

public final class LocalDateTimeTypeAdapterFactory
		extends AbstractTypeAdapterFactory<LocalDateTime> {

	private static final TypeAdapterFactory instance = new LocalDateTimeTypeAdapterFactory();

	private LocalDateTimeTypeAdapterFactory() {
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == LocalDateTime.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDateTime> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		throw NotImplementedYetException.create();
	}

}
