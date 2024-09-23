package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.IBuilder2;
import org.apache.commons.collections4.Bag;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BagTypeAdapter<E>
		extends TypeAdapter<Bag<E>> {

	private final Supplier<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory;
	private final Function<? super E, String> toKey;
	private final Function<? super String, ? extends E> fromKey;

	public static <E> TypeAdapter<Bag<E>> getInstance(
			final Supplier<? extends IBuilder2<? super E, ? super Integer, ? extends Bag<E>>> builderFactory,
			final Function<? super E, String> toKey,
			final Function<? super String, ? extends E> fromKey
	) {
		return new BagTypeAdapter<>(builderFactory, toKey, fromKey)
				.nullSafe();
	}

	@Override
	public void write(final JsonWriter out, final Bag<E> bag)
			throws IOException {
		out.beginObject();
		for ( final E e : bag ) {
			out.name(toKey.apply(e));
			out.value(bag.getCount(e));
		}
		out.endObject();
	}

	@Override
	public Bag<E> read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super E, ? super Integer, ? extends Bag<E>> builder = builderFactory.get();
		while ( in.hasNext() ) {
			builder.accept(fromKey.apply(in.nextName()), in.nextInt());
		}
		in.endObject();
		return builder.build();
	}

}
