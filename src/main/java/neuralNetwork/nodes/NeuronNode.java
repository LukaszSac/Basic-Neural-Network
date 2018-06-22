package neuralNetwork.nodes;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;

public class NeuronNode implements Node
{
    private double bias;
    private DoubleMatrix2D weightVector;
    private int inputCount;
    private double lastActivation;
    private double lastSum;
    private static DoubleDoubleFunction plus = new DoubleDoubleFunction() {
        public double apply(double a, double b) { return a+b; }
    };
    private DoubleMatrix2D weightChange;
    private int weightChangeCount = 0;

    private double biasChange;
    private int biasChangeCount = 0;

    private int activationChangeCount = 0;
    private double activationChange = 0;

    private void createWeightVector(int inputCount)
    {
        weightVector = new DenseDoubleMatrix2D(1,inputCount);
        weightChange = new DenseDoubleMatrix2D(1,inputCount);
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

    public void resetLearningValues() {
        lastActivation = 0;
        lastSum = 0;
        activationChangeCount = 0;
        activationChange = 0;
        weightChange = new DenseDoubleMatrix2D(1,inputCount);
        weightChangeCount = 0;
        biasChange = 0;
        biasChangeCount = 0;
    }

    public void addWeightChanges(DoubleMatrix2D changes) {
        weightChange = weightChange.assign(changes,plus);
        weightChangeCount ++;
    }

    public void applyWeightChanges() {
        weightChange.assign(new DoubleFunction() {
            public double apply(double v) {
                return v/weightChangeCount;
            }
        });
        weightVector.assign(weightChange,plus);
    }

    public void addBiasChange(double value) {
        biasChange +=value;
        biasChangeCount ++;
    }

    public void applyBiasChanges() {
        biasChange/= biasChangeCount;
    }

    public void setLastActivation(double value) {
        lastActivation = value;
    }

    public double getLastActivation() {
        return lastActivation;
    }

    public void setLastSum(double value) {
        lastSum = value;
    }

    public double getLastSum() {
        return lastSum;
    }

    public void addActivationChange(double value) {
        activationChange +=value;
        activationChangeCount++;
    }

    public void calculateActiovationChange() {
        activationChange/= activationChangeCount;
    }

    public double getActivationChange() {
        return activationChange;
    }
}
