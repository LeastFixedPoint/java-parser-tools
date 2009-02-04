package info.reflectionsofmind.parser.matcher;

import info.reflectionsofmind.parser.Result;

import java.util.List;

public interface Matcher
{
	List<Result> match(String input);
}