package lsh.ext.gson;

import java.io.IOException;
import java.io.Reader;
import javax.annotation.Nonnull;
import javax.annotation.WillCloseWhenClosed;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

@SuppressWarnings({ "AbstractClassExtendsConcreteClass", "AbstractClassWithOnlyOneDirectInheritor", "ClassWithoutNoArgConstructor" })
public abstract class AbstractDelegateJsonReader
		extends JsonReader {

	private final JsonReader in;

	@SuppressWarnings("WeakerAccess")
	protected AbstractDelegateJsonReader(@WillCloseWhenClosed final JsonReader in) {
		super(neverReader);
		this.in = in;
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void beginArray()
			throws IOException {
		in.beginArray();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void endArray()
			throws IOException {
		in.endArray();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void beginObject()
			throws IOException {
		in.beginObject();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void endObject()
			throws IOException {
		in.endObject();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public boolean hasNext()
			throws IOException {
		return in.hasNext();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public JsonToken peek()
			throws IOException {
		return in.peek();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public String nextName()
			throws IOException {
		return in.nextName();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public String nextString()
			throws IOException {
		return in.nextString();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public boolean nextBoolean()
			throws IOException {
		return in.nextBoolean();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void nextNull()
			throws IOException {
		in.nextNull();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public double nextDouble()
			throws IOException {
		return in.nextDouble();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public long nextLong()
			throws IOException {
		return in.nextLong();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public int nextInt()
			throws IOException {
		return in.nextInt();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod" })
	public void close()
			throws IOException {
		in.close();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public void skipValue()
			throws IOException {
		in.skipValue();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod" })
	public String toString() {
		return in.toString();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public String getPath() {
		return in.getPath();
	}

	@Override
	@SuppressWarnings({ "DesignForExtension", "MethodDoesntCallSuperMethod", "PublicMethodNotExposedInInterface" })
	public String getPreviousPath() {
		return in.getPreviousPath();
	}

	private static final Reader neverReader = new Reader() {
		// @formatter:off
		@Override public int read(@Nonnull final char[] buffer, final int offset, final int length) { throw new AssertionError(); }
		@Override public void close() { throw new AssertionError(); }
		// @formatter:on
	};

}
