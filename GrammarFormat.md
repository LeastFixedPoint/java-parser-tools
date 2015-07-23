A BNF string has the following format:

```
name1 ::= (expression1)
name2 ::= (expression2)
...
```

It is not required that every definition starts from a new line, we can separate them just by whitespace:

```
name1 ::= (expression1) name2 ::= (expression2) ...
```

An expression may contain following elements:

| `(a b c)` | Expressions a, then b, then c in this order. |
|:----------|:---------------------------------------------|
| `(a|b|c)` | One of the expressions a, b and c.           |
| `{a b c}` | Sequence of a, then b, then c, repeated any number of times (maybe zero). |
| `{a|b|c}` | Sequence of any number of a's, b's and c's in any order. |
| `[a b c]` | Either a, then b, then c, or nothing at all. |
| `[a|b|c]` | Either a, or b, or c, or nothing at all.     |
| `"text"`  | This exact text.  Double quotes in the text may be escaped with single quotes.  For example "'"this text begins and ends with a double quote'"". |
| `%lower%` | Lower case letter.                           |
| `%upper%` | Upper case letter.                           |
| `%alpha%` | Any case letter.                             |
| `%digit%` | A digit (0 to 9).                            |
| `%whitespace%` | Any sequence (but at least one) of ' ' or '\n' or '\r' or '\t' |