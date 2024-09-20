package lsh.ext.gson;

import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public interface IBuilder1<A1, R> {

	void accept(A1 a1);

	R build();

	static <E, C extends Collection<E>> IBuilder1<E, C> of(final C collection) {
		return new IBuilder1<>() {
			@Override
			public void accept(final E e) {
				collection.add(e);
			}

			@Override
			public C build() {
				return collection;
			}
		};
	}

	interface ILookup<A1, R> {

		IFactory<IBuilder1<A1, R>> lookup(TypeToken<? super R> typeToken);

	}

}
