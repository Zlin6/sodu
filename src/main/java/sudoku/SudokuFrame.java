package main.java.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//数独游戏主窗口
@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {
    //显示和操作游戏界面
    private JPanel buttonSelectionPanel;
    private Sudokupanel sPanel;

    public SudokuFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);////窗口关闭
        this.setTitle("Sudoku");//窗口标题
        this.setMinimumSize(new Dimension(1000,600));//窗口大小

        JMenuBar menuBar = new JMenuBar();  //菜单栏
        JMenu file = new JMenu("Game");//
        JMenu newGame = new JMenu("New Game");
        JMenuItem fourByfourGame = new JMenuItem("4 x 4 Game");
        fourByfourGame.addActionListener(new NewGameListener(Sudokupuzzletype.four,30));
        JMenuItem sixBySixGame = new JMenuItem("6 x 6 Game");
        sixBySixGame.addActionListener(new NewGameListener(Sudokupuzzletype.six,30));
        JMenuItem nineByNineGame = new JMenuItem("9 x 9 Game");
        nineByNineGame.addActionListener(new NewGameListener(Sudokupuzzletype.nine,30));
//        JMenuItem twelveByTwelveGame = new JMenuItem("12 x 12 Game");
//        twelveByTwelveGame.addActionListener(new NewGameListener(Sudokupuzzletype.twelve,20));

		/*
		 * need to include this when solving algorithm is improved
		 JMenuItem sixteenBySizteenGame = new JMenuItem("16 By 16 Game");
		sixteenBySizteenGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXTEENBYSIXTEEN,16));
		*/
        newGame.add(fourByfourGame);

        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);
//        newGame.add(twelveByTwelveGame);
        //newGame.add(sixteenBySizteenGame);

        //创建菜单栏的各选项
        file.add(newGame);
        menuBar.add(file);
        this.setJMenuBar(menuBar);
        //创建中间容器windowpanel，添加数独面板和按钮选择面板，再添加到主窗口
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800,600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

        sPanel = new Sudokupanel();  //添加自定义的面板

        //解耦的设计使得对界面进行修改和调整更加灵活和方便。
        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(Sudokupuzzletype.nine, 26);
    }
    //重构界面
    public void rebuildInterface(Sudokupuzzletype puzzleType,int fontSize) {
        Sudokupuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle); //初始化一个谜题
        sPanel.setFontSize(fontSize);  //初始化字体大小
        buttonSelectionPanel.removeAll();//移除所有数字按钮
        for(String value : generatedPuzzle.getVALIDNUM()) {  //初始化数字按钮
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(50,50));
            b.addActionListener(sPanel.new NumActionListener());
//            b.setText(value);
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();  //重新显示
        buttonSelectionPanel.revalidate();  //重新布局
        buttonSelectionPanel.repaint();
    }


    private class NewGameListener implements ActionListener {  //newgame的监听器

        private Sudokupuzzletype puzzleType;
        private int fontSize;

        public NewGameListener(Sudokupuzzletype puzzleType,int fontSize) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType,fontSize);
        }
    }

    public static void main(String[] args) {
        //将创建窗口和显示窗口的操作放在事件分派线程中执行，确保界面响应性和线程安全
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuFrame frame = new SudokuFrame();
                frame.setVisible(true);
            }
        });
    }
}