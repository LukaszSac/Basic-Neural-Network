package neuralNetwork.layers;

import neuralNetwork.nodes.InputNode;

public class InputLayer extends Layer
{

    public InputLayer(int nodesCount)
    {
        super();
        for(int i=0;i<nodesCount;i++)
            addNeuron(new InputNode());
    }
}
