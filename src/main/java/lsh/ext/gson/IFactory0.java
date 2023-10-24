package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IFactory0<T> {

	T create();

	interface IFactory<T> {

		IFactory0<T> create(TypeToken<T> typeToken);

	}

}
