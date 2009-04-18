package info.reflectionsofmind.parser.exception;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.matcher.Matchers;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.NamedNode;

import java.util.List;

public class InvalidGrammarException extends GrammarParsingException
{
	private static final long serialVersionUID = 1L;
	public final MatchResults results;
	
	public InvalidGrammarException(MatchResults results)
	{
		super("Grammar cannot be fully parsed. Longest tree:\n" + Matchers.toStringNamed((NamedNode)getLongestResult(results.matches)));
		this.results= results;
	}

	private static AbstractNode getLongestResult(List<AbstractNode> results)
	{
		AbstractNode longestResult = results.get(0);

		for (AbstractNode result : results)
			if (result.end > longestResult.end) longestResult = result;
		
		return longestResult;
	}
}
