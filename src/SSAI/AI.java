package SSAI;

import java.util.ArrayList;
import java.util.Map;

public class AI {
    public AI(AIGeneration aiGeneration) {
        aiGeneration.listOfAI.add(this);
        System.out.println("\\ Создан новый ИИ");
    }

    public boolean isStarted = false;
    public boolean isAlive = false;
    public double result = 0;
    public int reward = -1;

    void start() {
        AIThread aiThread = new AIThread();
        aiThread.start();
        System.out.println("\\ ИИ успешно запущен");
    }

    public class AIThread extends Thread {
        @Override
        public void run() {
            System.out.println("\n");

            NeuralNetwork currentNeuralNetwork = AIMain.neuralNetwork;
            isStarted = true;
            isAlive = true;

            int time = 1;
            ArrayList<Integer> list = new ArrayList<>();
            list.add(10000);
            list.add(50000);
            list.add(100000);
            list.add(500000);
            list.add(1000000);
            list.add(3000000);
            list.add(5000000);
            list.add(7000000);
            int lastTime = 100000;
            while (true) {
                if (time == 1 || time == lastTime || list.contains(time)) {
                    System.out.println("\\ Запущена новая итерация: " + time);
                }

                //forward pass
                for (int l = 1; l <= currentNeuralNetwork.numOfLayers; l++) { //Повторить для всех слоев
                    for (Map.Entry<String, Neuron> neuronSet : currentNeuralNetwork.listOfNeurons.entrySet()) {
                        Neuron neuron = neuronSet.getValue();
                        if (!neuron.id.split("\\.")[0].equals(Neuron.ID_INPUT) && !neuron.id.split("\\.")[0].equals(Neuron.ID_DISPLACE) && neuron.layer == l) {
                            neuron.makeInput(currentNeuralNetwork);
                            neuron.makeOutput();
                        }
                        if (neuron.id.split("\\.")[0].equals(Neuron.ID_OUTPUT) && neuron.layer == l) {
                            result = neuron.output;
                            if (time == 1 || time == lastTime || list.contains(time)) {
                                System.out.println("time: " + time + "; ideal: " + neuron.ideal + "; current: " + result);
                            }
                        }
                    }
                }

                //error search
//                HashMap<Double, Double> idealAndActualResults = new HashMap<>();
//                for (Map.Entry<String, Neuron> neuronSet : currentNeuralNetwork.listOfNeurons.entrySet()) {
//                    Neuron neuron = neuronSet.getValue();
//                    if (neuron.id.split("\\.")[0].equals(Neuron.ID_OUTPUT)) {
//                        idealAndActualResults.put(neuron.ideal, neuron.output);
//                    }
//                }
//                double error = AIMain.errorMSE(idealAndActualResults);

                //back pass
                //Нахождение дельты для всех выходящих нейронов
                for (Map.Entry<String, Neuron> neuronSet : currentNeuralNetwork.listOfNeurons.entrySet()) {
                    Neuron neuron = neuronSet.getValue();
                    if (neuron.id.split("\\.")[0].equals(Neuron.ID_OUTPUT)) {
                        neuron.delta = AIMain.deltaNeuronOutput(neuron);
                    }
                }
                //
                for (int l = currentNeuralNetwork.numOfLayers-1; l > 0; l--) {
                    for (Map.Entry<String, Neuron> neuronSet : currentNeuralNetwork.listOfNeurons.entrySet()) {
                        Neuron neuron = neuronSet.getValue();
                        if (neuron.layer == l) {
                            neuron.delta = AIMain.deltaNeuronHidden(neuron);
                            AIMain.synapseWeighChange(neuron);
                        }
                    }
                }


                if (time == 1 || time == lastTime || list.contains(time)) {
                    for (Map.Entry<String, Synapse> synapseSet : currentNeuralNetwork.listOfSynapses.entrySet()) {
                        System.out.println(synapseSet.getValue().getWeigh());
                    }
                    System.out.println("\n");
                }

                time++;
            }
//            int firstInput = (int) (Math.random()*2);
//            int secondInput = (int) (Math.random()*2);
//            currentNeuralNetwork.listOfNeurons.get(Neuron.ID_INPUT+"1.1").input = firstInput;
//            currentNeuralNetwork.listOfNeurons.get(Neuron.ID_INPUT+"1.2").input = secondInput;
//            int ideal;
//            if (firstInput == 0) {
//                if (secondInput == 0) {
//                    ideal = 0;
//                } else {
//                    ideal = 1;
//                }
//            } else {
//                if (secondInput == 0) {
//                    ideal = 1;
//                } else {
//                    ideal = 0;
//                }
//            }
//            currentNeuralNetwork.listOfNeurons.get(Neuron.ID_OUTPUT+"3.1").ideal = ideal;
//            System.out.println("New generation started: " + firstInput + ", " + secondInput + " = " + ideal);
//            new AIGeneration(currentNeuralNetwork, 1).startGeneration();

//            isAlive = false;

//            reward = 0; //Вычисление награды
        }
    }
}
