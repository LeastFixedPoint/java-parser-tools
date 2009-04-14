package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuotedStringMatcher
implements Matcher
{
	@Override
	public List<Result> match(String input)
	{
		if (input.charAt(0) != '"' )
			return Collections.<Result> emptyList();
		
		int pos = input.indexOf('"', 1);
		while (pos > 0 && input.charAt(pos - 1) == '\'')
			pos = input.indexOf('"', pos+1);

		if (pos == -1 || input.charAt(pos) != '"') 
			return Collections.<Result> emptyList();
		pos++;

		String text= input.substring(0, pos).replaceAll("\'\"", "\"");
		return Arrays.asList(new Result(new StringNode(text), pos));
	}

}
