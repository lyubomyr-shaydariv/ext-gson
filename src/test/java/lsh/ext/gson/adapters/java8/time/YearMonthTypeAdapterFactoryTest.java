package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class YearMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<YearMonth> {

	public YearMonthTypeAdapterFactoryTest() {
		super(new TypeToken<YearMonth>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return YearMonthTypeAdapterFactory.get();
	}

}
