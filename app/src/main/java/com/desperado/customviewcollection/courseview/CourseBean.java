package com.desperado.customviewcollection.courseview;

/**
 *
 */
public class CourseBean {
    private String name;
    private int week;
    private int section;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "name='" + name + '\'' +
                ", week=" + week +
                ", section=" + section +
                '}';
    }
}
