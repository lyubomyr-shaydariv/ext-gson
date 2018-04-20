package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class EpochDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date> {

	@Parameterized.Parameters
	public static Collection<TestWith<Date>> parameters() {
		return ImmutableList.of(
				testWith(
						new Date(1488929283000L),
						"1488929283"
				)
		);
	}

	public EpochDateTypeAdapterTest(final TestWith<Date> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Date> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return EpochDateTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Date value) {
		return value;
	}

}
