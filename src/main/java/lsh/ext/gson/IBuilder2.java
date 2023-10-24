package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder2<A1, A2, T> {

	void modify(A1 a1, A2 a2);

	T build();

	interface IFactory<A1, A2, T> {

		IBuilder2<A1, A2, T> create(TypeToken<T> typeToken);

	}

}
