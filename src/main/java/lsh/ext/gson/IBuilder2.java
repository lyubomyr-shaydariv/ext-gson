package lsh.ext.gson;

import java.util.Map;

import com.google.gson.reflect.TypeToken;

public interface IBuilder2<A1, A2, T> {

	void accept(A1 a1, A2 a2);

	T build();

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

	interface IFactory<A1, A2, T> {

		IBuilder2<A1, A2, T> create(TypeToken<? super T> typeToken);

	}

}
