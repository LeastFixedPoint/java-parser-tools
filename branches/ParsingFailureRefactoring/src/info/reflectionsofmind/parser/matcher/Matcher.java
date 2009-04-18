package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;

public abstract class Matcher
{
	abstract public MatchResults match(String input, int start);
	
	public MatchResults match(String input) 
	{
		return match(input, 0);
	}
	
	abstract public String getLabel();
}