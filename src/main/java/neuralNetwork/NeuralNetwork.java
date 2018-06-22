package neuralNetwork;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import neuralNetwork.layers.HiddenLayer;
import neuralNetwork.layers.InputLayer;
import neuralNetwork.layers.Layer;
import neuralNetwork.layers.OutputLayer;
import neuralNetwork.nodes.NeuronNode;
import neuralNetwork.nodes.Node;

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
    private double derSigmoid(double x) { return sigmoid(x)*(1-sigmoid(x));}

    private void backPropagation(DoubleMatrix2D wanted)
    {
        HiddenLayer prevLayer = layers.get(layers.size()-2);
        int index = 0;
        for(Node node : outputLayer.getNeurons())
        {
            int inputCount = node.getInputCount();
            DoubleMatrix2D weightChanges = new DenseDoubleMatrix2D(1,inputCount);
            DoubleMatrix2D biasChanges = new DenseDoubleMatrix2D(1,inputCount);
            for(int i=0;i<inputCount;i++)
            {
                Node correspondingNode = prevLayer.getNode(i);
                double weightChange = correspondingNode.getLastActivation();
                double activationChange = ((NeuronNode)node).getWeightVector().get(0,i);
                double biasChange = derSigmoid(correspondingNode.getLastSum());
                biasChange*=2*(node.getLastActivation() - wanted.get(0,index));
                weightChange*=biasChange;
                weightChanges.setQuick(0,i,weightChange);
                biasChanges.set(0,i,biasChange);
                correspondingNode.addActivationChange(activationChange*biasChange);
            }
            index++;
            System.out.println("Last layer weight changes: " + weightChanges);
            System.out.println("Last layer bias changes: " + biasChanges);
        }
        for(Node node : prevLayer.getNeurons())
        {
            node.calculateActiovationChange();
        }
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

    private void loadDataFromFile(String path, ArrayList data)
    {
        try {
            Scanner in = new Scanner(new FileReader(path));
            while (in.hasNext()) {
                String[] inputs = in.nextLine().split("\\s");
                DoubleMatrix2D oneInput = new DenseDoubleMatrix2D(1, inputs.length);
                for (String variable : inputs)
                    for (int i = 0; i < inputs.length; i++)
                        oneInput.setQuick(0, i, Integer.parseInt(variable));
                data.add(oneInput);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initLearningData(String structurePath)
    {
        try {
            Scanner in = new Scanner(new FileReader(structurePath));
            String input;
            input = in.nextLine();
            int inputNodeCount = Integer.parseInt(input.split("=")[1]);
            inputLayer = new InputLayer(inputNodeCount);
            input = in.nextLine();
            int outputNodeCount = Integer.parseInt(input.split("=")[1]);
            input = in.nextLine();
            int hiddenLayerCount = Integer.parseInt(input.split("=")[1]);
            int prev = inputNodeCount;
            for(int i=0;i<hiddenLayerCount;i++) {
                int newPrev;
                input = in.nextLine();
                layers.add(new HiddenLayer(prev, newPrev = Integer.parseInt(input)));
                System.out.println("Old prev: " + prev + ", New prev: " + newPrev);
                prev = newPrev;
            }
            outputLayer = new OutputLayer(prev,outputNodeCount);
            layers.add(outputLayer);
            in.close();
            learningInputs = new ArrayList<DoubleMatrix2D>();
            loadDataFromFile("src/main/resources/learninginput.txt", learningInputs);
            learningOutputs = new ArrayList<DoubleMatrix2D>();
            loadDataFromFile("\"src/main/resources/learningoutput.txt", learningOutputs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public NeuralNetwork(String structurePath)
    {
        initLearningData(structurePath);
        int index = 0;
        ArrayList <DoubleMatrix2D> gotgot = new ArrayList<DoubleMatrix2D>();
        for(DoubleMatrix2D learningMaterial : learningInputs)
        {
            System.out.println("For input:");
            System.out.println(learningMaterial);
            System.out.println("Output:");
            DoubleMatrix2D output = think(learningMaterial);
            gotgot.add(output);
            System.out.println(output);
            System.out.println("Wanted:");
            System.out.println(learningOutputs.get(index++));
            System.out.println("Weight changes: ");
            backPropagation(learningMaterial);
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
        layer.setLastSums(multResult);
        return multResult.assign(sigm);
    }

    public DoubleMatrix2D think(DoubleMatrix2D input)
    {
        DoubleMatrix2D output = input;
        inputLayer.setLastActivations(input);
        int index = 0;
        for(Layer layer : layers)
        {
            output = oneIteration(output,index++);
            layer.setLastActivations(output);
        }
        return output;
    }

}
