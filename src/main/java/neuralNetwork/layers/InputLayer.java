package neuralNetwork.layers;

import cern.colt.matrix.DoubleMatrix2D;
import neuralNetwork.nodes.InputNode;
import neuralNetwork.nodes.Node;

public class InputLayer extends Layer
{

    public InputLayer(int nodesCount)
    {
        super();
        for(int i=0;i<nodesCount;i++)
            addNeuron(new InputNode());
    }

    public void setInput(DoubleMatrix2D input)
    {
        int index = 0;
        for(Node node : nodes)
            ((InputNode)node).setInput(input.getQuick(0,index++));
    }

    public void print()
    {
        int index = 0;
        for(Node node : nodes)
        {
            System.out.println(index++ + ". node value: " + ((InputNode)node).getValue());
        }
    }
}
