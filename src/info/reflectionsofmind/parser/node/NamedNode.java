package info.reflectionsofmind.parser.node;

import java.util.ArrayList;
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
		return getNamedChildren(this);
	}

	private List<NamedNode> getNamedChildren(AbstractNode root)
	{
		List<NamedNode> namedNodes = new ArrayList<NamedNode>();

		for (AbstractNode node : root.children)
		{
			if (node instanceof NamedNode)
			{
				namedNodes.add((NamedNode) node);
			}
			else
			{
				namedNodes.addAll(getNamedChildren(node));
			}
		}

		return namedNodes;
	}
}