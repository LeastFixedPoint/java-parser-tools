package info.reflectionsofmind.parser;

import static info.reflectionsofmind.parser.Parsers.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import info.reflectionsofmind.parser.matcher.Matcher;

/**
*/
public class ParserTest
{
	@Test
	public void testValSuccess()
	{
		Matcher val = str("test");
		List<Result> results = val.match("test1");

		Assert.assertEquals(1, results.size());
		Assert.assertEquals(4, results.get(0).rest);
		Assert.assertTrue(results.get(0).node.children.isEmpty());
	}

	@Test
	public void testValFailure()
	{
		Matcher val = str("test");
		List<Result> results = val.match("tes1t");

		Assert.assertEquals(0, results.size());
	}

	@Test
	public void testSeqSuccess()
	{
		Matcher seq = seq(str("1"), str("2"));
		List<Result> results = seq.match("123");

		Assert.assertEquals(1, results.size());
		Assert.assertEquals(2, results.get(0).rest);
		Assert.assertEquals(2, results.get(0).node.children.size());
	}

	@Test
	public void testSeqFailure()
	{
		Matcher seq = seq(str("1"), str("2"));
		List<Result> results = seq.match("132");

		Assert.assertTrue(results.isEmpty());
	}

	@Test
	public void testChoSuccess()
	{
		Matcher cho = cho(str("12"), str("123"));
		List<Result> results = cho.match("1234");

		Assert.assertEquals(2, results.size());
		Assert.assertEquals(2, results.get(0).rest);
		Assert.assertEquals(3, results.get(1).rest);
		Assert.assertTrue(results.get(0).node.children.isEmpty());
		Assert.assertTrue(results.get(1).node.children.isEmpty());
	}

	@Test
	public void testChoFailure()
	{
		Matcher cho = cho(str("12"), str("123"));
		List<Result> results = cho.match("132");

		Assert.assertTrue(results.isEmpty());
	}

	@Test
	public void testOptPresent()
	{
		Matcher opt = opt(str("12"));
		List<Result> results = opt.match("123");

		Assert.assertEquals(2, results.size());
	}

	@Test
	public void testOptAbsent()
	{
		Matcher opt = opt(str("12"));
		List<Result> results = opt.match("132");

		Assert.assertEquals(1, results.size());
	}

	@Test
	public void testRepSuccess1()
	{
		Matcher rep = reps(1, 3, str("12"));
		List<Result> results = rep.match("121212121");

		Assert.assertEquals(3, results.size());
		Assert.assertEquals(2, results.get(0).rest);
		Assert.assertEquals(4, results.get(1).rest);
		Assert.assertEquals(6, results.get(2).rest);
	}

	@Test
	public void testRepSuccess2()
	{
		Matcher rep = reps(str("12"));
		List<Result> results = rep.match("1212121");

		Assert.assertEquals(4, results.size());
		Assert.assertEquals(0, results.get(0).rest);
		Assert.assertEquals(2, results.get(1).rest);
		Assert.assertEquals(4, results.get(2).rest);
		Assert.assertEquals(6, results.get(3).rest);
	}

	@Test
	public void testRepFailure()
	{
		Matcher rep = reps(2, 3, str("123"));
		List<Result> results = rep.match("12312123");

		Assert.assertTrue(results.isEmpty());
	}
}
