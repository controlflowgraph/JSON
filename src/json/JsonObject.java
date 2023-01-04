package json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonElement implements Iterable<Map.Entry<String, JsonElement>>
{
	private final HashMap<String, JsonElement> map = new HashMap<>();

	@Override
	public JsonElement get(Object key)
	{
		if(key.getClass().equals(String.class))
		{
			return this.map.get(key);
		}
		return null;
	}

	public void put(String key, JsonElement element)
	{
		this.map.put(key, element);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("{\n");
		Set<String> keys = this.map.keySet();
		String offset = "\t".repeat(JsonParser.indentations);
		JsonParser.indentations++;
		for(String key : keys)
			builder.append(offset).append("\t\"").append(key).append("\": ").append(this.map.get(key)).append(",\n");
		builder.append(offset).append("}");
		JsonParser.indentations--;
		return builder.toString().replaceFirst(",(\\n\\s*\\})$", "$1");
	}

	@Override
	public Iterator<Map.Entry<String, JsonElement>> iterator()
	{
		return this.map.entrySet().iterator();
	}
}
