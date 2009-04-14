package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WhitespaceMatcher implements Matcher
{
	@Override
	public List<Result> match(String input)
	{
		int len= input.length();
		int i= 0;
		while (i < len && Character.isWhitespace(input.charAt(i)))
			i++;
		if (i <= 0)
			return Collections.<Result> emptyList();
		return Arrays.asList(new Result(new StringNode(input.substring(0, i)), i));
	}
}
