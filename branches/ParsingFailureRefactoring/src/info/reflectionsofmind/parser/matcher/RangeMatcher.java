/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;

public final class RangeMatcher extends Matcher
{
	private final char to;
	private final char from;

	public RangeMatcher(char to, char from)
	{
		this.to = to;
		this.from = from;
	}

	@Override
	public MatchResults match(final String input, int start) 
	{
		if (start < input.length()) 
		{
			char c= input.charAt(start);
			for (char ch = from; ch < to; ch++)
			{
				if (c == ch) 
					return new MatchResults(Arrays.<AbstractNode>asList(new StringNode(start, start+1, "" + ch)));
			}
		}
		
		return new MatchResults("Expected : "+getLabel(), start);
	}
	
	@Override
	public String getLabel()
	{
		return "character in range ["+to+"-"+from+"]";
	}
}