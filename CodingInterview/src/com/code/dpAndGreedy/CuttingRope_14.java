package com.code.dpAndGreedy;

/**
 * 题目：
 *给定一根长度为n的绳子，请把绳子剪成m段（m、n都是整数，n>1并且m>1），每段绳子的长度记为k[0],k[1],…,k[m]。请问k[0]* k[1] * … *k[m]可能的最大乘积是多少？
 * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 */
public class CuttingRope_14 {

    /*
     * 使用动态规划的思想
     */

    /**
     *
     * 分析：
     * 1、求一个问题的最优解；
     * 2、整体的问题的最优解是依赖各个子问题的最优解；
     * 3、把大问题分解成若干个小问题，这些小问题之间还有互相重叠的更小的子问题；
     * 4、为避免子问题的重复计算，我们存储子问题的最优解。从上往下分析问题，从下往上求解问题。
     * 上面的几个条件可以看出，属于动态规划问题。
     *
     * 动态规划：
     * 1、定义函数f(n)表示为把长度为n的绳子剪成若干段后各段长度乘积的最大值。
     * 2、对于第一刀，我们有n-1种可能的选择，可推导出f(n)=max{f(i)*f(n-i)};
     * 3、很明显这是一个从上至下的递归，但是这个递归存在很多重复的计算，所以使用至下而上的动态规划，将子问题的最优解保存。
     * 4、注意绳子剪成ix(n-i)和(n-i)xi是相同的；
     * 5、注意不符合切割条件的输入n，以及输入为2、3长度时的结果，因为题中规定m>1。
     *
     * 时间复杂度O(n^2)，空间复杂度O(n)
     * @param length
     * @return
     */
    public static int dynamicPlan(int length){
        if(length <= 1)
            return 0;
        if(length == 2)
            return 1;
        if(length == 3)
            return 2;

        // products数组存放的是每个子问题的最优解
        // products[i]表示长度为i的绳子剪为若干段后各段长度乘积的最大值
        // 为了求解f(i)，我们需要求出所有的f(j)*f(i-j)
        // products的长度为length+1，因为存放的是0~length
        int[] products = new int[length + 1];
        products[0] = 0;
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;

        int max = 0;
        for(int i = 4; i <= length; i++){
            max = 0;
            for(int j = 1; j <= i/2; j++){
                if(products[j] * products[i-j] > max)
                    max = products[j] * products[i-j];
            }
            products[i] = max;
        }
        return products[length];
    }

    /**
     * 贪心算法
     * 1、贪心算法在对问题求解时，不从整体最优上加以考虑，他所做出的是在某种意义上的局部最优解；
     * 2、选择的贪心策略必须具备无后效性，即某个状态以前的过程不会影响以后的状态，只与当前状态有关；
     * 3、题目贪心策略：当n>=5时，尽可能多地剪长度为3的绳子；当剩下的绳子长度为4时，把绳子剪成两段长度为2的绳子。
     * @param length
     * @return
     */
    public static int greedy(int length){
        if(length <= 1)
            return 0;
        if(length == 2)
            return 1;
        if(length == 3)
            return 2;

        //尽可能多的剪长度为3的绳子
        int timesOf3 = length / 3;
        //当绳子最后剩下的长度为4的时候，不能再剪去长度为3的绳子段
        //此时，更好的方法就是把绳子剪成长度为2的两段，因为2x2>1x3
        if(length - timesOf3 * 3 == 1){
            timesOf3--;
        }
        int timesOf2 = (length - timesOf3 * 3) / 2;
        return (int) (Math.pow(3, timesOf3) * Math.pow(2, timesOf2));
    }
    public static void main(String args[]){
        System.out.println(dynamicPlan(5));
        System.out.println(greedy(5));
    }
}
