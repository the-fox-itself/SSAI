package SSAI;

import java.util.HashMap;
import java.util.Map;

public abstract class Neuron {
    public Neuron(String id, NeuralNetwork neuralNetwork) {
        this.id = id;
        this.layer = Integer.parseInt(id.split("\\.")[1]);
        System.out.println(layer);
        neuralNetwork.listOfNeurons.put(id, this);
    }
    public Neuron(String id, int input, int output, NeuralNetwork neuralNetwork) {
        this.id = id;
        this.layer = Integer.parseInt(id.split("\\.")[1]);
        System.out.println(layer);
        this.input = input;
        this.output = output;
        neuralNetwork.listOfNeurons.put(id, this);
    }

    public String id;
    public static final String ID_INPUT = "input";
    public static final String ID_HIDDEN = "hidden";
    public static final String ID_OUTPUT = "output";
    public static final String ID_DISPLACE = "displace";
    public static final String ID_CONTEXT = "context";
    public int layer;
    public double input;
    public double output;
    public double ideal;
    public double delta;

    public void makeInput(NeuralNetwork neuralNetwork) {
        HashMap<Double, Double> values = new HashMap<>();
        for (Map.Entry<String, Synapse> synapseSet : neuralNetwork.listOfSynapses.entrySet()) {
            if (synapseSet.getValue().inputNeuron.id.equals(this.id)) {
                values.put(synapseSet.getValue().outputNeuron.output, synapseSet.getValue().getWeigh());
            }
        }
        this.input = AIMain.neuronInput(values);
    }
    public void makeOutput() {
        this.output = AIMain.neuronOutputSigmoid(this.input);
    }
}