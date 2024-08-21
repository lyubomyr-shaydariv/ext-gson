package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder0<T> {

	T build();

	interface IFactory<T> {

		IBuilder0<T> create(TypeToken<? super T> typeToken);

		static <T> IFactory<T> unsupported() {
			return typeToken -> {
				throw new UnsupportedOperationException(typeToken.toString());
			};
		}

	}

}
