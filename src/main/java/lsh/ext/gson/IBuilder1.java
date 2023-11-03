package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder1<A1, T> {

	void accept(A1 a1);

	T build();

	interface IFactory<A1, T> {

		IBuilder1<A1, T> create(TypeToken<T> typeToken);

	}

}
