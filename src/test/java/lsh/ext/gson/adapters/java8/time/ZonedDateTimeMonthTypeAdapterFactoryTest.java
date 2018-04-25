package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class ZonedDateTimeMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return params(new TypeToken<ZonedDateTime>() {});
	}

	public ZonedDateTimeMonthTypeAdapterFactoryTest(final TestWith testWith) {
		super(testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return ZonedDateTimeTypeAdapterFactory.get();
	}

}
