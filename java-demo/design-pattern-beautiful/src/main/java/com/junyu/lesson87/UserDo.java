package com.junyu.lesson87;

/**
 * @author haojunsheng
 * @date 2021/5/10 20:16
 */
public class UserDo {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private String name;
    private String telephone;

    @Override
    public String toString() {
        return "UserDo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
