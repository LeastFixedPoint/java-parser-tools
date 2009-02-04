package info.reflectionsofmind.parser.matcher;

import java.util.ArrayList;
import java.util.List;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.NamedNode;

public class NamedMatcher implements Matcher
{
	private final String id;
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
	public List<Result> match(String input)
	{
		List<Result> results = new ArrayList<Result>();
		
		for (Result result : this.definition.match(input))
		{
			final AbstractNode node = new NamedNode(this.id);
			node.children.add(result.node);
			results.add(new Result(node, result.rest));
		}
		
		return results;
	}
}
