package is.ecci.ucr.projectami.DecisionTree;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Root extends Node {
	
	private LinkedHashSet<Branch<Integer, Node>> _branches;
	private Attribute _attribute;
	
	public Root(int nodeCounter) {
		super(nodeCounter);
		_branches = new LinkedHashSet<Branch<Integer, Node>>();
	}
	
	public void setAttribute(Attribute bestAttribute) {
		_attribute = bestAttribute;
	}
	
	public void addBranch(int value, Node node) {
		Branch branch = new Branch(value, node);
		_branches.add(branch);
	}
}
