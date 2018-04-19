package lsh.ext.gson.adapters;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

public final class EpochDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date> {

	@Nonnull
	@Override
	protected TypeAdapter<Date> createUnit(@Nonnull final Gson gson) {
		return EpochDateTypeAdapter.get();
	}

	@Nullable
	@Override
	protected Date nullValue() {
		return null;
	}

	@Nonnull
	@Override
	protected Date getValue() {
		return new Date(1488929283000L);
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "1488929283";
	}

}
