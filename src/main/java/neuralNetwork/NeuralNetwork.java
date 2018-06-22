package neuralNetwork;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.layers.HiddenLayer;
import neuralNetwork.layers.InputLayer;
import neuralNetwork.layers.OutputLayer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NeuralNetwork
{
    private List<HiddenLayer> layers = new ArrayList<HiddenLayer>();
    private InputLayer inputLayer;
    private OutputLayer outputLayer;
    private ArrayList<DoubleMatrix2D> learningInputs;
    private ArrayList<DoubleMatrix2D> learningOutputs;

    private double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }

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
        try {
            Scanner in = new Scanner(new FileReader(structurePath));
            String input;
            input = in.nextLine();
            inputLayer = new InputLayer(Integer.parseInt(input.split("=")[1]));
            input = in.nextLine();
            int outputNodeCount = Integer.parseInt(input.split("=")[1]);
            input = in.nextLine();
            int hiddenLayerCount = Integer.parseInt(input.split("=")[1]);
            int prev = inputLayer.nodesCount();
            for(int i=0;i<hiddenLayerCount;i++) {
                int newPrev;
                layers.add(new HiddenLayer(prev, newPrev = Integer.parseInt(input.split("=")[1])));
                prev = newPrev;
            }
            outputLayer = new OutputLayer(prev,outputNodeCount);
            layers.add(outputLayer);
            in.close();
            in = new Scanner(new FileReader("src/main/resources/learninginput.txt"));
            learningInputs = new ArrayList<DoubleMatrix2D>();
            while(in.hasNext())
            {
                String[] inputs = in.nextLine().split("\\s");
                DoubleMatrix2D oneInput = new DenseDoubleMatrix2D(1,inputs.length);
                for(String variable : inputs)
                    for (int i = 0; i < inputs.length; i++)
                        oneInput.setQuick(0, i, Integer.parseInt(variable));
                learningInputs.add(oneInput);
            }
            in.close();
            in = new Scanner(new FileReader("src/main/resources/learningoutput.txt"));
            learningOutputs = new ArrayList<DoubleMatrix2D>();
            while(in.hasNext())
            {
                String[] inputs = in.nextLine().split("\\s");
                DoubleMatrix2D oneInput = new DenseDoubleMatrix2D(1,inputs.length);
                for(String variable : inputs)
                    for (int i = 0; i < inputs.length; i++)
                        oneInput.setQuick(0, i, Integer.parseInt(variable));
                learningOutputs.add(oneInput);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int index = 0;
        for(DoubleMatrix2D learningMaterial : learningInputs)
        {
            System.out.println("For input:");
            System.out.println(learningMaterial);
            System.out.println("Output:");
            System.out.println(think(learningMaterial));
            System.out.println("Wanted:");
            System.out.println(learningOutputs.get(index++));
        }
    }


    private DoubleMatrix2D oneIteration(DoubleMatrix2D prevOutput, int layerIndex)
    {
        HiddenLayer layer = layers.get(layerIndex);
        DoubleMatrix2D rightMatrix = getLayersWeights(layer);
        DoubleMatrix2D multResult = multLayers(prevOutput,rightMatrix);
        DoubleDoubleFunction plus = new DoubleDoubleFunction() {
            public double apply(double a, double b) { return a+b; }
        };
        multResult.assign(layer.getBiases(),plus);
        DoubleFunction sigm = new DoubleFunction() {
            public double apply(double v) {
                return sigmoid(v);
            }
        };
        return multResult.assign(sigm);
    }

    public DoubleMatrix2D think(DoubleMatrix2D input)
    {
        DoubleMatrix2D output = input;
        for(int i=0;i<layers.size();i++)
            output = oneIteration(output,i);
        return output;
    }

}
