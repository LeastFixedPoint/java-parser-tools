## Example: reading grammar from BNF ##

Let's say we have a following grammar stored in a file.

```
positive-number ::= (%digit% { %digit% })
negative-number ::= ("-" positive-number)

addition ::= (("+" | "-") (positive-number | ("(" negative-number ")") | mul-expression))
sum-expression ::= ((positive-number | negative-number | mul-expression) addition {addition})

multiplication ::= (("*" | "/") (positive-number | ("(" negative-number ")") | ("(" sum-expression ")")))
mul-expression ::= ((positive-number | negative-number | ("(" sum-expression ")")) multiplication {multiplication})

expression ::= sum-expression
```

First, we need to read this grammar as a `String`.

```
String grammar = Files.readFileAsString(new File("path/to/file"));
```

Next, parse it into a `Matcher`.
The second parameter is the "top-level" expression of this grammar.

```
Matcher expression = Grammar.generate(grammar, "expression");
```

Using this `Matcher`, we can parse `String`s into `NamedNode` trees.

But given that a grammar may be ambiguous, the `Matchers.fullMatch`  method returns a list of `ResultTree`s, each of them a different representation of the expression. Ideally, though, there will be only one `ResultTree` in the list (or none if the expression doesn't conform to the grammar).

```
List<ResultTree> results = Matchers.fullMatch(matcher, "2+2");
ResultTree firstResult = results.get(0);
NamedNode root = (NamedNode) firstResult.root;
```

That root node corresponds to the "expression" rule in the grammar. We can descend into child nodes using the `getNamedChildren()` method of a `NamedNode`.

```
NamedNode sumExpression = root.getNamedChildren().get(0);
System.out.println(sumExpression.id); // prints "sum-expression"

NamedNode firstArgument = sumExpression.getNamedChildren().get(0);
System.out.println(firstArgument.id); // prints "positive-number"

NamedNode addition = sumExpression.getNamedChildren().get(1);
System.out.println(addition.id); // prints "addition"

NamedNode secondArgument = addition.getNamedChildren().get(0);
System.out.println(secondArgument.id); // prints "positive-number"

System.out.println(firstArgument.getText()+ " + " + secondArgument.getText());
// prints "2 + 2"
```

To pretty-print a `NamedNode` tree, use the `Matchers.toStringNamed` method:

```
System.out.println(Matchers.toStringNamed(root));
```

The console output will be:

```
expression:[2+2]
	sum-expression:[2+2]
		positive-number:[2]
		addition:[+2]
			positive-number:[2]
```