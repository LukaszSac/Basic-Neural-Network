package neuralNetwork.layers;

import cern.colt.matrix.DoubleMatrix2D;
import neuralNetwork.nodes.NeuronNode;
import neuralNetwork.nodes.Node;

import java.util.ArrayList;

public abstract class Layer
{
    protected ArrayList<Node> nodes;
    private DoubleMatrix2D lastActivations;
    private DoubleMatrix2D lastSums;
    private void init()
    {
        nodes = new ArrayList<Node>();
    }

    public Layer()
    {
        init();
    }

    public int nodesCount()
    {
        return nodes.size();
    }

    public void addNeuron(Node node)
    {
        nodes.add(node);
    }

    public void setLastActivations(DoubleMatrix2D lastActivations) {
        this.lastActivations = lastActivations;
        int index = 0;
        for(Node node : nodes)
            node.setLastActivation(lastActivations.get(0,index++));
    }

    public DoubleMatrix2D getLastActivations() {
        return lastActivations;
    }

    public DoubleMatrix2D getLastSums() {
        return lastSums;
    }

    public void setLastSums(DoubleMatrix2D lastSums) {
        this.lastSums = lastSums;
        int index = 0;
        for(Node node : nodes)
            node.setLastSum(lastSums.get(0,index++));
    }

    public Node getNode(int i)
    {
        return nodes.get(i);
    }
}
