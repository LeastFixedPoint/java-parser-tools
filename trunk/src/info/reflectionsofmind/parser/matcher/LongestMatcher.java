/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;

import java.util.ArrayList;
import java.util.List;

public final class LongestMatcher implements Matcher
{
	private final Matcher[] matchers;

	public LongestMatcher(Matcher[] matchers)
	{
		this.matchers = matchers;
	}

	@Override
	public List<Result> match(final String input)
	{
		final List<Result> allResults = new ArrayList<Result>();
		int maxRest= 0;
		
		for (int i = 0; i < matchers.length; i++)
		{
			for (final Result result : matchers[i].match(input))
			{
				allResults.add(result);
				if (maxRest < result.rest)
					maxRest= result.rest;
			}
		}

		if (allResults.isEmpty())
			return allResults;
		
		final List<Result> longestResults = new ArrayList<Result>();
		for (Result result : allResults)
			if (maxRest <= result.rest)
				longestResults.add(result);

		return longestResults;
	}
}