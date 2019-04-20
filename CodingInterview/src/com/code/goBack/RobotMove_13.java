package com.code.goBack;

/**
 * 机器人运动范围
 */
public class RobotMove_13 {

    /**
     * 回溯算法
     *
     * 返回机器人能到达的格子数
     * @param threshold 阈值
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @return
     */
    public int movingCount(int threshold, int rows, int cols){
        //参数校验
        if(threshold < 0 || rows <= 0 || cols <= 0){
            return 0;
        }

        //初始化访问标识数组
        boolean[] visited = new boolean[rows * cols];
        for(int i = 0; i < rows * cols; i++){
            visited[i] = false;
        }

        int count = movingCountCore(threshold, rows, cols, 0, 0, visited);
        return count;
    }

    /*
     * 判断从第row行、第col列出发可以到达的格子数
     */
    public int movingCountCore(int threshold, int rows, int cols, int row, int col, boolean[] visited){
        int count = 0;
        if(check(threshold, rows, cols, row, col, visited)){
            visited[row * cols + col] = true;
            count = 1 + movingCountCore(threshold, rows, cols, row-1, col, visited)
                    + movingCountCore(threshold, rows, cols, row, col-1, visited)
                    + movingCountCore(threshold, rows, cols, row+1, col, visited)
                    + movingCountCore(threshold, rows, cols, row, col+1, visited);
        }
        return count;
    }

    //检查机器人能否到达该格子
    public boolean check(int threshold, int rows, int cols, int row, int col, boolean[] visited){
        if(row >= 0 && row < rows && col >= 0 && col <cols
                && getDigitSum(row) + getDigitSum(col) <= threshold
                && !visited[row * cols + col]){
            return true;
        }
        return false;
    }

    //求一个数的各位数和
    public int getDigitSum(int num){
        int sum = 0;
        while(num > 0){
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        RobotMove_13 robot = new RobotMove_13();
        int count = robot.movingCount(4,6,6);
        System.out.println(count);
    }
}