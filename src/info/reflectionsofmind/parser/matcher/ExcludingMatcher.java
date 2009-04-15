/**
 * 
 */
package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;

import java.util.Iterator;
import java.util.List;

public final class ExcludingMatcher implements Matcher
{
	private final Matcher[] filters;
	private final Matcher matcher;

	public ExcludingMatcher(Matcher matcher, Matcher[] filters)
	{
		this.filters = filters;
		this.matcher= matcher;
	}

	@Override
	public List<Result> match(final String input)
	{
		final List<Result> results = matcher.match(input);
		
		for (int i = 0; !results.isEmpty() && i < filters.length; i++)
		{
			for (Iterator<Result> x= filters[i].match(input).iterator(); !results.isEmpty() && x.hasNext();) 
			{
				final Result xresult= x.next();
				for (Iterator<Result> r= results.iterator(); r.hasNext();) 
				{
					Result result= r.next();
					if (result.rest == xresult.rest)
						r.remove();
				}
			}
		}
		
		return results;
	}
}