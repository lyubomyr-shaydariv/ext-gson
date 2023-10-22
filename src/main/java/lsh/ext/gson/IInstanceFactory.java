package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IInstanceFactory<T> {

	T createInstance();

	interface IProvider<T> {

		IInstanceFactory<T> provide(TypeToken<T> typeToken);

	}

}
