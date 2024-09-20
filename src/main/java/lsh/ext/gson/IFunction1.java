package lsh.ext.gson;

public interface IFunction1<T, R> {

	R apply(T t);

	static <T> IFunction1<T, T> identity() {
		return t -> t;
	}

}
