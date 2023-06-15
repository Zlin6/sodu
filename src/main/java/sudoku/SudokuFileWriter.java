package main.java.sudoku;

import java.io.FileWriter;
import java.io.IOException;

public class SudokuFileWriter {
    public void writeToFile(String puzzle, String solution, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // 写入题目
            writer.write("Puzzle:\n");
            writer.write(puzzle);
            writer.write("\n\n");

            // 写入答案
            writer.write("Solution:\n");
            writer.write(solution);

            System.out.println("Puzzle and solution have been written to the file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing puzzle and solution to the file: " + filename);
            e.printStackTrace();
        }
    }
}
