package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class YearMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return params(new TypeToken<YearMonth>() {});
	}

	public YearMonthTypeAdapterFactoryTest(final TestWith testWith) {
		super(testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return YearMonthTypeAdapterFactory.get();
	}

}
