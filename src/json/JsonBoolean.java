package json;

public class JsonBoolean extends JsonValue<Boolean>
{
	public JsonBoolean(Boolean value)
	{
		super(value);
	}

	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
