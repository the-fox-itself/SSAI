package SSAI;

public class NeuronInput extends Neuron {
    public NeuronInput(String id, int input, NeuralNetwork neuralNetwork) {
        super(ID_INPUT+"."+id, input, input, neuralNetwork);
    }
}
