package lsh.ext.gson;

public interface IFunction<T, R> {

	R apply(T t);

	static <T> IFunction<T, T> identity() {
		return t -> t;
	}

}
