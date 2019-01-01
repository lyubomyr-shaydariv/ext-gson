package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class MonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Month> {

	public MonthTypeAdapterFactoryTest() {
		super(new TypeToken<Month>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MonthTypeAdapterFactory.get();
	}

}
