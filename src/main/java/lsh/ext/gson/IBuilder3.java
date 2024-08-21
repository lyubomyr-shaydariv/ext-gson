package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder3<A1, A2, A3, T> {

	void accept(A1 a1, A2 a2, A3 a3);

	T build();

	interface IFactory<A1, A2, A3, T> {

		IBuilder3<A1, A2, A3, T> create(TypeToken<? super T> typeToken);

		static <A1, A2, A3, T> IFactory<A1, A2, A3, T> unsupported() {
			return typeToken -> {
				throw new UnsupportedOperationException(typeToken.toString());
			};
		}

	}

}
