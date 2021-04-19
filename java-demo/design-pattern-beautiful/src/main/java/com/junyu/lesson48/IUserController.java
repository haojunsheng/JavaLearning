package com.junyu.lesson48;

import com.junyu.lesson48.UserVo;

/**
 * @author haojunsheng
 * @date 2021/4/16 19:52
 */
public interface IUserController {
    UserVo login(String telephone, String password);

    UserVo register(String telephone, String password);
}
