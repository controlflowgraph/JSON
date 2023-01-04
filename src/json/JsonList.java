package json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonList extends JsonElement implements Iterable<JsonElement>
{
	private final List<JsonElement> elements = new ArrayList<>();

	public void add(JsonElement element)
	{
		this.elements.add(element);
	}

	@Override
	public JsonElement get(Object key)
	{
		if(key.getClass().equals(Integer.class))
		{
			int index = (Integer) key;
			if(0 <= index && index < this.elements.size())
			{
				return this.elements.get(index);
			}
		}
		return null;
	}

	public int size()
	{
		return this.elements.size();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder("[\n");
		String offset = "\t".repeat(JsonParser.indentations);
		JsonParser.indentations++;
		for(JsonElement element : this.elements)
			builder.append(offset).append("\t").append(element).append(",\n");
		builder.append(offset).append("]");
		JsonParser.indentations--;
		return builder.toString().replaceFirst(",(\\n\\s*\\])$", "$1");
	}

	@Override
	public Iterator<JsonElement> iterator()
	{
		return this.elements.iterator();
	}
}
