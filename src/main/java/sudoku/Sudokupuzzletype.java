package main.java.sudoku;

public enum Sudokupuzzletype {  //枚举类表示数独的游戏类型，类的作用提供一组固定的常量，常量又与属性和方法关联
    //类对象
    four(4,4,2,2,new String[] {"1","2","3","4"},"4x4 game"),
    six(6,6,3,2,new String[] {"1","2","3","4","5","6"},"6x6 game"),
    nine(9,9,3,3,new String[] {"1","2","3","4","5","6","7","8","9"},"9x9 game"),
//    duijiaoxian()
//    twelve (12,12,4,3,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C"},"12 By 12 Game"),
    sixteen (16,16,4,4,new String[] {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"},"16 By 16 Game");
//    duijiaoxian(9,9,)
    //属性
    private final int rows;
    private final int colums;
    private final int boxw;
    private final int boxh;
    private final String[] validnum;
    private final String desc;

    //构造函数（方法）  初始化 谜题的行和列 谜题的每个宫格的宽（列）和高（行）  谜题中可用的数字值的数组  数独类型描述
    private Sudokupuzzletype(int rows,int colums,int boxw,int boxh,String[] validnum,String desc){
        this.rows = rows;
        this.colums = colums;
        this.boxw = boxw;
        this.boxh = boxh;
        this.validnum = validnum;
        this.desc = desc;
    }
    //给其他类 提供访问方法
    public int getRows(){ return rows; }
    public  int getColums(){ return  colums; }
    public int getBoxw(){
        return boxw;
    }
    public int getBoxh(){
        return boxh;
    }
    public String[] getValidnum(){
        return validnum;
    }
    public  String getDesc(){
        return desc;
    }


}
