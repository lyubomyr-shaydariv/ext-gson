package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class LocalDateTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalDate> {

	public LocalDateTypeAdapterFactoryTest() {
		super(new TypeToken<LocalDate>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalDateTypeAdapterFactory.get();
	}

}
