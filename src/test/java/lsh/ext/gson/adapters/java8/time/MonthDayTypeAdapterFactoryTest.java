package lsh.ext.gson.adapters.java8.time;

import java.time.MonthDay;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class MonthDayTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<MonthDay> {

	public MonthDayTypeAdapterFactoryTest() {
		super(new TypeToken<MonthDay>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MonthDayTypeAdapterFactory.get();
	}

}
