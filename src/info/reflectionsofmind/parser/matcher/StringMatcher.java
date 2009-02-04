/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class StringMatcher implements Matcher
{
	private final String string;

	public StringMatcher(String string)
	{
		this.string = string;
	}

	@Override
	public List<Result> match(final String input)
	{
		return input.startsWith(string) ? Arrays.asList(new Result(new StringNode(string), string.length())) : Collections.<Result> emptyList();
	}
}