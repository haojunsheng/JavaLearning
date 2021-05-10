package com.junyu.lesson77;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author haojunsheng
 * @date 2021/5/8 19:42
 */

public class Demo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 19, 89.0f));
        students.add(new Student("Peter", 20, 78.0f));
        students.add(new Student("Leo", 18, 99.0f));

        Collections.sort(students, new AgeAscComparator());
        print(students);

        Collections.sort(students, new NameAscComparator());
        print(students);

        Collections.sort(students, new ScoreDescComparator());
        print(students);
    }

    public static void print(List<Student> students) {
        for (Student s : students) {
            System.out.println(s.getName() + " " + s.getAge() + " " + s.getScore());
        }
    }

    public static class AgeAscComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    public static class NameAscComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static class ScoreDescComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            if (Math.abs(o1.getScore() - o2.getScore()) < 0.001) {
                return 0;
            } else if (o1.getScore() < o2.getScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
