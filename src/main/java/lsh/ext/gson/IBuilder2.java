package lsh.ext.gson;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

public interface IBuilder2<A1, A2, R> {

	void accept(A1 a1, A2 a2);

	R build();

	static <A1, A2, R> IBuilder2<A1, A2, R> of(
			final BiConsumer<? super A1, ? super A2> consume,
			final Supplier<? extends R> build
	) {
		return new IBuilder2<>() {
			@Override
			public void accept(final A1 a1, final A2 a2) {
				consume.accept(a1, a2);
			}

			@Override
			public R build() {
				return build.get();
			}
		};
	}

	static <A1, A2, R, AUX> IBuilder2<A1, A2, R> of(
			final Supplier<? extends AUX> createAux,
			final TriConsumer<? super AUX, ? super A1, ? super A2> consume,
			final Function<? super AUX, ? extends R> build
	) {
		return new IBuilder2<>() {
			private boolean isInitialized;
			@Nullable
			private AUX aux;

			@Override
			public void accept(final A1 a1, final A2 a2) {
				consume.accept(createOrGet(), a1, a2);
			}

			@Override
			public R build() {
				return build.apply(createOrGet());
			}

			private AUX createOrGet() {
				if ( !isInitialized ) {
					aux = createAux.get();
					isInitialized = true;
				}
				return aux;
			}
		};
	}

	static <A1, A2, R> IBuilder2<A1, A2, R> of(
			final Supplier<? extends R> createResult,
			final TriConsumer<? super R, ? super A1, ? super A2> consume
	) {
		return of(createResult, consume, Function.identity());
	}

	static <A1, A2, R, CT, CA> IBuilder2<A1, A2, R> of(
			final Collector<? super CT, CA, ? extends R> collector,
			final BiFunction<? super A1, ? super A2, ? extends CT> toElementType
	) {
		return new IBuilder2<>() {
			private final Supplier<CA> supplier = collector.supplier();
			private final BiConsumer<CA, ? super CT> accumulator = collector.accumulator();
			private final Function<CA, ? extends R> finisher = collector.finisher();

			private boolean isInitialized;
			@Nullable
			private CA ca;

			@Override
			public void accept(final A1 a1, final A2 a2) {
				final CT ct = toElementType.apply(a1, a2);
				accumulator.accept(createOrGet(), ct);
			}

			@Override
			public R build() {
				return finisher.apply(createOrGet());
			}

			private CA createOrGet() {
				if ( !isInitialized ) {
					ca = supplier.get();
					isInitialized = true;
				}
				return ca;
			}
		};
	}

	static <K, V, M extends Map<K, V>> IBuilder2<K, V, M> fromMap(final M map) {
		return of(map::put, () -> map);
	}

	static <K, V, M extends Map<K, V>> Supplier<IBuilder2<K, V, M>> fromMap(final Supplier<? extends M> factory) {
		return () -> fromMap(factory.get());
	}

	interface ILookup<A1, A2, R> {

		Supplier<IBuilder2<A1, A2, R>> lookup(TypeToken<? super R> typeToken);

	}

}
