import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NumberAnalyze {
    private static final double PERCENTILE = 0.9;
    private File file;// = new File("c:/data.txt");
    private List<Integer> numbers;

    public NumberAnalyze() {
        this.file = this.getFileName();
        this.numbers = this.getNumbersFromFile(this.file);
    }

    public static void main(String[] args) {
        //we can create static utilitarian class, but for more flexibility we create instance of class
        NumberAnalyze numberAnalyze = new NumberAnalyze();

        /**TODO delete this. Testing*/
        //numberAnalyze.numbers.stream().forEach(System.out::println);
        numberAnalyze.printToConsole(numberAnalyze.getPercentale(), numberAnalyze.getMediana(), numberAnalyze.getAverage(), numberAnalyze.getMax(), numberAnalyze.getMin());
    }

    public void printToConsole (Integer percentale, Integer median, Integer average, Integer max, Integer min) {
        System.out.println(
                "90 percentile " + percentale + "\n" +
                "median " + median + "\n" +
                "average " + average + "\n" +
                "max " + max + "\n" +
                "min " + min);
    }

    public Integer getPercentale() {
        //for readability add variable
        int length = this.numbers.size();
        int percentile = (int) (length * PERCENTILE);
        return numbers.get(percentile-1);
    }

    public Integer getMediana() {
        if (numbers.size() % 2 != 0)
            return numbers.get(numbers.size() / 2);
        else {
            int value1 = numbers.get(numbers.size()/2-1);
            int value2 = numbers.get(numbers.size()/2);
            return Math.round((value1+value2)/2);
        }
    }

    public Integer getAverage() {
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        return sum/numbers.size();
    }

    public Integer getMax() {
        return numbers.get(numbers.size()-1);
    }

    public Integer getMin() {
        return numbers.get(0);
    }

    public List<Integer> getNumbersFromFile(File file) {
        List<Integer> numbers = new ArrayList<>();
        //if file writed in OS Windows and started with BOM symbol
        Character bom = 65279;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
            while (reader.ready()) {
                int number = Integer.valueOf(reader.readLine().trim().replace(bom.toString(), ""));
                numbers.add(number);
            }
            if (numbers.isEmpty()) throw new IllegalStateException("This file is empty");
        } catch (IOException e) {
            System.out.println("Error when reading numbers from file. Maybe file have wrong format.");
            e.printStackTrace();
        }
        Collections.sort(numbers);
        return numbers;

    }

    public File getFileName() {
        System.out.println("Please enter destination of file fo analyze: ");
        try (BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(System.in))) {
            String fileName = fileNameReader.readLine();
            File readed = new File(fileName);
            if (!readed.exists() || readed.isDirectory())
                throw new IllegalArgumentException("File not exist or its directory.");
            return readed;
        } catch (IOException e) {
            System.out.println("Error, when reading filename. Maybe you write something illegal in console.\n" + e.toString());
        }
        throw new IllegalStateException("Its position not reachable. If you see this message something very wrong!");
    }
}
