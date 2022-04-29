package SSAI;

import java.util.ArrayList;

public class AIGeneration {
    public AIGeneration(NeuralNetwork neuralNetwork, int numOfAI) {
        this.neuralNetwork = neuralNetwork;
        this.numOfAI = numOfAI;
        System.out.println("\\ Создано новое поколение. Количество ИИ: " + numOfAI);
    }

    NeuralNetwork neuralNetwork;
    int numOfAI;
    ArrayList<AI> listOfAI = new ArrayList<>();

    void startGeneration() {
        for (int ai = numOfAI; ai > 0; ai--) {
            new AI(this).start();
        }
        System.out.println("\\ Поколение успешно запущено");
    }
}
