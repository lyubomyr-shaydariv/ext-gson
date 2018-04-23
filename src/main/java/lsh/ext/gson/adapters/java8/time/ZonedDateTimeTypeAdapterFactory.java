package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.NotImplementedYetException;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

public final class ZonedDateTimeTypeAdapterFactory
		extends AbstractTypeAdapterFactory<ZonedDateTime> {

	private static final TypeAdapterFactory instance = new ZonedDateTimeTypeAdapterFactory();

	private ZonedDateTimeTypeAdapterFactory() {
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	@Override
	protected boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == ZonedDateTime.class;
	}

	@Nonnull
	@Override
	protected TypeAdapter<ZonedDateTime> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		throw NotImplementedYetException.create();
	}

}