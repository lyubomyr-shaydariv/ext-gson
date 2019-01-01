package lsh.ext.gson.adapters.java8.time;

import java.time.Year;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class YearTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Year> {

	public YearTypeAdapterFactoryTest() {
		super(new TypeToken<Year>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return YearTypeAdapterFactory.get();
	}

}
