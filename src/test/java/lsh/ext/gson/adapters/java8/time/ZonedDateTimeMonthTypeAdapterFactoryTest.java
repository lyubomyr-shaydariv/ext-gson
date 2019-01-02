package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class ZonedDateTimeMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<ZonedDateTime> {

	public ZonedDateTimeMonthTypeAdapterFactoryTest() {
		super(ZonedDateTime.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return ZonedDateTimeTypeAdapterFactory.get();
	}

}
