package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Period}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PeriodTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Period> {

	@Getter
	private static final TypeAdapterFactory instance = new PeriodTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Period.class;
	}

	@Override
	protected TypeAdapter<Period> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return PeriodTypeAdapter.getInstance();
	}

}
