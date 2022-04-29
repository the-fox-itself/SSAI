package SSAI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawPanel extends JPanel {
    NeuralNetwork neuralNetwork = AIMain.neuralNetwork;

    HashMap<String, Integer[]> listOfCoordinates = new HashMap<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 100;
        int y;
        int gap = 40;
        int radius = 10;

        //Размещение нейронов
        for (int l = 1; l <= neuralNetwork.numOfLayers; l++) {
            ArrayList<Neuron> listOfNeutrons = new ArrayList<>();
            for (Map.Entry<String, Neuron> neuronSet : neuralNetwork.listOfNeurons.entrySet()) {
                Neuron neuron = neuronSet.getValue();
                if (neuron.layer == l) {
                    listOfNeutrons.add(neuron);
                }
            }
            if (listOfNeutrons.size() % 2 == 0) {
                y = AIMain.frame.getHeight()/2 - gap/2 - (listOfNeutrons.size()-2)/2*gap;
            } else {
                y = AIMain.frame.getHeight()/2 - (listOfNeutrons.size()-1)/2*gap;
            }
            for (Neuron neuron : listOfNeutrons) {
//                if (neuron.id.split("\\.")[0].equals(Neuron.ID_HIDDEN)) {
//                    g.setColor(Color.black);
//                } else if (neuron.id.split("\\.")[0].equals(Neuron.ID_INPUT)) {
//                    g.setColor(Color.yellow);
//                } else if (neuron.id.split("\\.")[0].equals(Neuron.ID_OUTPUT)) {
//                    g.setColor(Color.red);
//                } else if (neuron.id.split("\\.")[0].equals(Neuron.ID_CONTEXT)) {
//                    g.setColor(Color.magenta);
//                } else if (neuron.id.split("\\.")[0].equals(Neuron.ID_DISPLACE)) {
//                    g.setColor(Color.CYAN);
//                }
                g.fillOval(x, y-radius/2, radius, radius);
                listOfCoordinates.put(neuron.id, new Integer[]{x, y-radius/2});
                y += gap;
            }
            x += gap*2;
        }

        g.setColor(Color.black);
        for (Map.Entry<String, Synapse> synapseSet : neuralNetwork.listOfSynapses.entrySet()) {
            g.drawLine(listOfCoordinates.get(synapseSet.getValue().outputNeuron.id)[0]+radius/2,
                    listOfCoordinates.get(synapseSet.getValue().outputNeuron.id)[1]+radius/2,
                    listOfCoordinates.get(synapseSet.getValue().inputNeuron.id)[0]+radius/2,
                    listOfCoordinates.get(synapseSet.getValue().inputNeuron.id)[1]+radius/2);
        }

        x = 100;
        y = 20;
        for (int l = 1; l <= neuralNetwork.numOfLayers-1; l++) {
            for (Map.Entry<String, Synapse> synapseSet : neuralNetwork.listOfSynapses.entrySet()) {
                Synapse synapse = synapseSet.getValue();
                if (synapse.outputNeuron.layer == l) {
                    g.drawString(synapse.getWeigh() + "", x+(Integer.parseInt(synapse.outputNeuron.id.split("\\.")[1])-1)
                            * (gap * 2)+(Integer.parseInt(synapse.outputNeuron.id.split("\\.")[1])-1)*radius/2, y);
                    y += 20;
                }
            }
            y = 20;
        }

        for (Map.Entry<String, Neuron> neuronSet : neuralNetwork.listOfNeurons.entrySet()) {
            Neuron neuron = neuronSet.getValue();
            if (neuron.id.split("\\.")[0].equals(Neuron.ID_OUTPUT)) {
                g.drawString(String.valueOf(neuron.output), 10, AIMain.frame.getHeight()-60);
                break;
            }
        }
    }
}
