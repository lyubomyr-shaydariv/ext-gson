package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

public interface IBuilder0<R> {

	R build();

	interface ILookup<R> {

		IFactory<IBuilder0<R>> lookup(TypeToken<? super R> typeToken);

	}

}
