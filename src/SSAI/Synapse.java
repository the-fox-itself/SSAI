package SSAI;

public class Synapse {
    public Synapse(String id, Neuron outputNeuron, Neuron inputNeuron, NeuralNetwork neuralNetwork) {
        this.id = id;
        this.outputNeuron = outputNeuron;
        this.inputNeuron = inputNeuron;
        this.weigh = weigh;
        if (!neuralNetwork.listOfSynapses.containsValue(this)) {
            neuralNetwork.listOfSynapses.put(id, this);
        }
    }

    public String id;
    public Neuron outputNeuron;
    public Neuron inputNeuron;
    private double weigh = -1;
    public double gradient;
    public double previousWeighChange = 0;

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }
}
