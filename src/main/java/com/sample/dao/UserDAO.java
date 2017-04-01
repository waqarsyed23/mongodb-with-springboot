package com.sample.dao;


import com.sample.vo.RequestVO;
import com.sample.vo.UserVO;

/**
 * Created by WS on 6/12/16.
 */
public interface UserDAO {

    public UserVO getUser(RequestVO requestVO);
    public void addUser(UserVO userVO);
    public void updateUser(UserVO userVO);
}
