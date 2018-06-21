package neuralNetwork;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class Neuron
{
    private double bias;
    private DoubleMatrix1D weightVector;


    private void createWeightVector(int weightsNumber)
    {
        weightVector = new DenseDoubleMatrix1D(weightsNumber);
    }

    private void init(int weightsNumber)
    {
        createWeightVector(weightsNumber);
        bias = 0.0;
    }

    public Neuron()
    {
        init(0);
    }

    public Neuron(int weightNumber)
    {
        init(weightNumber);
    }

    public void setWeightNumber(int weightNumber)
    {
        createWeightVector(weightNumber);
    }

    public void changeBias(double bias)
    {
        this.bias += bias;
    }

    public void changeWeights(DenseDoubleMatrix1D weightDifferences)
    {
        weightVector.assign(weightDifferences);
    }

    public double getBias() {
        return bias;
    }

    public DoubleMatrix1D getWeightVector() {
        return weightVector;
    }
}
