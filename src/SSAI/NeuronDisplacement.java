package SSAI;

public class NeuronDisplacement extends Neuron {
    public NeuronDisplacement(String id, NeuralNetwork neuralNetwork) {
        super(ID_DISPLACE+"."+id, 1, 1, neuralNetwork);
    }
}
