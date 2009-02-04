package info.reflectionsofmind.parser.exception;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.matcher.Matchers;

import java.util.List;

public class InvalidGrammarException extends GrammarParsingException
{
	public InvalidGrammarException(List<Result> invalidResults)
	{
		super("Grammar cannot be fully parsed. Longest tree:\n" + Matchers.toStringNamed(getLongestResult(invalidResults)));
	}

	private static Result getLongestResult(List<Result> results)
	{
		Result longestResult = results.get(0);

		for (Result result : results)
			if (result.rest > longestResult.rest) longestResult = result;
		
		return longestResult;
	}
}
