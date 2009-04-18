/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.MatchResults;
import info.reflectionsofmind.parser.node.AbstractNode;

import java.util.Iterator;

public final class ExcludingMatcher extends Matcher
{
	private final Matcher[] filters;
	private final Matcher matcher;

	public ExcludingMatcher(Matcher matcher, Matcher[] filters)
	{
		this.filters = filters;
		this.matcher= matcher;
	}

	@Override
	public MatchResults match(final String input, int start) 
	{
		final MatchResults results = matcher.match(input, start);
		if (!results.success())
			return results;
		
		for (int i = 0; i < filters.length; i++)
		{
			MatchResults filterResults= filters[i].match(input, start);
			if (filterResults.success())
			{
				for (Iterator<AbstractNode> x= filterResults.matches.iterator(); !results.matches.isEmpty() && x.hasNext();) 
				{
					final AbstractNode xresult= x.next();
					for (Iterator<AbstractNode> r= results.matches.iterator(); r.hasNext();) 
					{
						AbstractNode result= r.next();
						if (result.getText().equals(xresult.getText()))
							r.remove();
					}
				}
			}
		}
		
		return results;
	}
	
	@Override
	public String getLabel()
	{
		String label= matcher.getLabel()+" (except ";
		for (int i= 0; i < filters.length; i++)
		{
			if (0 < i)
				label+= (i == filters.length - 1) ? ", or " : ", ";
			label+= filters[i].getLabel();
		}
		label+=")";
		return label;
	}
}