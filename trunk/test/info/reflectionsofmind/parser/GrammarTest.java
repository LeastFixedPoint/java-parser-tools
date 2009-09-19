/**
 * 
 */
package info.reflectionsofmind.parser;

import info.reflectionsofmind.parser.exception.GrammarParsingException;

import org.junit.Assert;
import org.junit.Test;

public class GrammarTest
{
	@Test
	public void grammarShouldDetectMissingSymbols()
	{
		String grammar = "a ::= (b c)\nb ::= %digit%";
		try
		{
			Grammar.generate(grammar, "a");
			Assert.fail("The Grammar.generate method failed to detect that the 'c' symbol is not defined.");
		} catch (GrammarParsingException e)
		{
		}
	}
}