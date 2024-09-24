package lsh.ext.gson;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

public interface IBuilder1<A1, R> {

	void accept(A1 a1);

	R build();

	static <E, C extends Collection<E>> IBuilder1<E, C> of(final C collection) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				collection.add(e);
			}

			@Override
			public C build() {
				return collection;
			}
		};
	}

	static <E, C extends Collection<E>> Supplier<IBuilder1<E, C>> from(final Supplier<? extends C> collectionFactory) {
		return () -> of(collectionFactory.get());
	}

	static <A1, R, CA> IBuilder1<A1, R> from(final Collector<? super A1, CA, ? extends R> collector) {
		return new IBuilder1<>() {
			private final Supplier<? extends CA> supplier = collector.supplier();
			private final BiConsumer<? super CA, ? super A1> accumulator = collector.accumulator();
			private final Function<? super CA, ? extends R> finisher = collector.finisher();

			private boolean isInitialized;
			@Nullable
			private CA ca;

			@Override
			public void accept(final A1 a1) {
				if ( !isInitialized ) {
					ca = supplier.get();
					isInitialized = true;
				}
				accumulator.accept(ca, a1);
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

	interface ILookup<A1, R> {

		Supplier<IBuilder1<A1, R>> lookup(TypeToken<? super R> typeToken);

	}

}
