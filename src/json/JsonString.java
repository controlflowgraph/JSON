package json;

public class JsonString extends JsonValue<String>
{
	public JsonString(String value)
	{
		super(value);
	}

	@Override
	public String toString()
	{
		String str = getValue()
				.replace("\\", "\\\\")
				.replace("\n", "\\n")
				.replace("\t", "\\t")
				.replace("\r", "\\r")
				.replace("\"", "\\\"");
		return "\"" + str + "\"";
	}
}