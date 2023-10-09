package lsh.ext.gson.ext.java.time.temporal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTemporalAccessorTypeAdapterFactory<T extends TemporalAccessor>
		extends AbstractTypeAdapterFactory<T> {

	private final Class<T> clazz;

	@Nullable
	private final DateTimeFormatter dateTimeFormatter;

	protected abstract TypeAdapter<T> create();

	protected abstract TypeAdapter<T> create(DateTimeFormatter dateTimeFormatter);

	@Override
	protected final boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == clazz;
	}

	@Override
	protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( dateTimeFormatter == null ) {
			return create();
		}
		return create(dateTimeFormatter);
	}

}
