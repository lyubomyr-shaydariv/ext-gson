package lsh.ext.gson;

import java.util.Collection;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionBuilder<E, C extends Collection<E>>
		implements IBuilder1<E, C> {

	private final C collection;

	public static <E, C extends Collection<E>> IBuilder1<E, C> getInstance(final C collection) {
		return new CollectionBuilder<>(collection);
	}

	@Override
	public void modify(final E e) {
		collection.add(e);
	}

	@Override
	public C build() {
		return collection;
	}

}
