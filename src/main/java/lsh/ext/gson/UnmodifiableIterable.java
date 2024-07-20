package lsh.ext.gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnmodifiableIterable<T>
		implements Iterable<T> {

	private static final Iterable<?> emptyUnmodifiableIterable = new UnmodifiableIterable<>(Collections.emptyList());

	private final Iterable<? extends T> iterable;

	public static <T> Iterable<T> of(final Iterable<? extends T> iterable) {
		if ( iterable instanceof UnmodifiableIterable<? extends T> ) {
			@SuppressWarnings("unchecked")
			final Iterable<T> castIterable = (Iterable<T>) iterable;
			return castIterable;
		}
		return new UnmodifiableIterable<>(iterable);
	}

	public static <T> Iterable<T> copyOf(final Collection<? extends T> collection) {
		if ( collection.isEmpty() ) {
			@SuppressWarnings("unchecked")
			final Iterable<T> castEmptyUnmodifiableIterable = (Iterable<T>) emptyUnmodifiableIterable;
			return castEmptyUnmodifiableIterable;
		}
		final ArrayList<T> copy = new ArrayList<>(collection);
		return new UnmodifiableIterable<>(copy);
	}

	public static <T> Iterable<T> copyOf(final Iterable<? extends T> iterable) {
		if ( iterable instanceof final Collection<? extends T> collection ) {
			return copyOf(collection);
		}
		final Collection<T> copy = new ArrayList<>();
		for ( final T t : iterable ) {
			copy.add(t);
		}
		if ( copy.isEmpty() ) {
			@SuppressWarnings("unchecked")
			final Iterable<T> castEmptyUnmodifiableIterable = (Iterable<T>) emptyUnmodifiableIterable;
			return castEmptyUnmodifiableIterable;
		}
		return new UnmodifiableIterable<>(copy);
	}

	@SafeVarargs
	public static <T> Iterable<T> copyOf(final T... array) {
		if ( array.length == 0 ) {
			@SuppressWarnings("unchecked")
			final Iterable<T> castEmptyUnmodifiableIterable = (Iterable<T>) emptyUnmodifiableIterable;
			return castEmptyUnmodifiableIterable;
		}
		final Collection<T> copy = Arrays.asList(array);
		return new UnmodifiableIterable<>(copy);
	}

	@Override
	public Iterator<T> iterator() {
		return UnmodifiableIterator.of(iterable.iterator());
	}

}
