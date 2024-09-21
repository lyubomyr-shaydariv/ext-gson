package lsh.ext.gson;

import java.util.Map;
import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;

public interface IBuilder2<A1, A2, R> {

	void accept(A1 a1, A2 a2);

	R build();

	static <K, V, M extends Map<K, V>> IBuilder2<K, V, M> of(final M map) {
		return new IBuilder2<>() {
			@Override
			public void accept(final K k, final V v) {
				map.put(k, v);
			}

			@Override
			public M build() {
				return map;
			}
		};
	}

	interface ILookup<A1, A2, R> {

		Supplier<IBuilder2<A1, A2, R>> lookup(TypeToken<? super R> typeToken);

	}

}
