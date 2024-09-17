package lsh.ext.gson.ext.com.google.common.primitives;

import java.io.IOException;

import com.google.common.primitives.ImmutableDoubleArray;
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
public final class ImmutableDoubleArrayTypeAdapter
		extends TypeAdapter<ImmutableDoubleArray> {

	@Getter
	private static final TypeAdapter<ImmutableDoubleArray> instance = new ImmutableDoubleArrayTypeAdapter()
			.nullSafe();

	@Override
	public void write(final JsonWriter out, final ImmutableDoubleArray array)
			throws IOException {
		out.beginArray();
		final int length = array.length();
		for ( int i = 0; i < length; i++ ) {
			final double e = array.get(i);
			out.value(e);
		}
		out.endArray();
	}

	@Override
	public ImmutableDoubleArray read(final JsonReader in)
			throws IOException {
		final ImmutableDoubleArray.Builder builder = ImmutableDoubleArray.builder();
		in.beginArray();
		while ( in.hasNext() ) {
			final double e = in.nextDouble();
			builder.add(e);
		}
		in.endArray();
		return builder.build();
	}

	public static final class Factory
			extends AbstractRawClassHierarchyTypeAdapterFactory<ImmutableDoubleArray> {

		@Getter
		private static final ITypeAdapterFactory<ImmutableDoubleArray> instance = new Factory();

		private Factory() {
			super(ImmutableDoubleArray.class);
		}

		@Override
		protected TypeAdapter<ImmutableDoubleArray> createTypeAdapter(final Gson gson, final TypeToken<? super ImmutableDoubleArray> typeToken) {
			return ImmutableDoubleArrayTypeAdapter.instance;
		}

	}

}
