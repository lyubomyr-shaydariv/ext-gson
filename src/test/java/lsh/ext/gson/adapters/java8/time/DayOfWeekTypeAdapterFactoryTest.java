package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class DayOfWeekTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<DayOfWeek> {

	public DayOfWeekTypeAdapterFactoryTest() {
		super(new TypeToken<DayOfWeek>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return DayOfWeekTypeAdapterFactory.get();
	}

}
