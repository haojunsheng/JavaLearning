package com.hjs.thinking.in.spring.ioc.overview.domain;

import com.hjs.thinking.in.spring.ioc.overview.annotation.Super;

/**
 * @author 俊语
 * @date 2021/6/8 下午10:39
 */
@Super
public class SuperUser extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}

