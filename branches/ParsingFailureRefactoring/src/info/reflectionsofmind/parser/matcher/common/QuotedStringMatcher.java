package info.reflectionsofmind.parser.matcher.common;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;

public class QuotedStringMatcher
extends Matcher
{
	@Override
	public MatchResults match(String input, int start) 
	{
		if (input.length() <= start || input.charAt(start) != '"' )
			return new MatchResults("Excepted beginning of quote", start);
		
		int pos = input.indexOf('"', start+1);
		while (pos > 0 && input.charAt(pos - 1) == '\'')
			pos = input.indexOf('"', pos+1);

		if (pos == -1 || input.charAt(pos) != '"') 
			return new MatchResults("Excepted end of quote", input.length());
		pos++;

		String text= input.substring(start, pos).replaceAll("\'\"", "\"");
		StringNode node= new StringNode(start, pos, text);
		return new MatchResults(Arrays.<AbstractNode>asList(node));
	}
	
	@Override
	public String getLabel()
	{
		return "quoted string";
	}

}
