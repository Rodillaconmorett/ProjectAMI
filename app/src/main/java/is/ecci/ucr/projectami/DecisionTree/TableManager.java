package is.ecci.ucr.projectami.DecisionTree;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TableManager {

	private Matrix _examples;
	private Matrix _targetAttributes;
	private LinkedHashSet<Attribute> _attributes;
	
	public TableManager (Matrix examples, Matrix targetAttributes, LinkedHashSet<Attribute> attributes) {
		_examples = examples;
		_targetAttributes = targetAttributes;
		_attributes = attributes;
	}
	
	public LinkedHashSet<Attribute> get_attributes() {
		return _attributes;
	}

	public void set_attributes(LinkedHashSet<Attribute> attributes) {
		_attributes = attributes;
	}

	public Matrix get_examples() {
		return _examples;
	}

	public void set_examples(Matrix examples) {
		_examples = examples;
	}

	public Matrix get_targetAttributes() {
		return _targetAttributes;
	}

	public void set_targetAttributes(Matrix targetAttributes) {
		_targetAttributes = targetAttributes;
	}

	public int[] getTargetAttributeOccurrences(Matrix targetAttributes) {
		int cwise = targetAttributes.valueCount(0);
		int[] classificationCount = new int[cwise];
		for (int i = 0; i < cwise; i++) {
			classificationCount[i] = 0;
			for (int j = 0; j < targetAttributes.rows(); j++) {
				if ((double) i == targetAttributes.get(j, 0))
					classificationCount[i]++;
			}
		}
		return classificationCount;
	}
	
	public ArrayList<int[]> getValueOccurrences (int attribute, Matrix examples, Matrix targetAttributes) {
		int cwise = targetAttributes.valueCount(0);
		int valuewise = examples.valueCount(attribute);
		ArrayList<int[]> valueOccurrences = new ArrayList<int[]>();
		for (int i = 0; i < valuewise; i++) {
			int[] valueOccurrence = new int[cwise];
			for (int j = 0; j < examples.rows(); j++) {
				int targetValue = (int)targetAttributes.get(j, 0);
				if (examples.get(j, attribute) == Double.MAX_VALUE) { // missing value
//					System.out.println(examples.get(j, attribute));
					examples.set(j, attribute, examples.mostCommonValueMissingData(attribute, targetAttributes.get(j, 0)));					
				}
				else if ( i == (int)(examples.get(j, attribute)) )
					valueOccurrence[targetValue]++;
			}
			valueOccurrences.add(valueOccurrence);
		}
		return valueOccurrences;
	}
	
	public Matrix[] getTrimmedMatrices(int attribute, int value, Matrix examples, Matrix targetAttributes) {
		Matrix trimmedExamples = new Matrix(examples, attribute, value, examples.rows(), examples.cols(), null);
		ArrayList<Integer> whichToCopy = trimmedExamples.getWhichRowsToCopy();
		Matrix trimmedTargetAttributes = new Matrix(targetAttributes, 0, -1, targetAttributes.rows(), targetAttributes.cols(), whichToCopy);
		Matrix[] trimmedMatrices = new Matrix[2];
		trimmedMatrices[0] = trimmedExamples;
		trimmedMatrices[1] = trimmedTargetAttributes;
		return trimmedMatrices;
	}
}
