package neuralNetwork.layers;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.nodes.NeuronNode;
import neuralNetwork.nodes.Node;

import java.util.ArrayList;

public class HiddenLayer extends Layer
{

    public HiddenLayer(int inputCount, int nodeCount)
    {
        super();
        for(int j=0;j<nodeCount;j++)
        {
            nodes.add(new NeuronNode(inputCount));
        }
    }

    public int getInputCount()
    {
        return ((NeuronNode)nodes.get(0)).getInputCount();
    }

    protected double getBias(int neuronIndex)
    {
        return ((NeuronNode) nodes.get(neuronIndex)).getBias();
    }

    public ArrayList<Node> getNeurons()
    {
        return nodes;
    }

    public DoubleMatrix2D getBiases()
    {
        DoubleMatrix2D output = new DenseDoubleMatrix2D(1,nodesCount());
        for(int i=0;i<nodesCount();i++)
        {
            output.setQuick(0,i,getBias(i));
        }
        return output;
    }


    public DoubleMatrix2D getWeights(int neuronIndex)
    {
        return ((NeuronNode) nodes.get(neuronIndex)).getWeightVector();
    }
}
