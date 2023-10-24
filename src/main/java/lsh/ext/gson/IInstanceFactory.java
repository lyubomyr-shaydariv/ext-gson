package lsh.ext.gson;

import com.google.gson.reflect.TypeToken;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface IInstanceFactory<T> {

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	T createInstance();

	@SuppressWarnings("checkstyle:MissingJavadocType")
	interface IProvider<T> {

		@SuppressWarnings("checkstyle:MissingJavadocMethod")
		IInstanceFactory<T> provide(TypeToken<T> typeToken);

	}

}
