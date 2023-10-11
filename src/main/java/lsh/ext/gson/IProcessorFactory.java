package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

public interface IProcessorFactory<T> {

	@Nullable
	IProcessor<T> createProcessor(TypeToken<?> typeToken);

}
