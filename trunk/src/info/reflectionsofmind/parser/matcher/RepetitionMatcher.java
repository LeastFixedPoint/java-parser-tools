/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Matchers;
import info.reflectionsofmind.parser.ResultTree;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.RepetitionNode;

import java.util.ArrayList;
import java.util.List;

public final class RepetitionMatcher implements Matcher
{
	public enum Type
	{
		SEQUENCE, CHOICE
	}

	private final int min;
	private final int max;
	private final Matcher matcher;

	public RepetitionMatcher(int min, int max, Type type, Matcher... matchers)
	{
		this.min = min;
		this.max = max;

		switch (type)
		{
			case SEQUENCE:
				this.matcher = Matchers.seq(matchers);
				break;
			case CHOICE:
				this.matcher = Matchers.cho(matchers);
				break;
			default:
				throw new RuntimeException("Unknown repetition matcher subtype: " + type);
		}
	}

	public RepetitionMatcher(int min, int max, Matcher matcher)
	{
		this.min = min;
		this.max = max;
		this.matcher = matcher;
	}

	@Override
	public List<ResultTree> match(final String input)
	{
		final List<ResultTree> combinedResults = new ArrayList<ResultTree>();

		final List<Matcher> repetition = new ArrayList<Matcher>();

		for (int i = 0; i < min; i++)
		{
			repetition.add(matcher);
		}

		final List<ResultTree> results = Matchers.seq(repetition.toArray(new Matcher[] {})).match(input);

		for (final ResultTree result : results)
		{
			final AbstractNode node = new RepetitionNode();
			node.children.addAll(result.root.children);
			combinedResults.add(new ResultTree(node, result.rest));
		}

		for (int i = 1; i <= max - min; i++)
		{
			final List<ResultTree> combinedSubResults = new ArrayList<ResultTree>();

			for (final ResultTree result : results)
			{
				final List<ResultTree> subResults = Matchers.reps(i, i, matcher).match(input.substring(result.rest));

				for (final ResultTree subResult : subResults)
				{
					final AbstractNode node = new RepetitionNode();
					node.children.addAll(result.root.children);
					node.children.addAll(subResult.root.children);
					combinedSubResults.add(new ResultTree(node, result.rest + subResult.rest));
				}
			}

			if (combinedSubResults.isEmpty())
			{
				break;
			}
			else
			{
				combinedResults.addAll(combinedSubResults);
			}
		}

		return combinedResults;
	}
}