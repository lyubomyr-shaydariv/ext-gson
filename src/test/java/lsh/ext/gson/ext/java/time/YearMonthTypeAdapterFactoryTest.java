package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class YearMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<YearMonth> {

	public YearMonthTypeAdapterFactoryTest() {
		super(YearMonth.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return YearMonthTypeAdapterFactory.get();
	}

}
