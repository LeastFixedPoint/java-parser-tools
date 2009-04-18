package info.reflectionsofmind.parser.matcher.common;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;

public class IntegerMatcher
extends Matcher
{
	@Override
	public MatchResults match(String input, int start) 
	{
		int i= start;
		if (start < input.length())
		{
			char c= input.charAt(start);
			int f= (c == '+' || c == '-') ? 1 : 0;
			
			if (Character.isDigit(input.charAt(f)))
			{
				i+= f + 1;
				while (Character.isDigit(input.charAt(i)))
					i++;
			}
		}
		
		if (i == start)
			return new MatchResults("Expected an integer", start);
		
		
		StringNode node= new StringNode(start, i, input.substring(0, i));
		return new MatchResults(Arrays.<AbstractNode>asList(node));
	}
	
	@Override
	public String getLabel()
	{
		return "integer";
	}
}
