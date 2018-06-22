package neuralNetwork.nodes;

import cern.colt.matrix.DoubleMatrix2D;

public class InputNode implements Node
{
    private double value;
    private double lastActivation;
    public InputNode()
    {

    }


    public void setInput(double value)
    {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setLastActivation(double value) {
        lastActivation = value;
    }

    public double getLastActivation() {
        return lastActivation;
    }

    public void setLastSum(double value) {

    }

    public double getLastSum() {
        return 0;
    }

    public void addActivationChange(double value) {

    }

    public void calculateActiovationChange() {

    }

    public double getActivationChange() {
        return 0;
    }

    public int getInputCount() {
        return 0;
    }

    public void resetLearningValues() {

    }

    public void addWeightChanges(DoubleMatrix2D changes) {

    }

    public void applyWeightChanges() {

    }

    public void addBiasChange(double value) {

    }

    public void applyBiasChanges() {

    }

}
