package json;

public class JsonNumber extends JsonValue<Double>
{
	public JsonNumber(Double value)
	{
		super(value);
	}

	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
