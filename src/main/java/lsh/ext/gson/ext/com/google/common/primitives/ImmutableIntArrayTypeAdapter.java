package lsh.ext.gson.ext.com.google.common.primitives;

import java.io.IOException;

import com.google.common.primitives.ImmutableIntArray;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassHierarchyTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImmutableIntArrayTypeAdapter
		extends TypeAdapter<ImmutableIntArray> {

	@Getter
	private static final TypeAdapter<ImmutableIntArray> instance = new ImmutableIntArrayTypeAdapter()
			.nullSafe();

	@Override
	public void write(final JsonWriter out, final ImmutableIntArray array)
			throws IOException {
		out.beginArray();
		final int length = array.length();
		for ( int i = 0; i < length; i++ ) {
			final int e = array.get(i);
			out.value(e);
		}
		out.endArray();
	}

	@Override
	public ImmutableIntArray read(final JsonReader in)
			throws IOException {
		final ImmutableIntArray.Builder builder = ImmutableIntArray.builder();
		in.beginArray();
		while ( in.hasNext() ) {
			final int e = in.nextInt();
			builder.add(e);
		}
		in.endArray();
		return builder.build();
	}

	public static final class Factory
			extends AbstractRawClassHierarchyTypeAdapterFactory<ImmutableIntArray> {

		@Getter
		private static final ITypeAdapterFactory<ImmutableIntArray> instance = new Factory();

		private Factory() {
			super(ImmutableIntArray.class);
		}

		@Override
		protected TypeAdapter<ImmutableIntArray> createTypeAdapter(final Gson gson, final TypeToken<? super ImmutableIntArray> typeToken) {
			return ImmutableIntArrayTypeAdapter.instance;
		}

	}

}
