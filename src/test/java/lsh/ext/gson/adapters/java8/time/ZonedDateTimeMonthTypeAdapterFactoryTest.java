package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class ZonedDateTimeMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<ZonedDateTime> {

	public ZonedDateTimeMonthTypeAdapterFactoryTest() {
		super(new TypeToken<ZonedDateTime>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return ZonedDateTimeTypeAdapterFactory.get();
	}

}
