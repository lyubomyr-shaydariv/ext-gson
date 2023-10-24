package lsh.ext.gson;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface IProcessor<T> {

	@SuppressWarnings("checkstyle:MissingJavadocMethod")
	void process(T input);

}
