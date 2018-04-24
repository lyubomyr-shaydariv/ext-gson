package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class MonthTypeAdapterTest
		extends AbstractTypeAdapterTest<Month> {

	@Parameterized.Parameters
	public static Iterable<TestWith<? extends Month>> parameters() {
		return ImmutableList.of(
				parameterize(Month.SEPTEMBER, "\"SEPTEMBER\"")
						.get()
		);
	}

	public MonthTypeAdapterTest(final TestWith<? extends Month> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Month> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return MonthTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Month value) {
		return value;
	}

}
