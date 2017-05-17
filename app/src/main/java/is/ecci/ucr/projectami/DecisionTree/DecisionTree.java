package is.ecci.ucr.projectami.DecisionTree;

public class DecisionTree extends SupervisedLearner {

	private Node _root;
	
	@Override
	public void train(Matrix features, Matrix labels) throws Exception {
		ID3 id3 = new ID3();
		_root = id3.buildTree(features, labels);
		String fileName = "output/vowelWithGainRatio.dot";
		_root.dumpDot(features, labels, fileName);
		System.out.println(id3.getNodeCounter());
	}

	@Override
	public void predict(double[] features, double[] labels) throws Exception {
//		System.out.println("Predicting...");
//		for (int i = 0; i < features.length; i++) {
//			System.out.println("	Feature" + i + ": " + features[i]);
//		}
		labels[0] = _root.makeDecision(features, _root.getAttribute().getColumnPositionID());
	}
	
	public void printTree(){
		//System.out.println(_root.);
	}
}
