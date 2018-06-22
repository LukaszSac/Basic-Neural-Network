package neuralNetwork.nodes;

public class InputNode implements Node
{
    private double value;
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
}
