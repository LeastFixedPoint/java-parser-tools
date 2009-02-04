package info.reflectionsofmind.parser;

import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.matcher.Matchers;
import info.reflectionsofmind.parser.node.NamedNode;
import info.reflectionsofmind.parser.node.Nodes;
import info.reflectionsofmind.util.Files;

public class Application
{
	public static void main(final String[] args) throws Exception
	{
		String path = Thread.currentThread().getContextClassLoader().getResource("info/reflectionsofmind/parser/example1.grammar").getFile().replaceAll("%20", " ");
		String input = Files.readAsString(path);
		
		long start = System.currentTimeMillis();
		Matcher matcher = Grammar.generate(input, "expression");
		long delay = System.currentTimeMillis() - start;
		System.out.println("Grammar generated in: " + delay);
		
		start = System.currentTimeMillis();
		Result result = Matchers.fullMatch(matcher, "2+2").get(0);
		delay = System.currentTimeMillis() - start;
		System.out.println("Input parsed in: " + delay);
		
		System.out.println(Nodes.toStringNamed((NamedNode)result.node));
	}
}
