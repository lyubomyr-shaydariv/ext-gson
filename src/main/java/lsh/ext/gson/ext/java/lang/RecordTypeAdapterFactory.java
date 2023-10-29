package lsh.ext.gson.ext.java.lang;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecordTypeAdapterFactory<T extends Record>
		extends AbstractTypeAdapterFactory<T> {

	@Getter
	private static final ITypeAdapterFactory<Record> instance = new RecordTypeAdapterFactory<>();

	@Override
	protected TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( !Record.class.isAssignableFrom(typeToken.getRawType()) ) {
			return null;
		}
		@SuppressWarnings("unchecked")
		final Class<T> recordClass = (Class<T>) typeToken.getRawType();
		return gson.getAdapter(recordClass);
	}

}
