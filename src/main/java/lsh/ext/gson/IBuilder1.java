package lsh.ext.gson;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

public interface IBuilder1<A1, R> {

	void accept(A1 a1);

	R build();

	static <A1, R> IBuilder1<A1, R> of(
			final Consumer<? super A1> consume,
			final Supplier<? extends R> build
	) {
		return new IBuilder1<>() {
			@Override
			public void accept(final A1 a1) {
				consume.accept(a1);
			}

			@Override
			public R build() {
				return build.get();
			}
		};
	}

	static <A1, R, CA> IBuilder1<A1, R> of(final Collector<? super A1, CA, ? extends R> collector) {
		return new IBuilder1<>() {
			private final Supplier<? extends CA> supplier = collector.supplier();
			private final BiConsumer<? super CA, ? super A1> accumulator = collector.accumulator();
			private final Function<? super CA, ? extends R> finisher = collector.finisher();

			private boolean isInitialized;
			@Nullable
			private CA ca;

			@Override
			public void accept(final A1 a1) {
				accumulator.accept(createOrGet(), a1);
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

	static <E, C extends Collection<E>> IBuilder1<E, C> fromCollection(final C collection) {
		return of(collection::add, () -> collection);
	}

	static <E, C extends Collection<E>> Supplier<IBuilder1<E, C>> fromCollection(final Supplier<? extends C> collectionFactory) {
		return () -> fromCollection(collectionFactory.get());
	}

	interface ILookup<A1, R> {

		Supplier<IBuilder1<A1, R>> lookup(TypeToken<? super R> typeToken);

	}

}
