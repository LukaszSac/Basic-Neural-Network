package neuralNetwork.layers;

import cern.colt.matrix.DoubleMatrix2D;
import neuralNetwork.nodes.NeuronNode;

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

    public double getBias(int neuronIndex)
    {
        return ((NeuronNode) nodes.get(neuronIndex)).getBias();
    }
    public DoubleMatrix2D getWeights(int neuronIndex)
    {
        return ((NeuronNode) nodes.get(neuronIndex)).getWeightVector();
    }
}
