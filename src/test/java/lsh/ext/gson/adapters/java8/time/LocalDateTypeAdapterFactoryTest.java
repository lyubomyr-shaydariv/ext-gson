package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class LocalDateTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalDate> {

	public LocalDateTypeAdapterFactoryTest() {
		super(LocalDate.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalDateTypeAdapterFactory.get();
	}

}
