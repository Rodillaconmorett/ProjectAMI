package is.ecci.ucr.projectami.DecisionTree;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;


public class Node {
	
	private Label _label;
	private LinkedHashMap<Double, Node> _branches;
	private Attribute _attribute;
	private int _nodeID;
	
	public Node (int nodeCounter) {
		_label = new Label();
		_branches = new LinkedHashMap<Double, Node>();
		_attribute = new Attribute();
		_nodeID = nodeCounter;
	}
	
	public Node (Node that) {
		this(-1);
	}
	
	public int getNodeID() {
		return _nodeID;
	}
	
	public Label getLabel() {
		return _label;
	}
	
	public void setLabel(Label label) {
		_label = label;
	}

	public void setAttribute(Attribute bestAttribute) {
		_attribute = bestAttribute;
	}
	
	public void addBranch(double value, Node node) {
		_branches.put(value, node);
	}
	
	public LinkedHashMap<Double, Node> getBranches() {
		return _branches;
	}
	
	public Attribute getAttribute() {
		return _attribute;
	}
	
	public void dumpDot(Matrix examples, Matrix targetAttributes, String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new File(fileName));
		out.println("digraph DecisionTree {");
		out.println("graph [ordering=\"out\"];");
		dumpDot(out, examples, targetAttributes);
		out.println("}");
		out.close();
	}
	
	private void dumpDot(PrintWriter out, Matrix examples, Matrix targetAttributes) {
		String myLabel = "";
		if (_branches.isEmpty()) {
			myLabel = _label.getStrValue();
		}
		else {
			myLabel = _attribute.getName();
		}
		System.out.println("  " + _nodeID + " [label=\"" + myLabel + "\"];");
		out.println("  " + _nodeID + " [label=\"" + myLabel + "\"];");//" + toString() + "\"];\n");
		
		if (!_branches.isEmpty()) {
			for (double key : _branches.keySet()) {
				Node childNode = _branches.get(key);
				String edgeLabel = "";
				edgeLabel = examples.attrValue(_attribute.getColumnPositionID(), (int)key);
				int childNodeID = childNode.getNodeID();
				System.out.println("  " + _nodeID + " -> " + childNodeID);
				System.out.println("  " + _nodeID + " -> " + childNodeID);
				out.print("  " + _nodeID + " -> " + childNodeID);
				out.print(" [label=\" " + edgeLabel + "\"];\n");
//				System.out.println(edgeLabel);
				childNode.dumpDot(out, examples, targetAttributes);
			}
		}
	}
	
	public double makeDecision (double[] features, int attribute) {
		double decision = 0;
		if (_branches.isEmpty())
			return _label.getValue();
		else {
			for (double branchValue : _branches.keySet()) {
				double value = features[attribute];
				if (branchValue == value) {
					Node childNode = _branches.get(branchValue);
					decision = childNode.makeDecision(features, childNode.getAttribute().getColumnPositionID());
				}
			}
		}
		return decision;
	}
}
