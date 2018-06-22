package neuralNetwork.layers;

import neuralNetwork.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Layer
{
    protected List<Node> nodes;

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
}
