package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class LocalDateTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalDateTime> {

	public LocalDateTimeTypeAdapterFactoryTest() {
		super(LocalDateTime.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalDateTimeTypeAdapterFactory.getDefaultInstance();
	}

}
