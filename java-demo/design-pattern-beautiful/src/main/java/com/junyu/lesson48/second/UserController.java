package com.junyu.lesson48.second;

import com.junyu.lesson48.IUserController;
import com.junyu.lesson48.UserVo;

/**
 * @author haojunsheng
 * @date 2021/4/16 19:52
 */
public class UserController implements IUserController {
    @Override
    public UserVo login(String telephone, String password) {
        //...省略login逻辑...
        // ...返回UserVo数据..
        return null;
    }

    @Override
    public UserVo register(String telephone, String password) {
        //...省略register逻辑...
        // ...返回UserVo数据...
        return null;
    }
}
