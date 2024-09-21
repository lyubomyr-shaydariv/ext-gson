package lsh.ext.gson;

import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;

public interface IBuilder0<R> {

	R build();

	interface ILookup<R> {

		Supplier<IBuilder0<R>> lookup(TypeToken<? super R> typeToken);

	}

}
