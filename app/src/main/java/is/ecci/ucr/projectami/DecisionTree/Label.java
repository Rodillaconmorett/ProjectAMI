package is.ecci.ucr.projectami.DecisionTree;

public class Label {
	
	private String _strvalue;//El nombre del nodo
	private double _value; // El nombre de la opci�n padre de la que viene
	int _attriFatherID;	// El atributo padre
	int _optionTaken;   //La opci�n tomada para llegar a este nodo
	
	public Label(String strvalue, double value) {
		_strvalue = strvalue;
		_value = value;
		_attriFatherID = -1;
		_optionTaken = -1;
	}
	public Label(String strvalue, double value, int attriFatherID, int optionTaken) {
		_strvalue = strvalue;
		_value = value;
		_attriFatherID = attriFatherID;
		_optionTaken = optionTaken;
	}
	
	public Label() {	// start as not a leaf node and modify after...
		_strvalue = "Not a leaf node";
		_value = -1;
	}
	
	public int getAttriFatherId(){
		return _attriFatherID;
	}
	
	public int getOptionTaken(){
		return _optionTaken;
	}
	public String getStrValue() {
		return _strvalue;
	}
	
	public double getValue() {
		return _value;
	}
}
