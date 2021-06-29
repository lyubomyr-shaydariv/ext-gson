package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class MonthDayTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<MonthDay> {

	public MonthDayTypeAdapterFactoryTest() {
		super(MonthDay.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MonthDayTypeAdapterFactory.getDefaultInstance();
	}

}
