package lsh.ext.gson;

import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class UnmodifiableIterator<T>
		implements Iterator<T> {

	private final Iterator<? extends T> iterator;

	static <T> Iterator<T> of(final Iterator<? extends T> iterator) {
		return new UnmodifiableIterator<>(iterator);
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		return iterator.next();
	}

}
