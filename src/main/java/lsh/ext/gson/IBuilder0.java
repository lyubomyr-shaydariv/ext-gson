package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder0<T> {

	T build();

	interface IFactory<T> {

		IBuilder0<T> create(TypeToken<T> typeToken);

	}

}
