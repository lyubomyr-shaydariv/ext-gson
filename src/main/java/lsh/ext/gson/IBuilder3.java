package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder3<A1, A2, A3, T> {

	void accept(A1 a1, A2 a2, A3 a3);

	T build();

	interface ILookup<A1, A2, A3, T> {

		IBuilder3<A1, A2, A3, T> lookup(TypeToken<? super T> typeToken);

	}

}
