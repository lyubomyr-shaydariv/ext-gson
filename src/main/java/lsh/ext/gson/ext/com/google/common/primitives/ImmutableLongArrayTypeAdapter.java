package lsh.ext.gson.ext.com.google.common.primitives;

import java.io.IOException;

import com.google.common.primitives.ImmutableLongArray;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImmutableLongArrayTypeAdapter
		extends TypeAdapter<ImmutableLongArray> {

	@Getter
	private static final TypeAdapter<ImmutableLongArray> instance = new ImmutableLongArrayTypeAdapter()
			.nullSafe();

	@Override
	public void write(final JsonWriter out, final ImmutableLongArray array)
			throws IOException {
		out.beginArray();
		final int length = array.length();
		for ( int i = 0; i < length; i++ ) {
			final long e = array.get(i);
			out.value(e);
		}
		out.endArray();
	}

	@Override
	public ImmutableLongArray read(final JsonReader in)
			throws IOException {
		final ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
		in.beginArray();
		while ( in.hasNext() ) {
			final long e = in.nextLong();
			builder.add(e);
		}
		in.endArray();
		return builder.build();
	}

}
