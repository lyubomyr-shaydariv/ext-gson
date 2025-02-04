package lsh.ext.gson;

@FunctionalInterface
public interface ITriConsumer<T, U, V> {

	void accept(T t, U u, V v);

}
