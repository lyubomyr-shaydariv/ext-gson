package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Month}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonthTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Month> {

	@Getter
	private static final TypeAdapterFactory instance = new MonthTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Month.class;
	}

	@Override
	protected TypeAdapter<Month> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return MonthTypeAdapter.getInstance();
	}

}
