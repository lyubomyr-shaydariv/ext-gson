package lsh.ext.gson.ext.java.lang;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class RecordTypeAdapterTest
		extends AbstractTypeAdapterTest<Record, Record> {

	@Nullable
	@Override
	protected Record finalize(@Nullable final Record value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		try {
			return Stream.of(
					test(
							RecordTypeAdapter.getInstance(FooBar.class, gson),
							"{\"foo\":\"1\",\"bar\":\"2\"}",
							() -> new FooBar("1", "2")
					)
			);
		} catch ( final NoSuchMethodException ex ) {
			throw new AssertionError(ex);
		}
	}

}
