package cn.edu.cug.cs.gtl.app;

import cn.edu.cug.cs.gtl.geom.Timeline;
import org.apache.spark.mllib.random.UniformGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArithmeticGenerator {
    private String outputFileName = "d://devs//data//1.txt";
    private BufferedWriter bufferedWriter = null;
    private UniformGenerator randomGenerator = null;

    public ArithmeticGenerator(String fileName) {
        if (fileName != null && fileName.isEmpty() == false)
            outputFileName = fileName;
        randomGenerator = new UniformGenerator();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.finalize();
    }

    public static void main(String[] args) {
        ArithmeticGenerator ag = new ArithmeticGenerator(null);
        for (int i = 0; i < 40; i++) {
            ag.generateDailyExercise(3, 5, 5, 10);
        }
    }

    public void generateDailyExercise(int digit, int addition, int subtraction, int multiplication) {
        int i = 0, j = 0, k = 0;
        int r = 0;
        while (i < addition || j < subtraction || k < multiplication) {
            r = (int) (randomGenerator.nextValue() * 10) % 3;
            if (r == 0 && i < addition) {
                generateAddition(digit);
                i++;
            } else if (r == 1 && j < subtraction) {
                generateSubtraction(digit);
                j++;
            } else if (r == 2 && k < multiplication) {
                generateMultiplication(digit);
                k++;
            } else {
                continue;
            }
        }
    }

    public void generateSubtraction(int digit) {
        try {
            Integer[] digits1 = new Integer[digit];
            Integer[] digits2 = new Integer[digit];
            boolean repeat = true;
            while (repeat) {
                for (int i = 0; i < digit; ++i) {
                    digits1[i] = (int) (randomGenerator.nextValue() * 10);
                    digits2[i] = (int) (randomGenerator.nextValue() * 10);
                    if (digits1[0] < digits2[0]) {
                        Integer t = digits1[0];
                        digits1[0] = digits2[0];
                        digits2[0] = t;
                    }
                    if (digits1[i] < digits2[i])
                        repeat = false;
                }
            }
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for (int i = 0; i < digit; ++i) {
                sb1.append(digits1[i].intValue());
                sb2.append(digits2[i].intValue());
            }

            if (Integer.valueOf(sb1.toString()) < Integer.valueOf(sb2.toString())) {
                Integer[] t = digits1;
                digits1 = digits2;
                digits2 = t;
            }

            sb1.delete(0, sb1.length());
            sb2.delete(0, sb2.length());

            StringBuilder sb3 = new StringBuilder("——");
            for (int i = 0; i < digit; ++i) {
                sb1.append(digits1[i].intValue()).append(' ');
                sb2.append(digits2[i].intValue()).append(' ');
                sb3.append("—");
            }
            sb1.insert(0, "    ");
            sb2.insert(0, "   -");

            bufferedWriter.write(sb1.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(sb2.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(sb3.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateAddition(int digit) {
        try {
            Integer[] digits1 = new Integer[digit];
            Integer[] digits2 = new Integer[digit];
            boolean repeat = true;
            while (repeat) {
                for (int i = 0; i < digit; ++i) {
                    digits1[i] = (int) (randomGenerator.nextValue() * 10);

                    while (digits1[0] == 0)
                        digits1[0] = (int) (randomGenerator.nextValue() * 10);

                    digits2[i] = (int) (randomGenerator.nextValue() * 10);
                    if (digits1[i] + digits2[i] > 10)
                        repeat = false;
                }
            }
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder("——");
            for (int i = 0; i < digit; ++i) {
                sb1.append(digits1[i].intValue()).append(' ');
                sb2.append(digits2[i].intValue()).append(' ');
                sb3.append("—");
            }
            sb1.insert(0, "    ");
            sb2.insert(0, "   +");

            bufferedWriter.write(sb1.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(sb2.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(sb3.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateMultiplication(int digit) {
        try {
            Integer[] digits1 = new Integer[digit];
            Integer[] digits2 = new Integer[digit];
            boolean repeat = true;
            while (repeat) {
                for (int i = 0; i < digit; ++i) {
                    digits1[i] = (int) (randomGenerator.nextValue() * 10);

                    while (digits1[0] == 0)
                        digits1[0] = (int) (randomGenerator.nextValue() * 10);

                    digits2[i] = (int) (randomGenerator.nextValue() * 10);
                    if (digits1[i] + digits2[i] > 10)
                        repeat = false;
                }
            }
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder("——");
            for (int i = 0; i < digit; ++i) {
                sb1.append(digits1[i].intValue()).append(' ');
                sb2.append(' ').append(' ');
                sb3.append("—");
            }
            sb1.insert(0, "    ");
            sb2.insert(0, "   x");

            bufferedWriter.write(sb1.toString());
            bufferedWriter.newLine();

            while (digits2[0] < 2)
                digits2[0] = (int) (randomGenerator.nextValue() * 10);
            sb2.replace(sb2.length() - 2, sb2.length() - 1, digits2[0].toString());
            bufferedWriter.write(sb2.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(sb3.toString());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
