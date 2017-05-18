package is.ecci.ucr.projectami.DecisionTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ID3 {
	
	private int _nodeCounter;
	private TableManager tableManager;
	
	public ID3 () {
		_nodeCounter = 0;
	}
	
	public double getNodeCounter() {
		return _nodeCounter;
	}
	
	public Node buildTree (Matrix examples, Matrix targetAttributes) {
		LinkedHashSet<Attribute> attributes = createAllAttributes(examples);
		tableManager = new TableManager(examples, targetAttributes, attributes);
		Node root = runID3(examples, targetAttributes, attributes);
		return root;
	}
	
	// this set does not include the target attribute
	private LinkedHashSet<Attribute> createAllAttributes (Matrix examples) {
		LinkedHashSet<Attribute> attributes = new LinkedHashSet<Attribute>();
		for (int i = 0; i < examples.cols(); i++) {
			Attribute attribute = new Attribute(examples.attrName(i), getValuesOfAttributeFromCurrentTable(examples, i), i);
			attributes.add(attribute);
		}
		return attributes;
	}
	
	// attributes can either be target attributes or examples
	private LinkedHashSet<Double> getValuesOfAttributeFromCurrentTable (Matrix attributes, int attribute) {
		LinkedHashSet<Double> values = new LinkedHashSet<Double>();
		int numberofValues = attributes.valueCount(attribute);
		for (int i = 0; i < attributes.rows(); i++) {
			if (attributes.get(i, attribute) != Double.MAX_VALUE)
				values.add(attributes.get(i, attribute));
			if (values.size() == numberofValues)
				break;
		}
		return values;
	}
	
	public Node runID3 (Matrix examples, Matrix targetAttributes, LinkedHashSet<Attribute> attributes) {
		
		//Prueba de recorrido sobre los atributos
		int counter = 1;
		for (Attribute attri : attributes){
			System.out.print(counter + " : "+ attri.getName() + "{");
			for (Double value : attri.getValues()){
				/*examples.attrValue(): String
				Este m�todo hace uso de m_enum_to_str.get(attr).get(val) el cual devuelve el nombre del valor de atributo.
				*/
				System.out.print(examples.attrValue(attri.getColumnPositionID(), value.intValue()) + ",");
			}
			System.out.println("}");
			counter++;
		}
		
		Node root = new Node(_nodeCounter);
		_nodeCounter++;
		if (allExamplesPositive(targetAttributes) || attributes.isEmpty()) {
			double l = targetAttributes.get(0, 0);
			String strl = targetAttributes.attrValue(0, (int)l);
			Label label = new Label(strl, l);
//			System.out.println("LABEL SET: " + label.getStrValue());
			root.setLabel(label);
		}
		else {
			Attribute A = findBestAttribute(examples, targetAttributes, attributes);
			root.setAttribute(A);
			for (double value : A.getValues()) {
				Matrix[] examples_vi = tableManager.getTrimmedMatrices(A.getColumnPositionID(), (int)value, examples, targetAttributes);
				if (examples_vi[0].rows() == 0) {
					Node leafNode = new Node(_nodeCounter);
					_nodeCounter++;
					double mcv = targetAttributes.mostCommonValue(0);
					Label label = new Label(targetAttributes.attrValue(0, (int)mcv), (int)mcv, A.getColumnPositionID(), (int) value);
//					System.out.println("LABEL SET: " + label.getStrValue());
					leafNode.setLabel(label);
					root.addBranch(value, leafNode);
				}
				else {
					attributes.remove(A);
					root.addBranch(value, runID3(examples_vi[0], examples_vi[1], attributes));
					attributes.add(A);
				}
			}
		}
		return root;
	}
	
	private boolean allExamplesPositive(Matrix targetAttribute) {
		return targetAttribute.columnMax(0) == targetAttribute.columnMin(0);
	}
	
	// algorithm for finding best attribute is based on Gain (S, A)
	private Attribute findBestAttribute (Matrix examples, Matrix targetAttributes, LinkedHashSet<Attribute> attributes) {
		double bestGain = -10;
		int bestAttribute = -10;
		for (Attribute attribute : attributes) {
			int attributeColPos = attribute.getColumnPositionID();
			double gain = calculateGain(attributeColPos, examples, targetAttributes, attributes);
			/*double splitInformation = splitInformation(attributeColPos, examples, targetAttributes, attributes);
			if (splitInformation > 0) {
				gain = gain / splitInformation;
			}*/
			if (gain >= bestGain) {
				bestGain = gain;
				bestAttribute = attributeColPos;
			}
		}
		for (Attribute attribute : attributes) {
			if (attribute.getColumnPositionID() == bestAttribute)
				return attribute;
		}
		return null;
	}
	
	private double splitInformation(int attribute, Matrix examples, Matrix targetAttributes, LinkedHashSet<Attribute> attributes) {
		ArrayList<int[]> valueOccurrences = tableManager.getValueOccurrences(attribute, examples, targetAttributes);
		int[] targetOccurrences = tableManager.getTargetAttributeOccurrences(targetAttributes);
		double targetTotalOccurrences = 0;
		for (int i = 0; i < targetOccurrences.length; i++) {
			targetTotalOccurrences += targetOccurrences[i];
		}
		double[] valueSummation = new double[valueOccurrences.size()];
		for (int i = 0; i < valueOccurrences.size(); i++) {
			for (int j = 0; j < targetOccurrences.length; j++) {
				valueSummation[i] += valueOccurrences.get(i)[j];
			}
		}
		double value = 0;
		for (int i = 0; i < valueOccurrences.size(); i++) {
			value += (-1) * calculateEntropyForSplit(valueOccurrences.get(i), targetTotalOccurrences);
		}
		return value;
	}
	
	private double calculateEntropyForSplit (int[] occurrences, double targetTotalOccurrences) {
		double totalOccurrences = 0;
		int counter = 0;
		for (int i = 0; i < occurrences.length; i ++) {
			if ((double)occurrences[i] == 0)
				counter++;
			totalOccurrences += (double)occurrences[i];
		}
		if (counter == occurrences.length - 1)
			return 0;
		if (totalOccurrences == 0)
			return 0;
		double entropy = 0;
		for (int i = 0; i < occurrences.length; i++) {
			if (occurrences[i] != 0)
				entropy += (-1) * (totalOccurrences/targetTotalOccurrences) * (Math.log10(totalOccurrences/targetTotalOccurrences) / Math.log10(2)); 
		}
		return entropy;
	}
	
	private double calculateGain (int attribute, Matrix examples, Matrix targetAttributes, LinkedHashSet<Attribute> attributes) {
		ArrayList<int[]> valueOccurrences = tableManager.getValueOccurrences(attribute, examples, targetAttributes);
		int[] targetOccurrences = tableManager.getTargetAttributeOccurrences(targetAttributes);
		double totalOccurrences = 0;
		for (int i = 0; i < targetOccurrences.length; i++) {
			totalOccurrences += targetOccurrences[i];
		}
		double[] valueSummation = new double[valueOccurrences.size()];
		for (int i = 0; i < valueOccurrences.size(); i++) {
			for (int j = 0; j < targetOccurrences.length; j++) {
				valueSummation[i] += valueOccurrences.get(i)[j];
			}
		}
		double value = 0;
		for (int i = 0; i < valueOccurrences.size(); i++) {
			value += (-1) * (valueSummation[i] / totalOccurrences) * calculateEntropy(valueOccurrences.get(i));
		}
		double gain = calculateEntropy(targetOccurrences);
		gain += value;
		return gain;
	}
	
	private double calculateEntropy (int[] occurrences) {
		double totalOccurrences = 0;
		int counter = 0;
		for (int i = 0; i < occurrences.length; i ++) {
			if ((double)occurrences[i] == 0)
				counter++;
			totalOccurrences += (double)occurrences[i];
		}
		if (counter == occurrences.length - 1)
			return 0;
		if (totalOccurrences == 0)
			return 0;
		double entropy = 0;
		for (int i = 0; i < occurrences.length; i++) {
			if (occurrences[i] != 0)
				entropy += (-1) * (occurrences[i]/totalOccurrences) * (Math.log10(occurrences[i]/totalOccurrences) / Math.log10(2)); 
		}
		return entropy;
	}
}
