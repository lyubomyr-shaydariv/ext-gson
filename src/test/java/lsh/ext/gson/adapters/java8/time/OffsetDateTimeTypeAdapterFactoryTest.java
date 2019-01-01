package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class OffsetDateTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<OffsetDateTime> {

	public OffsetDateTimeTypeAdapterFactoryTest() {
		super(new TypeToken<OffsetDateTime>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OffsetDateTimeTypeAdapterFactory.get();
	}

}
