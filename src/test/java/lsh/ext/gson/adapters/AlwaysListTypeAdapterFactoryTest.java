package lsh.ext.gson.adapters;

import java.util.List;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class AlwaysListTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(List.class)
				)
		);
	}

	public AlwaysListTypeAdapterFactoryTest(final TestWith testWith) {
		super(false, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return AlwaysListTypeAdapterFactory.get();
	}

}
