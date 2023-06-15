package main.java.sudoku;
//数独游戏 类
public class Sudokupuzzle {
    //验证解答是否正确
    //存储数独游戏的数字  存储数独游戏数字是否可修改的布尔值  游戏的行和列 每个宫格的宽和高  有效数字值数组
    protected String[][] bbox;
    protected  boolean [][] mutable;
    private final int ROWS;
    private final int COLUMNS;
    private final int BOXW;
    private final int BOXH;
    private final String [] VALIDNUM;

    //构造函数，通过传入参数初始化数独游戏对象
    public Sudokupuzzle(int rows,int columns,int boxw,int boxh,String[] validnum ){
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOXW = boxw;
        this.BOXH = boxh;
        this.VALIDNUM = validnum;
        this.bbox = new String[ROWS][COLUMNS] ;
        this.mutable = new boolean[ROWS][COLUMNS];
        initbbox();
        initmutable();
    }


    //通过复制数独对象 初始化
    public Sudokupuzzle(Sudokupuzzle puzzle){
        this.ROWS = puzzle.ROWS;
        this.COLUMNS = puzzle.COLUMNS;
        this.BOXW = puzzle.BOXW;
        this.BOXH = puzzle.BOXH;
        this.VALIDNUM = puzzle.VALIDNUM;
        this.bbox = new String[ROWS][COLUMNS];
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLUMNS;j++){
                this.bbox[i][j] = puzzle.bbox[i][j];
            }
        }
        this.mutable = new boolean[ROWS][COLUMNS];
        for(int i = 0;i<ROWS;i++){
            for(int j=0;j<COLUMNS;j++){
                this.mutable[i][j]= puzzle.mutable[i][j];
            }
        }
    }

    //获取数独的相关信息
    public int getnumrows(){
        return this.ROWS;
    }
    public int getnumcolumns(){
        return this.COLUMNS;
    }
    public int getBOXW(){
        return this.BOXW;
    }
    public int getBOXH(){
        return this.BOXH;
    }
    public String[] getVALIDNUM(){
        return this.VALIDNUM;
    }

    //尝试在指定行列位置 放置数字 ，并查看是否可修改。
    public void makeMove(int row,int col,String num,boolean isMutable) {
        if(this.isValidValue(num) && this.isValidMove(row,col,num) && this.isSlotMutable(row, col)) {//数字、移动有效且位置可修改
            this.bbox[row][col] = num;
            this.mutable[row][col] = isMutable;
        }
    }
    public void makeMove2(int row,int col,String num,boolean isMutable) {

            this.bbox[row][col] = num;
            this.mutable[row][col] = isMutable;

    }
    //判断在该位置放置数字是否有效移动
    public boolean isValidMove(int row,int col,String num ){
        if(this.inrange(row,col)){
            if(!this.numincol(col,num) && !this.numinrow(row,num) && !this.numinbox(row,col,num)) {
                return true;
            }
        }
        //若不满足上述条件，说明该数字移动不了
        return false;
    }

    //判断所在的该列是否已存在 插入数字
    public boolean numincol(int col,String num){
        if(col <= this.COLUMNS) {
            for(int row=0;row < this.ROWS;row++) {
                if(this.bbox[row][col].equals(num)) {
                    return true;
                }
            }
        }
        return false;

    }
    //判断所在的该行是否已存在 插入数字
    public boolean numinrow(int row,String num){
        if(row <= this.ROWS) {
            for(int col=0;col < this.COLUMNS;col++) {
                if(this.bbox[row][col].equals(num)) {
                    return true;
                }
            }
        }
        return false;

    }

    //判断所在的宫格里面是否已存在 插入数字
    public boolean numinbox(int row,int col,String num){
        if(this.inrange(row, col)) {
            int boxRow = row / this.BOXH;//宫格行索引
            int boxCol = col / this.BOXW;//宫格列索引

            int startingRow = (boxRow*this.BOXH);//数独表格行索引
            int startingCol = (boxCol*this.BOXW);//列索引

            for(int i = startingRow;i <= (startingRow+this.BOXH)-1;i++) {
                for(int j = startingCol;j <= (startingCol+this.BOXW)-1;j++) {
                    if(this.bbox[i][j].equals(num)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }
    //判断指定位置是否可用（空的且可修改）
    public boolean isSlotAvailable(int row,int col){
        return (this.inrange(row,col) && this.bbox[row][col].equals("") && this.isSlotMutable(row, col));

    }
    //判断指定位置是否可修改
    public boolean isSlotMutable (int row,int col){
        return this.mutable[row][col];

    }
    //获取指定位置的数字值
    public  String getvalue(int row,int col){
        if(this.inrange(row,col)) {
            return this.bbox[row][col];
        }
        return "";

    }
    //获取数独游戏的 数字存储
    public  String [][] getbbox(){
        return this.bbox;

    }
    //判断数字是否有效，（是否符合给定的有效数字数组里面的数字）
    private boolean isValidValue(String num) {
        for (String str : this.VALIDNUM) {
            if (str.equals(num)) {
                return true;
            }
        }
        return false;
    }

    //判断是否在合法范围内
    public boolean inrange(int row,int col){
            return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }

    //判断数独游戏的数字是否填满
    public boolean bboxfull() {
        for (int r = 0; r < this.ROWS; r++) {
            for (int c = 0; c < this.COLUMNS; c++) {
                if (this.bbox[r][c].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    //将指定位置的数字清除
    public void makeSlotEmpty (int row,int col){
            this.bbox[row][col] = "";
    }

    @Override
    public String toString(){   //提供一个自定义的对象，打印数独游戏的当前状态 返回数独游戏的当前状态的字符串表示
            String str = "Game Board:\n";
            for(int row=0;row < this.ROWS;row++) {
                for(int col=0;col < this.COLUMNS;col++) {
                    if(this.bbox[row][col]=="")
                    {
                        str += "  ";
                    }
                    else{
                        str += this.bbox[row][col] + " ";

                    }
                }
                str += "\n";
            }
            return str+"\n";

    }
    public boolean issolved() {
        // 检查每一行是否包含1到9的所有数字
        for (int row = 0; row < ROWS; row++) {
            boolean[] found = new boolean[VALIDNUM.length];
            for (int col = 0; col < COLUMNS; col++) {
                String value = bbox[row][col];
                if (isValidValue(value)) {
                    int index = getIndex(value);
                    found[index] = true;
                }
            }
            for (boolean numFound : found) {
                if (!numFound) {
                    return false;
                }
            }
        }

        // 检查每一列是否包含1到9的所有数字
        for (int col = 0; col < COLUMNS; col++) {
            boolean[] found = new boolean[VALIDNUM.length];
            for (int row = 0; row < ROWS; row++) {
                String value = bbox[row][col];
                if (isValidValue(value)) {
                    int index = getIndex(value);
                    found[index] = true;
                }
            }
            for (boolean numFound : found) {
                if (!numFound) {
                    return false;
                }
            }
        }

        // 检查每个宫格是否包含1到9的所有数字
        for (int boxRow = 0; boxRow < ROWS; boxRow += BOXH) {
            for (int boxCol = 0; boxCol < COLUMNS; boxCol += BOXW) {
                boolean[] found = new boolean[VALIDNUM.length];
                for (int row = boxRow; row < boxRow + BOXH; row++) {
                    for (int col = boxCol; col < boxCol + BOXW; col++) {
                        String value = bbox[row][col];
                        if (isValidValue(value)) {
                            int index = getIndex(value);
                            found[index] = true;
                        }
                    }
                }
                for (boolean numFound : found) {
                    if (!numFound) {
                        return false;
                    }
                }
            }
        }

        return true; // 如果通过了上述所有检查，则数独已成功解决
    }

    private int getIndex(String value) {
        for (int i = 0; i < VALIDNUM.length; i++) {
            if (VALIDNUM[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private void initbbox(){
            for(int row = 0;row < this.ROWS;row++) {
                for(int col = 0;col < this.COLUMNS;col++) {
                    this.bbox[row][col] = "";
                }
            }


    }

    private  void initmutable(){
            for(int row = 0;row < this.ROWS;row++) {
                for(int col = 0;col < this.COLUMNS;col++) {
                    this.mutable[row][col] = true;
                }
            }
    }


}
