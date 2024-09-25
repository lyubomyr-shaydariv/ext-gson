package lsh.ext.gson;

import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;

public interface IBuilder3<A1, A2, A3, R> {

	void accept(A1 a1, A2 a2, A3 a3);

	R build();

	static <A1, A2, A3, R> IBuilder3<A1, A2, A3, R> of(
			final TriConsumer<? super A1, ? super A2, ? super A3> consume,
			final Supplier<? extends R> build
	) {
		return new IBuilder3<>() {
			@Override
			public void accept(final A1 a1, final A2 a2, final A3 a3) {
				consume.accept(a1, a2, a3);
			}

			@Override
			public R build() {
				return build.get();
			}
		};
	}

	interface ILookup<A1, A2, A3, R> {

		Supplier<IBuilder3<A1, A2, A3, R>> lookup(TypeToken<? super R> typeToken);

	}

}
