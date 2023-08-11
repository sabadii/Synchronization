import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int threadCount = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    String route = generateRoute("RLRFR", 100);
                    int countR = countCommands(route, 'R');
                    updateSizeToFreq(countR);
                    System.out.println("Маршрут: " + route);
                    System.out.println("Количество команд поворота направо: " + countR);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printSizeToFreq();

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countCommands(String route, char command) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == command) {
                count++;
            }
        }
        return count;
    }

    public synchronized static void updateSizeToFreq(int size) {
        sizeToFreq.put(size, sizeToFreq.getOrDefault(size, 0) + 1);
    }

    public synchronized static void printSizeToFreq() {
        System.out.println();
        int mostFreq = 0;
        int mostFreqCount = 0;
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            if (entry.getValue() > mostFreqCount) {
                mostFreqCount = entry.getValue();
                mostFreq = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений " + mostFreq + " (встретилось " + mostFreqCount + " раз)");
    }
}