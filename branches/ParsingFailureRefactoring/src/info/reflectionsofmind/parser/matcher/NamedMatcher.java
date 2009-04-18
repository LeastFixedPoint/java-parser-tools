package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.NamedNode;

import java.util.ArrayList;
import java.util.List;

public class NamedMatcher extends Matcher
{
	public final String id;
	private Matcher definition;

	public NamedMatcher(String id)
	{
		this.id = id;
	}
	
	public NamedMatcher define(Matcher definition)
	{
		this.definition = definition;
		return this;
	}
	
	@Override
	public MatchResults match(String input, int start) 
	{
		List<AbstractNode> results = new ArrayList<AbstractNode>();

		MatchResults matchResults= this.definition.match(input, start);
		if (!matchResults.success())
			return matchResults;

		for (AbstractNode result : matchResults.matches)
		{
			final AbstractNode node = new NamedNode(this.id, start, result.end);
			node.children.add(result);
			results.add(node);
		}
		return new MatchResults(results);
	}
	
	@Override
	public String getLabel()
	{
		return id;
	}
}
