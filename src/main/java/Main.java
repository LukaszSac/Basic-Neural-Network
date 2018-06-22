import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.NeuralNetwork;

public class Main
{

    static public void main(String[] str)
    {
        System.out.println("Hi world!");
        NeuralNetwork nn = new NeuralNetwork("src/main/resources/structure.txt");
    }

}
