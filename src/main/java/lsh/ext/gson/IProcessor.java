package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

public interface IProcessor<T> {

	void process(T input);

	interface IFactory<T> {

		@Nullable
		IProcessor<T> createProcessor(TypeToken<?> typeToken);

	}

}
