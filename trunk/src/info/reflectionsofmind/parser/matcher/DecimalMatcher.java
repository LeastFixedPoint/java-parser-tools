package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;
import info.reflectionsofmind.parser.node.StringNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DecimalMatcher
implements Matcher
{
	static final private IntegerMatcher __integerMatcher= new IntegerMatcher();
	//decimal ::= ([sign] ((digits "." {digits}) | ("." digits) | digits)) 
	@Override
	public List<Result> match(String input)
	{
		int i;
		List<Result> integers= __integerMatcher.match(input);
		if (integers.isEmpty()) {
			if ('.' != input.charAt(0)) 
				return Collections.<Result> emptyList();
			integers= __integerMatcher.match(input.substring(1));
			if (integers.isEmpty()) 
				return Collections.<Result> emptyList();
			i= integers.get(0).rest+1;
		}
		else {
			i= integers.get(0).rest;
			if ('.' == input.charAt(0)) {
				i++;
				
				integers= __integerMatcher.match(input.substring(i));
				if (!integers.isEmpty()) 
					i+= integers.get(0).rest;
			}
		}
		
		return Arrays.asList(new Result(new StringNode(input.substring(0, i)), i));
	}
}
