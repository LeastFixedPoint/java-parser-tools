package info.reflectionsofmind.parser;

import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.DOCUMENT;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.LITERAL;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.OBJECT_LIST;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.PREDICATE_VALUES;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.STATEMENT;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.SUBJECT;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.TRIPLES;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.URIREF;
import static info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar.VERB;
import info.reflectionsofmind.parser.matcher.Matcher;
import info.reflectionsofmind.parser.matcher.Matchers;
import info.reflectionsofmind.parser.matcher.contrib.TurtleGrammar;
import info.reflectionsofmind.parser.node.AbstractNode;
import info.reflectionsofmind.parser.node.Navigation;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TurtleTests
extends TestCase
{
	public void testBasicTriples() throws Exception {
		
		String document= 	
			"@prefix resource: <meteor:net.sf.meteor.Resource.>.\n" +
			"@prefix test: <meteor:net.sf.meteor.test.>.\n" +
			"\n" +
			"test:CustomerFromManifest resource:type test:Customer ;\n" +
			"			resource:description \"A Customer defined in a Meteor manifest\" .\n\n\n\n\n";
		
		Matcher literalMatcher= TurtleGrammar.getMatcher(LITERAL);		
		Assert.assertEquals(1, literalMatcher.match("\"A Customer defined in a Meteor manifest\"").matches.size());
		
		String testgrammar= "uriref ::= (\"<\" relativeURI \">\")\n" +
				"relativeURI ::= (anychar {anychar})\n" +
				"anychar ::= (%alpha% | %digit% | \" \" | \":\" | \".\")";  
		Matcher relativeURIMatcher= Grammar.generate(testgrammar, "relativeURI");		
		Assert.assertEquals(1, Matchers.fullMatch(relativeURIMatcher, "meteor:net.sf.meteor.TestBindType.instance").matches.size());
		
		Matcher urirefMatcher= TurtleGrammar.getMatcher(URIREF);		
		Assert.assertEquals(1, urirefMatcher.match("<meteor:net.sf.meteor.TestBindType.instance>").matches.size());
		
		Matcher subjectMatcher= TurtleGrammar.getMatcher(SUBJECT);		
		Assert.assertEquals(1, subjectMatcher.match("<meteor:net.sf.meteor.TestBindType.instance>").matches.size());
		Assert.assertEquals(1, subjectMatcher.match("test:CustomerFromManifest").matches.size());
		
		Matcher verbMatcher= TurtleGrammar.getMatcher(VERB);	
		Assert.assertEquals(1, verbMatcher.match("resource:type").matches.size());
		
		Matcher objectListMatcher= TurtleGrammar.getMatcher(OBJECT_LIST);		
		Assert.assertEquals(1, objectListMatcher.match("webbench:WebbenchModule").matches.size());
		
		Matcher valuesMatcher= TurtleGrammar.getMatcher(PREDICATE_VALUES);		
		Assert.assertEquals(1, valuesMatcher.match("resource:type webbench:WebbenchModule").matches.size());
		Assert.assertEquals(1, valuesMatcher.match("resource:description \"A Customer defined in a Meteor manifest\"").matches.size());
		Assert.assertEquals(1, valuesMatcher.match("resource:type test:Customer ;\n\t\t\tresource:description \"A Customer defined in a Meteor manifest\"").matches.size());
		
		Matcher triplesMatcher= TurtleGrammar.getMatcher(TRIPLES);
		String tripleDoc= "<meteor:net.sf.meteor.TestBindType.instance> resource:type webbench:WebbenchModule"; 
		Assert.assertEquals(1, triplesMatcher.match(tripleDoc).matches.size());
		tripleDoc= "test:CustomerFromManifest resource:type test:Customer ;\n\t\t\tresource:description \"A Customer defined in a Meteor manifest\"";
		Assert.assertEquals(1, triplesMatcher.match(tripleDoc).matches.size());
		
		Matcher statementMatcher= TurtleGrammar.getMatcher(STATEMENT);
		String statementDoc= tripleDoc+" ."; 
		Assert.assertEquals(1, statementMatcher.match(statementDoc).matches.size());
		
		Matcher matcher= TurtleGrammar.getMatcher(DOCUMENT);		
		MatchResults results= matcher.match(document);
		Assert.assertEquals(1, results.matches.size());
		AbstractNode rootNode= results.matches.get(0);
		
		// the AST should have 3 statements in it
		List<AbstractNode> statements= Navigation.findAllById(rootNode, "statement");
		Assert.assertEquals(3, statements.size());
		
		
		document= "@prefix meteor: <meteor:net.sf.meteor.> .\n" +
				"@prefix jdbc: <meteor:net.sf.meteor.storage.jdbc.> .\n" +
				"@prefix h2: <meteor:net.sf.meteor.library.h2.> .\n\n" +
				"h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor ;\n" +
				"jdbc:JDBCDriverDescriptor.protocol \"jdbc:h2\" ;\n" +
				"jdbc:JDBCDriverDescriptor.driverClass \"org.h2.Driver\" .\n\n\n\n\n\n\n"; 
		results= Matchers.fullMatch(matcher, document);
		Assert.assertEquals(0, results.matches.size());
		
		
	}
	
}
