package neuralNetwork.nodes;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;

public class NeuronNode implements Node
{
    private double bias;
    private DoubleMatrix2D weightVector;
    private int inputCount;

    private void createWeightVector(int inputCount)
    {
        weightVector = new DenseDoubleMatrix2D(1,inputCount);
        this.inputCount = inputCount;
    }

    private void init(int inputCount)
    {
        createWeightVector(inputCount);
        for(int i=0;i<inputCount;i++)
            weightVector.setQuick(0, i, 1);
        bias = 0.0;
    }

    public NeuronNode()
    {
        init(0);
    }

    public NeuronNode(int inputCount)
    {
        init(inputCount);
    }

    public void setWeightNumber(int weightNumber)
    {
        createWeightVector(weightNumber);
    }

    public void changeBias(double bias)
    {
        this.bias += bias;
    }

    public void changeWeights(DenseDoubleMatrix2D weightDifferences)
    {

    }

    public double getBias() {
        return bias;
    }

    public DoubleMatrix2D getWeightVector() {
        return weightVector;
    }

    public int getInputCount() {
        return inputCount;
    }
}
