package json;

public class JsonNull extends JsonValue<Void>
{
    public JsonNull(Void value)
    {
        super(value);
    }

    @Override
    public String toString()
    {
        return "null";
    }
}
