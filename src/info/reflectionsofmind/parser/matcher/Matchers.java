package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.NamedNode;
import info.reflectionsofmind.parser.node.Nodes;

import java.util.ArrayList;
import java.util.List;

public final class Matchers
{
	private Matchers()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static List<Result> fullMatch(final Matcher matcher, final String input)
	{
		final List<Result> partResults = matcher.match(input);
		final List<Result> fullResults = new ArrayList<Result>();

		for (final Result result : partResults)
		{
			if (result.rest == input.length())
			{
				fullResults.add(result);
			}
		}

		return fullResults;
	}

	public static String toStringFull(Result result)
	{
		return "Matched [" + result.rest + "] symbols, tree:\n" + Nodes.toStringFull(result.node) + "\n";
	}

	public static String toStringNamed(Result result)
	{
		return "Matched [" + result.rest + "] symbols, tree:\n" + Nodes.toStringNamed((NamedNode) result.node) + "\n";
	}

	public static String toStringFull(List<Result> results)
	{
		StringBuilder builder = new StringBuilder();

		for (Result result : results)
			builder.append(toStringFull(result));
		
		return builder.toString();
	}

	public static String toStringNamed(List<Result> results)
	{
		StringBuilder builder = new StringBuilder();

		for (Result result : results)
			builder.append(toStringNamed(result));
		
		return builder.toString();
	}
}
