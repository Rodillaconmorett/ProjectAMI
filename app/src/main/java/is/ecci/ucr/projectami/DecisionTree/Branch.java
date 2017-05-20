package is.ecci.ucr.projectami.DecisionTree;

public class Branch<V, N> {
	private V _value;
	private N _node;
	
	public Branch (V value, N node) {
		this._value = value;
		this._node = node;
	}

	public V get_value() {
		return _value;
	}

	public N get_node() {
		return _node;
	}
}
