package neuralNetwork.nodes;

import cern.colt.matrix.DoubleMatrix2D;

public interface Node {
    void setLastActivation(double value);
    double getLastActivation();
    void setLastSum(double value);
    double getLastSum();
    void addActivationChange(double value);
    void calculateActiovationChange();
    double getActivationChange();
    int getInputCount();
    void resetLearningValues();
    void addWeightChanges(DoubleMatrix2D changes);
    void applyWeightChanges();
    void addBiasChange(double value);
    void applyBiasChanges();
}
