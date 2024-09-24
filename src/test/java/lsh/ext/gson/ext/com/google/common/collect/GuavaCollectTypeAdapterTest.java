package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class GuavaCollectTypeAdapterTest {

	private static final Multiset<String> multiset = ImmutableMultiset.<String>builder()
			.addCopies("foo", 1)
			.addCopies("bar", 2)
			.addCopies("baz", 3)
			.build();

	@Test
	public void testForMultisetIterator() {
		final Iterator<? extends Multiset.Entry<String>> iterator = multiset
				.entrySet()
				.iterator();
		final Iterator<String> unit = GuavaCollectTypeAdapter.forMultiset(iterator);
		Assertions.assertIterableEquals(List.of("foo", "bar", "bar", "baz", "baz", "baz"), (Iterable<String>) () -> unit);
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
	}

	@Test
	public void testForMultisetIteratorOfEmpty() {
		final Iterator<? extends Multiset.Entry<String>> iterator = ImmutableMultiset.<String>of()
				.entrySet()
				.iterator();
		final Iterator<String> unit = GuavaCollectTypeAdapter.forMultiset(iterator);
		Assertions.assertIterableEquals(List.of(), (Iterable<String>) () -> unit);
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
	}

}
