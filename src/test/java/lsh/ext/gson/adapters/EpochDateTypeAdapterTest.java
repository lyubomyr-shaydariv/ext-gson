package lsh.ext.gson.adapters;

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
	public static Iterable<TestWith<? extends Date>> parameters() {
		return ImmutableList.of(
				parameterize(new Date(1488929283000L), "1488929283")
						.get()
		);
	}

	public EpochDateTypeAdapterTest(final TestWith<Date> testWith) {
		super(null, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Date> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return EpochDateTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Date value) {
		return value;
	}

}
