package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder0<T> {

	T build();

	interface ILookup<T> {

		IBuilder0<T> lookup(TypeToken<? super T> typeToken);

	}

}
