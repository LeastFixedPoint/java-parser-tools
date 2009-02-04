/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class RangeMatcher implements Matcher
{
	private final char to;
	private final char from;

	public RangeMatcher(char to, char from)
	{
		this.to = to;
		this.from = from;
	}

	@Override
	public List<Result> match(final String input)
	{
		if (input.isEmpty()) return Collections.<Result> emptyList();

		for (char ch = from; ch < to; ch++)
		{
			if (input.charAt(0) == ch) return Arrays.asList(new Result(new StringNode("" + ch), 1));
		}

		return Collections.<Result> emptyList();
	}
}