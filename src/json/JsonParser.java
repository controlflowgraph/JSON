package json;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonParser
{
	public static JsonElement parse(Path path, Charset charset)
	{
		try
		{
			return parse(Files.readString(path, charset));
		}
		catch (IOException e){e.printStackTrace();}
		return null;
	}

	public static JsonElement parse(File source)
	{
		return parse(source.toPath());
	}

	public static JsonElement parse(Path path)
	{
		return parse(path, StandardCharsets.UTF_8);
	}

	public static JsonElement parse(String text)
	{
		return new JsonParser(text).parse();
	}

	private int index;
	private final char[] characters;

	public static int indentations = 0;

	private JsonParser(String content)
	{
		this.characters = content.toCharArray();
	}

	public JsonElement parse()
	{
		this.index = 0;
		return parseValue();
	}

	private JsonElement parseObject()
	{
		JsonObject object = new JsonObject();
		boolean running = true;
		String key = null;
		while(this.index < this.characters.length && running)
		{
			char c = this.characters[this.index++];
			if(c != ' ' && c != '\t' && c != '\n' && c != '\r')
			{
				if(c == '}')
				{
					running = false;
				}
				else if(key != null)
				{
					object.put(key, parseValue());
					key = null;
				}
				else if(c == '"')
				{
					key = parseString();
				}
			}
		}
		return object;
	}

	private JsonElement parseList()
	{
		JsonList list = new JsonList();
		boolean running = true;
		while(this.index < this.characters.length && running)
		{
			char c = this.characters[this.index++];
			if(c != ' ' && c != '\n' && c != '\t' && c != '\r')
			{
				if(c == ']')
				{
					running = false;
				}
				else
				{
					this.index--;
					list.add(parseValue());
				}
			}
		}
		return list;
	}

	private JsonElement parseValue()
	{
		JsonElement value = null;
		while(this.index < this.characters.length && value == null)
		{
			char c = this.characters[this.index++];
			switch (c)
			{
				case ' ', '\r', '\t', '\n', ',' -> {}
				case '"' -> value = new JsonString(parseString());
				case 't', 'f' -> value = new JsonBoolean(parseBoolean());
				case '{' -> value = parseObject();
				case '[' -> value = parseList();
				case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+' -> value = new JsonNumber(parseNumber());
				case 'n' -> value = new JsonNull(parseNull());
				default -> {
					StringBuilder e = new StringBuilder();
					for(int i = this.index - 30; i < this.index + 1000; i++)
						e.append(this.characters[i]);
					throw new RuntimeException("Unknown character (" + this.characters[this.index - 1] + " -> " + ((int) this.characters[this.index - 1]) + ") " + e);
				}
			}
		}
		return value;
	}

	private Void parseNull()
	{
		boolean u = this.characters[this.index++] != 'u';
		boolean l1 = this.characters[this.index++] != 'l';
		boolean l2 = this.characters[this.index++] != 'l';
		if(u || l1 || l2) throw new RuntimeException("Unknown character near null!");
		return null;
	}

	private String parseString()
	{
		StringBuilder builder = new StringBuilder();
		boolean running = true;
		while(this.index < this.characters.length && running)
		{
			char c = this.characters[this.index++];
			if(c == '"')
			{
				running = false;
			}
			else if(c == '\\')
			{
				char esc = this.characters[this.index++];
				switch (esc)
				{
					case 'n' -> builder.append('\n');
					case 't' -> builder.append('\t');
					case 'r' -> builder.append('\r');
					case 'u' -> {
						for (int i = 0; i < 4; i++)
						{
							this.index++;
						}
					}
					case 'U' -> {
						for (int i = 0; i < 8; i++)
						{
							this.index++;
						}
					}
					case '\\' -> builder.append('\\');
					case '"' -> builder.append('"');
					default -> {} //throw new RuntimeException("Unknown escape sequence! " + esc);
				}
			}
			else
			{
				builder.append(c);
			}
		}
		return builder.toString();
	}

	private Double parseNumber()
	{
		this.index--;
		StringBuilder builder = new StringBuilder();
		boolean running = true;
		boolean decimal = false;
		boolean negative = false;
		if(this.characters[this.index] == '-')
		{
			negative = true;
			this.index++;
		}
		else if(this.characters[this.index] == '+')
		{
			this.index++;
		}
		while(this.index < this.characters.length && running)
		{
			char c = this.characters[this.index++];
			if('0' <= c && c <= '9' || (c == '.' && !decimal))
			{
				builder.append(c);
				decimal = true;
			}
			else
			{
				running = false;
			}
		}
		this.index--;
		return Double.parseDouble(builder.toString()) * (negative ? -1 : 1);
	}

	private Boolean parseBoolean()
	{
		this.index--;
		if(this.characters[this.index] == 't')
		{
			if(this.index + 3 < this.characters.length)
			{
				if(this.characters[this.index + 1] == 'r' && this.characters[this.index + 2] == 'u' && this.characters[this.index + 3] == 'e')
				{
					return true;
				}
			}
		}
		else if(this.characters[this.index] == 'f')
		{
			if(this.index + 4 < this.characters.length)
			{
				if(this.characters[this.index + 1] == 'a' && this.characters[this.index + 2] == 'l' && this.characters[this.index + 3] == 's' && this.characters[this.index + 4] == 'e')
				{
					return false;
				}
			}
		}

		return null;
	}
}
