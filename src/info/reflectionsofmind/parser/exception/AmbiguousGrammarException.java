package info.reflectionsofmind.parser.exception;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.matcher.Matchers;

import java.util.List;

public class AmbiguousGrammarException extends GrammarParsingException
{
	public AmbiguousGrammarException(List<Result> ambiguousResults)
	{
		super("May be parsed as [" + ambiguousResults.size() + "] trees:\n" + Matchers.toStringNamed(ambiguousResults));
	}
}
