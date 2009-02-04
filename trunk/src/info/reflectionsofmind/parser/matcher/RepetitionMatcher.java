/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Parsers;
import info.reflectionsofmind.parser.Result;
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
				this.matcher = Parsers.seq(matchers);
				break;
			case CHOICE:
				this.matcher = Parsers.cho(matchers);
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
	public List<Result> match(final String input)
	{
		final List<Result> combinedResults = new ArrayList<Result>();

		final List<Matcher> repetition = new ArrayList<Matcher>();

		for (int i = 0; i < min; i++)
		{
			repetition.add(matcher);
		}

		final List<Result> results = Parsers.seq(repetition.toArray(new Matcher[] {})).match(input);

		for (final Result result : results)
		{
			final AbstractNode node = new RepetitionNode();
			node.children.addAll(result.node.children);
			combinedResults.add(new Result(node, result.rest));
		}

		for (int i = 1; i <= max - min; i++)
		{
			final List<Result> combinedSubResults = new ArrayList<Result>();

			for (final Result result : results)
			{
				final List<Result> subResults = Parsers.reps(i, i, matcher).match(input.substring(result.rest));

				for (final Result subResult : subResults)
				{
					final AbstractNode node = new RepetitionNode();
					node.children.addAll(result.node.children);
					node.children.addAll(subResult.node.children);
					combinedSubResults.add(new Result(node, result.rest + subResult.rest));
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