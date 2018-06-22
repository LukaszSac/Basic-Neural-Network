import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.NeuralNetwork;

public class Main
{

    static public void main(String[] str)
    {
        System.out.println("Hi world!");
        NeuralNetwork nn = new NeuralNetwork("src/main/structure.txt");
        double[][] inputValues = {{1.0,2.0,3.0}};
        DoubleMatrix2D input = new DenseDoubleMatrix2D(inputValues);
        System.out.println("Output: " + nn.think(input));
    }

}
