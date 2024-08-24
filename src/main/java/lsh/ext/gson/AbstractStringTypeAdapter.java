package lsh.ext.gson;

public abstract class AbstractStringTypeAdapter<T>
		extends AbstractCharSequenceTypeAdapter<T> {

	protected abstract T fromString(String text);

	protected abstract String toString(T value);

	@Override
	protected final T fromCharSequence(final CharSequence cs) {
		return fromString(cs.toString());
	}

	@Override
	protected final CharSequence toCharSequence(final T value) {
		return toString(value);
	}

}
