package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class DayOfWeekTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<DayOfWeek> {

	public DayOfWeekTypeAdapterFactoryTest() {
		super(DayOfWeek.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return DayOfWeekTypeAdapterFactory.get();
	}

}
