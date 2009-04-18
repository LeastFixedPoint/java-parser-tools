/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;

public final class StringMatcher extends Matcher
{
	private final String string;

	public StringMatcher(String string)
	{
		this.string = string;
	}

	@Override
	public MatchResults match(final String input, int start) 
	{
		int i= 0;
		int l= string.length();
		int e= input.length();
		while (i < l && (start+i) < e && input.charAt(start + i) == string.charAt(i))
			i++;
		if (i == l)
			return new MatchResults(Arrays.<AbstractNode>asList(new StringNode(start, start + l, string)));
		return new MatchResults("Expected '"+string+"'", start + i);
	}
	
	@Override
	public String getLabel()
	{
		return "'"+string+"'";
	}
}