package info.reflectionsofmind.parser.exception;

import info.reflectionsofmind.parser.matcher.Matchers;
import info.reflectionsofmind.parser.node.AbstractNode;

import java.util.List;

public class AmbiguousGrammarException extends GrammarParsingException
{
	private static final long	serialVersionUID	= 1L;

	public AmbiguousGrammarException(List<AbstractNode> ambiguousResults)
	{
		super("May be parsed as [" + ambiguousResults.size() + "] trees:\n" + Matchers.toStringNamed(ambiguousResults));
	}
}
