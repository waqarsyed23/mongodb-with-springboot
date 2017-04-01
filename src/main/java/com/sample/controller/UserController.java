package com.sample.controller;

import com.sample.service.UserService;
import com.sample.util.RequestValidator;
import com.sample.vo.RequestVO;
import com.sample.vo.ResponseVO;
import com.sample.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.sample.util.APIMessage.BACKEND_SERVER_EXCEPTION;
import static com.sample.util.APIMessage.REQUEST_VALIDATION_FAIL;


/**
 * Created by WS on 8/12/16.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RequestValidator requestValidator;

    @Autowired
    ResponseVO responseVO;

    String response=null;

    /**
     * validates the login and return the role of the user
     * @param requestVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO getUserInfo(@RequestBody RequestVO requestVO){

        if(!requestValidator.validateRequest(requestVO)){
            responseVO.setStatus(ResponseVO.FAIL);
            responseVO.setMessage(REQUEST_VALIDATION_FAIL);
            responseVO.setUserVO(null);
            return responseVO;
        }
        try {
            UserVO userVO = userService.getUser(requestVO);

            responseVO.setUserVO(userVO);
            responseVO.setStatus(ResponseVO.SUCCESS);
        } catch (Exception e) {
            responseVO.setUserVO(null);
            responseVO.setStatus(ResponseVO.FAIL);
            responseVO.setMessage(BACKEND_SERVER_EXCEPTION);
        }
        return responseVO;
    }

}
