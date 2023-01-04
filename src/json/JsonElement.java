package json;

public abstract class JsonElement
{
	public abstract JsonElement get(Object key);

	public JsonObject getObject(Object key)
	{
		return get(key) instanceof JsonObject obj ? obj : null;
	}

	public JsonList getList(Object key)
	{
		return get(key) instanceof JsonList obj ? obj : null;
	}

	public JsonString getString(Object key)
	{
		return get(key) instanceof JsonString obj ? obj : null;
	}

	public JsonBoolean getBoolean(Object key)
	{
		return get(key) instanceof JsonBoolean obj ? obj : null;
	}

	public JsonNumber getNumber(Object key)
	{
		return get(key) instanceof JsonNumber obj ? obj : null;
	}

	public JsonObject asObject()
	{
		return this instanceof JsonObject obj ? obj : null;
	}

	public JsonList asList()
	{
		return this instanceof JsonList obj ? obj : null;
	}

	public JsonString asString()
	{
		return this instanceof JsonString obj ? obj : null;
	}

	public JsonBoolean asBoolean()
	{
		return this instanceof JsonBoolean obj ? obj : null;
	}

	public JsonNumber asNumber()
	{
		return this instanceof JsonNumber obj ? obj : null;
	}

}
