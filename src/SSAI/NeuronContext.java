package SSAI;

public class NeuronContext extends Neuron {
    public NeuronContext(String id, NeuralNetwork neuralNetwork) {
        super(ID_CONTEXT+"."+id, neuralNetwork);
    }
}
