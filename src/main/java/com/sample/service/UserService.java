package com.sample.service;

import com.sample.dao.UserDAO;
import com.sample.vo.RequestVO;
import com.sample.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by WS on 8/12/16.
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("userDAOMongo")
    UserDAO userDAO;

    /**
     *
     * @param userVO
     */
    public UserVO getUser(RequestVO userVO){

        UserVO user = userDAO.getUser(userVO);

        return user;
    }

    public void addUser(UserVO userVO){
        userDAO.addUser(userVO);
    }

    public void updateUser(UserVO userVO){
        userDAO.updateUser(userVO);
    }


}
