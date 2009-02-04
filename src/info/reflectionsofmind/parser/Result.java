/**
 * 
 */
package info.reflectionsofmind.parser;

import info.reflectionsofmind.parser.node.AbstractNode;

public final class Result
{
	/** Node containing parsed symbols as a tree. */
	public final AbstractNode node;

	/** Index of the first symbol of the unparsed part of the string. */
	public final int rest;

	public Result(final AbstractNode tree, final int rest)
	{
		this.node = tree;
		this.rest = rest;
	}

	@Override
	public String toString()
	{
		return this.node.toString();
	}
}