package lsh.ext.gson.internal;

import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class UnmodifiableIterator<T>
		implements Iterator<T> {

	private final Iterator<? extends T> iterator;

	@SuppressWarnings("UseOfConcreteClass")
	static <T> Iterator<T> of(final Iterator<? extends T> iterator) {
		if ( iterator instanceof UnmodifiableIterator<? extends T> ) {
			@SuppressWarnings("unchecked")
			final Iterator<T> castIterator = (Iterator<T>) iterator;
			return castIterator;
		}
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
