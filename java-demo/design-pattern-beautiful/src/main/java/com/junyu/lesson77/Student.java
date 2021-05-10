package com.junyu.lesson77;

/**
 * @author haojunsheng
 * @date 2021/5/8 19:42
 */
public class Student {
    private String name;
    private int age;
    private float score;
    public Student(String name, int age, float score) {
        this.name=name;
        this.age=age;
        this.score=score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
