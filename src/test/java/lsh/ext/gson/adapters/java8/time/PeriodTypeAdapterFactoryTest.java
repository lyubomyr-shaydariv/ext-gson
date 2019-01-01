package lsh.ext.gson.adapters.java8.time;

import java.time.Period;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class PeriodTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Period> {

	public PeriodTypeAdapterFactoryTest() {
		super(new TypeToken<Period>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return PeriodTypeAdapterFactory.get();
	}

}
