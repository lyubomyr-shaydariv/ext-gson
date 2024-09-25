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

	static <K, V, M extends Map<K, V>> IBuilder2<K, V, M> of(final M map) {
		return of(map::put, () -> map);
	}

	static <K, V, M extends Map<K, V>> Supplier<IBuilder2<K, V, M>> from(final Supplier<? extends M> factory) {
		return () -> of(factory.get());
	}

	static <A1, A2, R, CT, CA> IBuilder2<A1, A2, R> from(
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
				if ( !isInitialized ) {
					ca = supplier.get();
					isInitialized = true;
				}
				final CT ct = toElementType.apply(a1, a2);
				accumulator.accept(ca, ct);
			}

			@Override
			public R build() {
				if ( !isInitialized ) {
					ca = supplier.get();
					isInitialized = true;
				}
				return finisher.apply(ca);
			}
		};
	}

	interface ILookup<A1, A2, R> {

		Supplier<IBuilder2<A1, A2, R>> lookup(TypeToken<? super R> typeToken);

	}

}
