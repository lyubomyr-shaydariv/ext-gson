package lsh.ext.gson.adapters.java8.time;

import java.time.Year;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class YearTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Year> {

	public YearTypeAdapterFactoryTest() {
		super(Year.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return YearTypeAdapterFactory.get();
	}

}
