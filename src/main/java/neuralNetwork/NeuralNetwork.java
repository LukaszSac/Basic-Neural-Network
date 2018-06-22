package neuralNetwork;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.layers.HiddenLayer;
import neuralNetwork.layers.InputLayer;
import neuralNetwork.layers.Layer;
import neuralNetwork.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork
{
    private List layers = new ArrayList<Layer>();
    private InputLayer inputLayer;
    private OutputLayer outputLayer;


    private DoubleMatrix2D multLayers(DoubleMatrix2D leftMatrix, DoubleMatrix2D rightMatrix)
    {
        int columns = rightMatrix.columns();
        DoubleMatrix2D output = new DenseDoubleMatrix2D(1,columns);
        leftMatrix.zMult(rightMatrix,output);
        return output;
    }

    private DoubleMatrix2D getLayersWeights(HiddenLayer layer)
    {
        DoubleMatrix2D output = new DenseDoubleMatrix2D(layer.getInputCount(),layer.nodesCount());
        for(int i=0;i<layer.nodesCount();i++)
        {
            DoubleMatrix2D weights = layer.getWeights(i);
            for(int j=0;j<weights.size();j++)
                output.setQuick(j,i,weights.get(0,j));
        }
        return output;
    }

    public NeuralNetwork(String structurePath)
    {
        inputLayer = new InputLayer(3);
        outputLayer = new OutputLayer(3,2);
    }
}
