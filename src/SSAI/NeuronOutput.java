package SSAI;

public class NeuronOutput extends Neuron {
    public NeuronOutput(String id, int ideal, NeuralNetwork neuralNetwork) {
        super(ID_OUTPUT+"."+id, neuralNetwork);
        this.ideal = ideal;
    }
}
