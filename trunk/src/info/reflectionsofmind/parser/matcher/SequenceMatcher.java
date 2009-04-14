/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Parsers;
import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.SequenceNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SequenceMatcher implements Matcher
{
	private final Matcher[] matchers;

	public SequenceMatcher(Matcher[] matchers)
	{
		this.matchers = matchers;
	}

	@Override
	public List<Result> match(final String input)
	{
		if (matchers.length == 0) return Arrays.asList(new Result(new SequenceNode(), 0));

		final List<Result> results = matchers[0].match(input);
		final List<Result> combinedResults = new ArrayList<Result>();

		for (final Result result : results)
		{
			if (result.node != null)
			{
				List<Result> subResults= Parsers.seq(Parsers.tail(matchers)).match(input.substring(result.rest));
				for (final Result subResult : subResults)
				{
					final AbstractNode node = new SequenceNode();
					node.children.add(result.node);
					node.children.addAll(subResult.node.children);

					combinedResults.add(new Result(node, result.rest + subResult.rest));
				}
			}
		}

		return combinedResults;
	}
}