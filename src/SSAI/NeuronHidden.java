package SSAI;

public class NeuronHidden extends Neuron {
    public NeuronHidden(String id, NeuralNetwork neuralNetwork) {
        super(ID_HIDDEN+"."+id, neuralNetwork);
    }
}
