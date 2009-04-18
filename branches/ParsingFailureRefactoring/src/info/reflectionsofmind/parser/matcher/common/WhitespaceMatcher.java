package info.reflectionsofmind.parser.matcher.common;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;

public class WhitespaceMatcher extends Matcher
{
	@Override
	public MatchResults match(String input, int start) 
	{
		int len= input.length();
		int i= start;
		while (i < len && Character.isWhitespace(input.charAt(i)))
			i++;
		if (i <= start)
			return new MatchResults("Expected whitespace", start);
		StringNode node= new StringNode(start, i, input.substring(start, i));
		return new MatchResults(Arrays.<AbstractNode>asList(node));
	}
	
	@Override
	public String getLabel()
	{
		return "whitespace";
	}
}
