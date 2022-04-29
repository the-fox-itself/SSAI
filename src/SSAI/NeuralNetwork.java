package SSAI;

import java.util.HashMap;
import java.util.Map;

public class NeuralNetwork {
    public NeuralNetwork(int numOfLayers) {
        this.numOfLayers = numOfLayers;
        System.out.println("\\ Создана новая нейронная система");
    }

    public void makeSynapsesWithRandWeigh() {
        for (int l = 1; l <= numOfLayers; l++) {
            for (Map.Entry<String, Neuron> neuronSet : listOfNeurons.entrySet()) {
                Neuron neuron = neuronSet.getValue();
                if (neuron.layer == l) {
                    for (Map.Entry<String, Neuron> neuronSet1 : listOfNeurons.entrySet()) {
                        Neuron neuron1 = neuronSet1.getValue();
                        if (neuron1.layer == l+1 && !neuron1.id.split("\\.")[0].equals(Neuron.ID_DISPLACE)) {
                            new Synapse(neuron.id+":"+neuron1.id, neuron, neuron1, this);
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Synapse> synapseSet : listOfSynapses.entrySet()) {
            Synapse synapse = synapseSet.getValue();
            if (synapse.getWeigh() == -1) {
                synapse.setWeigh(Math.random());
            }
        }
        System.out.println("\\ Синопсы со случайными весами созданы");
    }

    public int numOfLayers;
    HashMap<String, Neuron> listOfNeurons = new HashMap<>();
    HashMap<String, Synapse> listOfSynapses = new HashMap<>();
}
