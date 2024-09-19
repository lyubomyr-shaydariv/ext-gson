package lsh.ext.gson;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractJsonObjectTypeAdapter<O, K, V>
		extends TypeAdapter<O> {

	private final TypeAdapter<V> valueTypeAdapter;
	private final Function<? super K, String> encodeKey;
	private final Function<? super String, ? extends K> decodeKey;

	protected abstract Iterable<? extends Map.Entry<K, V>> toIterable(O object);

	protected abstract IBuilder2<? super K, ? super V, ? extends O> createBuilder();

	@Override
	public final void write(final JsonWriter out, final O object)
			throws IOException {
		out.beginObject();
		for ( final Map.Entry<K, V> e : toIterable(object) ) {
			out.name(encodeKey.apply(e.getKey()));
			valueTypeAdapter.write(out, e.getValue());
		}
		out.endObject();
	}

	@Override
	public final O read(final JsonReader in)
			throws IOException {
		final IBuilder2<? super K, ? super V, ? extends O> builder = createBuilder();
		in.beginObject();
		while ( in.hasNext() ) {
			final String name = in.nextName();
			final V v = valueTypeAdapter.read(in);
			builder.accept(decodeKey.apply(name), v);
		}
		in.endObject();
		return builder.build();
	}

}
