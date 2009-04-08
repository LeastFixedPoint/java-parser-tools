package info.reflectionsofmind.parser.node;

import java.util.List;

public class NamedNode extends AbstractNode
{
	public NamedNode(String id)
	{
		super(id);
	}

	public NamedNode(String id, String text)
	{
		super(id, text);
	}

	public List<NamedNode> getNamedChildren()
	{
		return Navigation.getNamedChildren(this);
	}

}