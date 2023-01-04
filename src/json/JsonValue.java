package json;

public class JsonValue<T> extends JsonElement
{
	private final T value;

	public JsonValue(T value)
	{
		this.value = value;
	}

	public T getValue()
	{
		return this.value;
	}

	@Override
	public JsonElement get(Object key)
	{
		return null;
	}

	@Override
	public String toString()
	{
		return this.value.toString();
	}
}
