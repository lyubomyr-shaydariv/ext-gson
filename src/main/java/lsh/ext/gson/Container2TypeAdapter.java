package lsh.ext.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Container2TypeAdapter<E1, E2, C, AUX>
		extends TypeAdapter<C> {

	private final TypeAdapter<E2> typeAdapter;
	private final Function<? super C, ? extends Iterator<? extends AUX>> toIterator;
	private final Function<? super AUX, ? extends E1> toE1;
	private final Function<? super AUX, ? extends E2> toE2;
	private final Function<? super E1, String> toName;
	private final Function<? super String, ? extends E1> fromName;
	private final Supplier<? extends IBuilder2<? super E1, ? super E2, ? extends C>> createBuilder;

	public static <E2, C, AUX> TypeAdapter<C> getInstance(
			final TypeAdapter<E2> typeAdapter,
			final Function<? super C, ? extends Iterator<? extends AUX>> toIterator,
			final Function<? super AUX, String> toE1,
			final Function<? super AUX, ? extends E2> toE2,
			final Supplier<? extends IBuilder2<? super String, ? super E2, ? extends C>> createBuilder
	) {
		return getInstance(typeAdapter, toIterator, toE1, toE2, Function.identity(), Function.identity(), createBuilder);
	}

	public static <E1, E2, C, AUX> TypeAdapter<C> getInstance(
			final TypeAdapter<E2> typeAdapter,
			final Function<? super C, ? extends Iterator<? extends AUX>> toIterator,
			final Function<? super AUX, ? extends E1> toE1,
			final Function<? super AUX, ? extends E2> toE2,
			final Function<? super E1, String> toName,
			final Function<? super String, ? extends E1> fromName,
			final Supplier<? extends IBuilder2<? super E1, ? super E2, ? extends C>> createBuilder
	) {
		return new Container2TypeAdapter<>(typeAdapter, toIterator, toE1, toE2, toName, fromName, createBuilder);
	}

	@Override
	public void write(final JsonWriter out, final C container)
			throws IOException {
		out.beginObject();
		final Iterator<? extends AUX> iterator = toIterator.apply(container);
		while ( iterator.hasNext() ) {
			final AUX element = iterator.next();
			final E1 e1 = toE1.apply(element);
			out.name(toName.apply(e1));
			final E2 e2 = toE2.apply(element);
			typeAdapter.write(out, e2);
		}
		out.endObject();
	}

	@Override
	public C read(final JsonReader in)
			throws IOException {
		in.beginObject();
		final IBuilder2<? super E1, ? super E2, ? extends C> builder = createBuilder.get();
		while ( in.hasNext() ) {
			final E1 e1 = fromName.apply(in.nextName());
			final E2 e2 = typeAdapter.read(in);
			builder.accept(e1, e2);
		}
		in.endObject();
		return builder.build();
	}

}
