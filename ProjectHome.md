  * Can parse (A/E)BNF-style grammars.
  * Non-code-generating.
  * Minimalistic design.
  * Based on combinators.

Example grammar:
```
positive-number ::= (%digit% { %digit% })
negative-number ::= ("-" positive-number)

addition ::= (("+" | "-") (positive-number | ("(" negative-number ")") | mul-expression))
sum-expression ::= ((positive-number | negative-number | mul-expression) addition {addition})

multiplication ::= (("*" | "/") (positive-number | ("(" negative-number ")") | ("(" sum-expression ")")))
mul-expression ::= ((positive-number | negative-number | ("(" sum-expression ")")) multiplication {multiplication})

expression ::= sum-expression
```