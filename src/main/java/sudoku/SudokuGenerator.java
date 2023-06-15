package main.java.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
//用回溯算法实现生成数独谜题
public class SudokuGenerator {
    SudokuFileWriter fileWriter = new SudokuFileWriter();
    String filename = "sudoku.txt";
    public Sudokupuzzle generateRandomSudoku(Sudokupuzzletype puzzleType) {
        Sudokupuzzle puzzle = new Sudokupuzzle(puzzleType.getRows(), puzzleType.getColums(), puzzleType.getBoxw(), puzzleType.getBoxh(), puzzleType.getValidnum());

        Sudokupuzzle copy = new Sudokupuzzle(puzzle); //复制一份，用于生成谜题的填充过程（答案）

        Random randomGenerator = new Random();

        List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getVALIDNUM()));//未使用的有效数字集合

        //从集合中选数填充完第一列
        for(int r = 0;r < copy.getnumrows();r++) {
            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());//随机生成集合里某个值
            copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
            notUsedValidValues.remove(randomValue);
        }


        backtrackSudokuSolver(0, 0, copy);//调用回溯递归算法

        int numberOfValuesToKeep = (int)(0.5*(copy.getnumrows()*copy.getnumrows()));//计算要保留数字的格子数量

        for(int i = 0;i < numberOfValuesToKeep;) {// 挑选随机单元格，填入数字
            int randomRow = randomGenerator.nextInt(puzzle.getnumrows());
            int randomColumn = randomGenerator.nextInt(puzzle.getnumcolumns());

            if(puzzle.isSlotAvailable(randomRow, randomColumn)) {  //随机填入空单元格
                puzzle.makeMove(randomRow, randomColumn, copy.getvalue(randomRow, randomColumn), false);
                i++;
            }
        }
        fileWriter.writeToFile(puzzle.toString(),copy.toString(),filename);

        return puzzle;
    }

    /**
     * Solves the sudoku puzzle
     * Pre-cond: r = 0,c = 0
     * Post-cond: solved puzzle
     * @param r: the current row
     * @param c: the current column
     * @return valid move or not or done
     * Responses: Erroneous data
     */
    private boolean backtrackSudokuSolver(int r,int c,Sudokupuzzle puzzle) {
        //检查范围
        if(!puzzle.inrange(r,c)) {
            return false;
        }

        //若所在单元格为空
        if(puzzle.isSlotAvailable(r, c)) {

            //循环遍历 找到可能的数字准备填入
            for(int i = 0;i < puzzle.getVALIDNUM().length;i++) {  //这里的算法可以改进

                //如果数字可放入当前单元格，满足不在同行同列同宫格
                if(!puzzle.numinrow(r, puzzle.getVALIDNUM()[i]) && !puzzle.numincol(c,puzzle.getVALIDNUM()[i]) && !puzzle.numinbox(r,c,puzzle.getVALIDNUM()[i])) {

                    //进行填充（放入）
                    puzzle.makeMove(r, c, puzzle.getVALIDNUM()[i], true);

                    //如果填满了，表示已找到有效解
                    if(puzzle.bboxfull()) {
                        return true;
                    }

                    //到达最后一行，找下列第一个位置
                    if(r == puzzle.getnumrows() - 1) {
                        if(backtrackSudokuSolver(0,c + 1,puzzle)) {
                            return true;
                        }
                    } else {//没到达最后一行，找下一行位置
                        if(backtrackSudokuSolver(r + 1,c,puzzle)) {
                            return true;
                        }
                    }
                }
            }
        }

        //若所在单元格不为空
        else {
            //求解下一格
            if(r == puzzle.getnumrows() - 1) { //若在前一列最后一行，寻找下一列第一行
                return backtrackSudokuSolver(0,c + 1,puzzle);
            } else {
                return backtrackSudokuSolver(r + 1,c,puzzle);
            }
        }

        //数字填上后继续找，最终回溯返回false时表示 不满足条件，置空单元格
        puzzle.makeSlotEmpty(r, c);

        //backtrack
        return false;
    }
}