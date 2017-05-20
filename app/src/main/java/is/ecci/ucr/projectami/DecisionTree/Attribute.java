package is.ecci.ucr.projectami.DecisionTree;

import java.util.LinkedHashSet;

public class Attribute {
	
	private String _name;
	private LinkedHashSet<Double> _values;
	private int _columnPosition;
	
	public Attribute(String name, LinkedHashSet<Double> values, int columnPosition) {
		_name = name;
		_values = values;
		_columnPosition = columnPosition;
	}
	
	public Attribute() {
		_name = "Leaf Node";
		_values = new LinkedHashSet<Double>();
		_columnPosition = -2;
	}

	public void setName(String name) {
		_name = name;
	}

	public LinkedHashSet<Double> getValues() {
		return _values;
	}

	public void setValues(LinkedHashSet<Double> values) {
		_values = values;
	}

	public int getColumnPositionID() {
		return _columnPosition;
	}

	public void setColumnPosition(int columnPosition) {
		_columnPosition = columnPosition;
	}

	public String getName() {
		return _name;
	}
	
	public int getNumberOfValues() {
		return _values.size();
	}
	
	public boolean equals(Attribute other) {
		if (this.getColumnPositionID() == other.getColumnPositionID())
			return true;
		return false;
	}
}
