package lsh.ext.gson;

import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnmodifiableIterable<T>
		implements Iterable<T> {

	private final Iterable<? extends T> iterable;

	@SuppressWarnings("UseOfConcreteClass")
	public static <T> Iterable<T> of(final Iterable<? extends T> iterable) {
		if ( iterable instanceof UnmodifiableIterable<? extends T> ) {
			@SuppressWarnings("unchecked")
			final Iterable<T> castIterable = (Iterable<T>) iterable;
			return castIterable;
		}
		return new UnmodifiableIterable<>(iterable);
	}

	@Override
	public Iterator<T> iterator() {
		return UnmodifiableIterator.of(iterable.iterator());
	}

}
