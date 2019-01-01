package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class LocalDateTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalDateTime> {

	public LocalDateTimeTypeAdapterFactoryTest() {
		super(new TypeToken<LocalDateTime>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalDateTimeTypeAdapterFactory.get();
	}

}
