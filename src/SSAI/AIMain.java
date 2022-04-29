package SSAI;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static Libraries.Methods.getFrame;
import static Libraries.Methods.visTrue;

public class AIMain {
    public static NeuralNetwork neuralNetwork = new NeuralNetwork(5);
    public static double E = 0.7;
    public static double A = 0.3;
    public static JFrame frame = getFrame("Neutral network", null, 900, 700, new BorderLayout(), null, true);

    public static void main(String[] args) {
        DrawPanel drawPanel = new DrawPanel();
        frame.add(drawPanel, BorderLayout.CENTER);
        visTrue(frame);
        new GameLoop().start();

        int input1 = 1;
        int input2 = 0;
        int idealResult = 1; // 1 and 0: 1

        System.out.println("\\ Входные данные: " + input1 + "; " + input2);
        System.out.println("\\ Идеальный результат: " + idealResult);

        new NeuronInput("1.1", input1, neuralNetwork);
        new NeuronInput("1.2", input2, neuralNetwork);
        new NeuronInput("1.3", input2, neuralNetwork);
        new NeuronInput("1.4", input2, neuralNetwork);
        new NeuronInput("1.5", input2, neuralNetwork);
        new NeuronInput("1.6", input2, neuralNetwork);
        new NeuronInput("1.7", input2, neuralNetwork);
        new NeuronInput("1.8", input2, neuralNetwork);
        new NeuronInput("1.9", input2, neuralNetwork);
        new NeuronInput("1.10", input2, neuralNetwork);
        new NeuronInput("1.11", input2, neuralNetwork);
        new NeuronInput("1.12", input2, neuralNetwork);
        new NeuronInput("1.13", input2, neuralNetwork);
        new NeuronInput("1.14", input2, neuralNetwork);
        new NeuronInput("1.15", input2, neuralNetwork);
        new NeuronInput("1.16", input2, neuralNetwork);
        new NeuronInput("1.17", input2, neuralNetwork);
        new NeuronInput("1.18", input2, neuralNetwork);
        new NeuronInput("1.19", input2, neuralNetwork);
        new NeuronInput("1.20", input2, neuralNetwork);
        new NeuronInput("1.21", input2, neuralNetwork);
        new NeuronInput("1.22", input2, neuralNetwork);
        new NeuronInput("1.23", input2, neuralNetwork);

        new NeuronHidden("2.1", neuralNetwork);
        new NeuronHidden("2.2", neuralNetwork);
        new NeuronHidden("2.3", neuralNetwork);
        new NeuronHidden("2.4", neuralNetwork);
        new NeuronHidden("2.5", neuralNetwork);
        new NeuronHidden("2.6", neuralNetwork);
        new NeuronHidden("2.7", neuralNetwork);
        new NeuronHidden("2.8", neuralNetwork);
        new NeuronHidden("2.9", neuralNetwork);
        new NeuronHidden("2.10", neuralNetwork);
        new NeuronHidden("2.11", neuralNetwork);
        new NeuronHidden("2.12", neuralNetwork);
        new NeuronHidden("3.1", neuralNetwork);
        new NeuronHidden("3.2", neuralNetwork);
        new NeuronHidden("3.3", neuralNetwork);
        new NeuronHidden("3.4", neuralNetwork);
        new NeuronHidden("3.5", neuralNetwork);
        new NeuronHidden("4.1", neuralNetwork);
        new NeuronHidden("4.2", neuralNetwork);
        new NeuronHidden("4.3", neuralNetwork);
        new NeuronHidden("4.4", neuralNetwork);
        new NeuronHidden("4.5", neuralNetwork);

        new NeuronOutput("5.1", idealResult, neuralNetwork);
        new NeuronOutput("5.2", idealResult, neuralNetwork);
        System.out.println("\\ Все нейроны созданы");

        neuralNetwork.makeSynapsesWithRandWeigh();

        AIGeneration aiGeneration = new AIGeneration(neuralNetwork, 1);
        aiGeneration.startGeneration();
    }

    public static double neuronInput(HashMap<Double, Double> neuronValues) {
        int returnSum = 0;
        for (Map.Entry<Double, Double> neuronValueSet : neuronValues.entrySet()) {
            Double previousOutput = neuronValueSet.getKey();
            Double weigh = neuronValueSet.getValue();
            returnSum += (previousOutput*weigh);
        }
        return returnSum;
    }

    public static double neuronOutputSigmoid(double x) {
        return 1/(1+Math.pow(Math.E, -1*x));
    }
    public static double neuronOutputHyperbolicTangent(double x) {
        return (Math.pow(Math.E, 2*x)-1)/(Math.pow(Math.E, 2*x)+1);
    }

    public static double errorMSE(HashMap<Double, Double> idealAndActualResults) {
        int returnSum = 0;
        for (Map.Entry<Double, Double> idealAndActualSet : idealAndActualResults.entrySet()) {
            Double idealResult = idealAndActualSet.getKey();
            Double actualResult = idealAndActualSet.getValue();
            returnSum += Math.pow(idealResult - actualResult, 2);
        }
        returnSum /= idealAndActualResults.size();
        return returnSum;
    }
    public static double errorRootMSE(HashMap<Double, Double> idealAndActualResults) {
        return Math.sqrt(errorMSE(idealAndActualResults));
    }
    public static double errorArctan() {
        return -1;
    }

    public static double deltaNeuronOutput(Neuron neuronOutput) {
        return (neuronOutput.ideal - neuronOutput.output) * ((1 - neuronOutput.output) * neuronOutput.output);
    }
    public static double deltaNeuronHidden(Neuron neuronHidden) {
        double sum = 0;
        for (Map.Entry<String, Synapse> synapseSet : neuralNetwork.listOfSynapses.entrySet()) {
            Synapse synapse = synapseSet.getValue();
            if (synapse.outputNeuron.id.equals(neuronHidden.id)) {
                sum += synapse.getWeigh() * synapse.inputNeuron.delta;
            }
        }
        return ((1 - neuronHidden.output) * neuronHidden.output) * sum;
    }
    public static void synapseWeighChange(Neuron neuron) {
        for (Map.Entry<String, Synapse> synapseSet : neuralNetwork.listOfSynapses.entrySet()) {
            Synapse synapse = synapseSet.getValue();
            if (synapse.outputNeuron.id.equals(neuron.id)) {
                synapse.gradient = synapse.inputNeuron.delta * synapse.outputNeuron.output;
                synapse.previousWeighChange = AIMain.E * synapse.gradient + AIMain.A * synapse.previousWeighChange;
                synapse.setWeigh(synapse.getWeigh() + synapse.previousWeighChange);
            }
        }
    }

    public static class GameLoop extends Thread {
        int millisecondsPerUpdate = 1000/30;
        @Override
        public void run() {
            double previous = new Date().getTime();
            double steps = 0;
            while (true) {
                double loopStartTime = new Date().getTime();
                double elapsed = loopStartTime - previous;
                previous = new Date().getTime();
                steps += elapsed;

                handleInput();

                while (steps >= millisecondsPerUpdate) {
                    updateGameStats();
                    steps -= millisecondsPerUpdate;
                }

                frame.repaint();

                double loopSlot = 10;
                double endTime = loopStartTime + loopSlot;
                while (new Date().getTime() < endTime) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
        public void handleInput() {

        }
        public void updateGameStats() {

        }
    }
}
