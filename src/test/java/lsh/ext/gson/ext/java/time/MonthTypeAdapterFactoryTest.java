package lsh.ext.gson.ext.java.time;

import java.time.Month;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class MonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Month> {

	public MonthTypeAdapterFactoryTest() {
		super(Month.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MonthTypeAdapterFactory.get();
	}

}
