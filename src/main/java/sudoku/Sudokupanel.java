package main.java.sudoku;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
//创建数独面板，实现交互性
@SuppressWarnings("serial")
public class Sudokupanel extends JPanel {

    private Sudokupuzzle puzzle;
    private int currentlySelectedCol; //当前选定的列
    private int currentlySelectedRow; //当前选定的行
    private int usedWidth;
    private int usedHeight;
    private int fontSize;


    public Sudokupanel() {
        this.setPreferredSize(new Dimension(540,450));//设置面板大小
        this.addMouseListener(new SudokuPanelMouseAdapter());//添加鼠标事件监听器实例
        this.puzzle = new SudokuGenerator().generateRandomSudoku(Sudokupuzzletype.nine);//默认
        currentlySelectedCol = -1;
        currentlySelectedRow = -1;
        usedWidth = 0;
        usedHeight = 0;
        fontSize = 26;

    }


    public Sudokupanel(Sudokupuzzle puzzle) {
        this.setPreferredSize(new Dimension(540,450));
        this.addMouseListener(new SudokuPanelMouseAdapter());
        this.puzzle = puzzle;
        currentlySelectedCol = -1;
        currentlySelectedRow = -1;
        usedWidth = 0;
        usedHeight = 0;
        fontSize = 26;


    }
    //设置谜题和字体
    public void newSudokuPuzzle(Sudokupuzzle puzzle) {
        this.puzzle = puzzle;
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    //绘制方法，绘制数独面板，绘制组件外观
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(1.0f,1.0f,1.0f));

        //计算每个数独单元格的宽度和高度。
        int slotWidth = this.getWidth()/puzzle.getnumcolumns();
        int slotHeight = this.getHeight()/puzzle.getnumrows();
        //计算用于绘制数独拼图的实际宽度和高度
        usedWidth = (this.getWidth()/puzzle.getnumcolumns())*puzzle.getnumcolumns();
        usedHeight = (this.getHeight()/puzzle.getnumrows())*puzzle.getnumrows();
        //在画布上填充一个矩形，作为数独拼图的背景。白色
        g2d.fillRect(0, 0,usedWidth,usedHeight);
        //绘制网格线
        g2d.setColor(new Color(0.0f,0.0f,0.0f));
        for(int x = 0;x <= usedWidth;x+=slotWidth) {
            if((x/slotWidth) % puzzle.getBOXW() == 0) {
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(x, 0, x, usedHeight);
            }
            else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(x, 0, x, usedHeight);
            }
        }
        //边框的线
        g2d.drawLine(0, 0, 0,usedHeight);
        g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
        for(int y = 0;y <= usedHeight;y+=slotHeight) {
            if((y/slotHeight) % puzzle.getBOXH() == 0) {
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(0, y, usedWidth, y);
            }
            else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, y, usedWidth, y);
            }
        }
        //边框的线
        g2d.drawLine(0, 0, usedWidth - 1,0);
        g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);
        //绘制数独拼图的数字和当前选中的格子的背景
        Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
        g2d.setFont(f);
        //获取字体渲染的上下文信息
        FontRenderContext fContext = g2d.getFontRenderContext();
        for(int row=0;row < puzzle.getnumrows();row++) {
            for(int col=0;col < puzzle.getnumcolumns();col++) {
                if(!puzzle.isSlotAvailable(row, col)) {
                    //格子内文本的宽度高度
                    int textWidth = (int) f.getStringBounds(puzzle.getvalue(row, col), fContext).getWidth();
                    int textHeight = (int) f.getStringBounds(puzzle.getvalue(row, col), fContext).getHeight();
                    //在格子中心绘制数字


                    if(!puzzle.mutable[row][col])
                    {
                        g2d.drawString(puzzle.getvalue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));

                    }
                    else{
                        g2d.setColor(Color.PINK);
                        g2d.drawString(puzzle.getvalue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));

                    }
                    g2d.setColor(Color.BLACK);


                }
            }
        }
        if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
            g2d.setColor(new Color(0.0f,0.5f,1.0f,0.1f));
            g2d.fillRect(currentlySelectedCol * slotWidth ,currentlySelectedRow * slotHeight ,slotWidth,slotHeight);
            // 绘制所在行
            g2d.fillRect(
                    0,
                    currentlySelectedRow * slotHeight,
                    usedWidth,
                    slotHeight
            );

            // 绘制所在列
            g2d.fillRect(
                    currentlySelectedCol * slotWidth,
                    0,
                    slotWidth,
                    usedHeight
            );
            g2d.setColor(new Color(0.0f, 0.5f, 1.0f, 0.1f));
            int boxWidth = puzzle.getBOXW() * slotWidth;
            int boxHeight = puzzle.getBOXH() * slotHeight;
            int boxRow = currentlySelectedRow / puzzle.getBOXH();
            int boxCol = currentlySelectedCol / puzzle.getBOXW();
            int boxX = boxCol * boxWidth;
            int boxY = boxRow * boxHeight;
            g2d.fillRect(boxX, boxY, boxWidth, boxHeight);
        }
    }

    //事件处理方法
    ///处理从数字按钮的动作监听器接收到的消息
    public void messageFromNumActionListener(String buttonValue) {
        if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
            puzzle.makeMove2(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
            repaint();
            if(puzzle.issolved()){
                JOptionPane.showMessageDialog(this, "Congratulations! SodukuPuzzle solved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(puzzle.bboxfull())
            {
                JOptionPane.showMessageDialog(this, "Sorry! Failed to solve Sudoku.", "failure", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    //数字按钮的动作监听器类 被点击时调用message
    public class NumActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            JButton button = (JButton) e.getSource();
//            String buttonValue = button.getText();
//            messageFromNumActionListener(buttonValue);
            //将数字按钮的文本值作为参数传递
            messageFromNumActionListener(((JButton) e.getSource()).getText());
//            button.setForeground(Color.GREEN);
        }
    }
    //鼠标适配器类，处理鼠标点击事件，更新选定状态
    private class SudokuPanelMouseAdapter extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //鼠标点击左键，则计算单元格的宽度和高度，根据点击的坐标计算当前行和列
            if(e.getButton() == MouseEvent.BUTTON1) {
                int slotWidth = usedWidth/puzzle.getnumcolumns();
                int slotHeight = usedHeight/puzzle.getnumrows();
                currentlySelectedRow = e.getY() / slotHeight;
                currentlySelectedCol = e.getX() / slotWidth;
                e.getComponent().repaint();
            }
        }
    }
}