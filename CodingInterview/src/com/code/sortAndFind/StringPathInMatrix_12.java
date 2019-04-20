package com.code.sortAndFind;
/**
 * 二维矩阵中的路径
 */
public class StringPathInMatrix_12 {

    /**
     * @param matrix 输入矩阵
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @param str 待搜索的字符串
     * @return true 存在路径, false 不存在路径
     */
    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str){
        //参数校验
        if(matrix == null || matrix.length == 0 || matrix.length != rows * cols || str == null || str.length == 0){
            return false;
        }

        //初始化访问标记矩阵,因为不能再次进入
        boolean[] visited = new boolean[rows * cols];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
        }


        //以每个点为起始点进行搜索
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(hasPathCore(matrix, rows, cols, str, 0, i, j, visited)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 回溯搜索算法
     * @param matrix 输入矩阵
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @param str 待搜索的字符串
     * @param index 已经处理str中字符个数
     * @param row 当前处理的行数
     * @param col 当前处理的列数
     * @param visited 访问标记数组
     * @return true 存在路径， false 不存在路径
     */
    public static boolean hasPathCore(char[] matrix, int rows, int cols, char[] str, int index, int row, int col, boolean[] visited){
        if(index >= str.length){
            return true;
        }

        boolean hasPath = false;

        // 推断位置是否合法
        if(row >= 0 && row < rows && col >= 0 && col < cols && matrix[row * cols + col] == str[index] && !visited[row * cols + col]){
            //标记已访问
            visited[row * cols + col] = true;

            // 按左上右下进行探索
            hasPath =  hasPathCore(matrix, rows, cols, str, index+1, row, col - 1, visited)||
                    hasPathCore(matrix, rows, cols, str, index+1, row - 1, col, visited)||
                    hasPathCore(matrix, rows, cols, str, index+1, row, col + 1, visited)||
                    hasPathCore(matrix, rows, cols, str, index+1, row + 1, col, visited);

            // 回溯
            if(!hasPath){
                index--;
                visited[row * cols + col] = false;
            }
        }
        return hasPath;
    }

    public static void main(String[] args) {
        System.out.println(hasPath("ABCESFCSADEE".toCharArray(), 3, 4, "ABCCED".toCharArray()));
    }

}

