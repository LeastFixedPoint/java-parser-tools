/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.ChoiceNode;

import java.util.ArrayList;
import java.util.List;

public final class ChoiceMatcher implements Matcher
{
	private final Matcher[] matchers;

	public ChoiceMatcher(Matcher[] matchers)
	{
		this.matchers = matchers;
	}

	@Override
	public List<Result> match(final String input)
	{
		final List<Result> combinedResults = new ArrayList<Result>();

		for (int i = 0; i < matchers.length; i++)
		{
			for (final Result result : matchers[i].match(input))
			{
				final AbstractNode node = new ChoiceNode(i);
				node.children.add(result.node);
				combinedResults.add(new Result(node, result.rest));
			}
		}

		return combinedResults;
	}
}