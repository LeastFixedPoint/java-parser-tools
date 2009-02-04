package info.reflectionsofmind.parser;

import info.reflectionsofmind.parser.matcher.ChoiceMatcher;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.matcher.RangeMatcher;
import info.reflectionsofmind.parser.matcher.RepetitionMatcher;
import info.reflectionsofmind.parser.matcher.SequenceMatcher;
import info.reflectionsofmind.parser.matcher.StringMatcher;

import java.util.Arrays;

public final class Parsers
{
	private Parsers()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	// ============================================================================================
	// === BASIC MATCHERS
	// ============================================================================================

	public static Matcher str(final String string)
	{
		return new StringMatcher(string);
	}

	public static Matcher range(final char from, final char to)
	{
		return new RangeMatcher(to, from);
	}

	public static Matcher seq(final Matcher... matchers)
	{
		return new SequenceMatcher(matchers);
	}

	public static Matcher cho(final Matcher... matchers)
	{
		return new ChoiceMatcher(matchers);
	}

	// ============================================================================================
	// === SIMPLE REPETITION MATCHERS
	// ============================================================================================
	
	public static Matcher rep(final Matcher matchers)
	{
		return rep(0, Integer.MAX_VALUE, matchers);
	}

	public static Matcher rep(final int num, final Matcher matchers)
	{
		return rep(num, num, matchers);
	}

	public static Matcher min(final int min, final Matcher matchers)
	{
		return rep(min, Integer.MAX_VALUE, matchers);
	}

	public static Matcher max(final int max, final Matcher matchers)
	{
		return rep(0, max, matchers);
	}

	public static Matcher rep(final int min, final int max, final Matcher matcher)
	{
		return new RepetitionMatcher(min, max, matcher);
	}

	// ============================================================================================
	// === REPETITION OF SEQUENCE MATCHERS
	// ============================================================================================
	
	public static Matcher reps(final Matcher... matchers)
	{
		return reps(0, Integer.MAX_VALUE, matchers);
	}

	public static Matcher reps(final int num, final Matcher... matchers)
	{
		return reps(num, num, matchers);
	}

	public static Matcher mins(final int min, final Matcher... matchers)
	{
		return reps(min, Integer.MAX_VALUE, matchers);
	}

	public static Matcher maxs(final int max, final Matcher... matchers)
	{
		return reps(0, max, matchers);
	}

	public static Matcher reps(final int min, final int max, final Matcher... matchers)
	{
		return new RepetitionMatcher(min, max, RepetitionMatcher.Type.SEQUENCE, matchers);
	}

	// ============================================================================================
	// === REPETITION OF CHOICE MATCHERS
	// ============================================================================================
	
	public static Matcher repc(final Matcher... matchers)
	{
		return repc(0, Integer.MAX_VALUE, matchers);
	}

	public static Matcher repc(final int num, final Matcher... matchers)
	{
		return repc(num, num, matchers);
	}

	public static Matcher minc(final int min, final Matcher... matchers)
	{
		return repc(min, Integer.MAX_VALUE, matchers);
	}

	public static Matcher maxc(final int max, final Matcher... matchers)
	{
		return repc(0, max, matchers);
	}

	public static Matcher repc(final int min, final int max, final Matcher... matchers)
	{
		return new RepetitionMatcher(min, max, RepetitionMatcher.Type.CHOICE, matchers);
	}

	// ============================================================================================
	// === OPTIONAL MATCHERS
	// ============================================================================================

	public static Matcher opt(final Matcher matcher)
	{
		return cho(matcher, str(""));
	}

	public static Matcher opts(final Matcher... matchers)
	{
		return cho(seq(matchers), str(""));
	}

	public static Matcher optc(final Matcher... matchers)
	{
		return cho(cho(matchers), str(""));
	}

	public static <T> Matcher[] tail(final Matcher[] array)
	{
		return Arrays.asList(array).subList(1, array.length).toArray(new Matcher[] {});
	}
}
