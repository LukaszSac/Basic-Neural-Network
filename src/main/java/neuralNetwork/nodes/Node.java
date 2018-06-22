package neuralNetwork.nodes;

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
}
