package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder3<A1, A2, A3, R> {

	void accept(A1 a1, A2 a2, A3 a3);

	R build();

	interface ILookup<A1, A2, A3, R> {

		IFactory<IBuilder3<A1, A2, A3, R>> lookup(TypeToken<? super R> typeToken);

	}

}
