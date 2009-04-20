/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.ChoiceNode;

import java.util.ArrayList;
import java.util.List;

public class ChoiceMatcher extends Matcher
{
	protected final Matcher[] matchers;

	public ChoiceMatcher(Matcher[] matchers)
	{
		this.matchers = matchers;
	}

	@Override
	public MatchResults match(final String input, int start) 
	{
		final List<AbstractNode> combinedResults = new ArrayList<AbstractNode>();

		MatchResults err= null;
		for (int i = 0; i < matchers.length; i++)
		{
			MatchResults results= matchers[i].match(input, start);
			if (results.success())
			{
				for (final AbstractNode result : results.matches)
				{
					final AbstractNode node = new ChoiceNode(i, start, result.end);
					node.children.add(result);
					combinedResults.add(node);
				}
			}
			else if (err == null || err.position < results.position)
				err= results;
		}
		
		if (combinedResults.isEmpty())
		{
			if (err != null)
				return err;
			return new MatchResults("Expected "+getLabel(), start);
		}

		return new MatchResults(combinedResults);
	}

	public String getLabel()
	{
		String label= "one of the following: ";
		for (int i= 0; i < matchers.length; i++)
		{
			if (0 < i)
				label+= (i == matchers.length - 1) ? ", or " : ", ";
			label+= matchers[i].getLabel();
		}
		return label;
	}
}