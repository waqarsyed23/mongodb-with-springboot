package com.sample.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import com.sample.vo.RequestVO;
import com.sample.vo.UserVO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WS on 8/12/16.
 */
@Component
@Qualifier("userDAOMongo")
public class UserDAOMongo implements UserDAO {

    @Autowired
    MongoFactory mongoFactory;

    //name of collection created in mongo
    private static final String mongocollection = "user";

    /***
     * This method retrieves the user information from mongo db collection based on input criteria
     * @param requestVO
     * @return
     */
    @Override
    public UserVO getUser(RequestVO requestVO) {

        String userName = requestVO.getUsername();
        String password = requestVO.getPassword();
        String userRole = null;

        MongoCollection<Document> userCollection = mongoFactory.getCollection(mongocollection);

        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("username", userName));
        obj.add(new BasicDBObject("password", password));
        andQuery.put("$and", obj);
        System.out.println(andQuery.toString());

        Document userDoc = userCollection.find(andQuery).first();
        System.out.println(userDoc.toJson());

        if(userDoc !=null && !userDoc.isEmpty()){
            userRole = (String) userDoc.get("role");
            System.out.println(userRole);
        }

        return null;
    }


    /***
     * Add the user info in the mongo collection
     * @param userVO
     */
    public void addUser(UserVO userVO){

        MongoCollection<Document> userCollection = mongoFactory.getCollection(mongocollection);

        /**Check if user does not exist - If exist then dont add it*/
        BasicDBObject whereQuery = new BasicDBObject("email", userVO.getEmail());
        Document document = userCollection.find(whereQuery).first();

        if(document.isEmpty()){
            //Create the document and insert in the collection
            Document doc = new Document();
            doc.put("email", userVO.getEmail());
            doc.put("password", userVO.getPassword());
            doc.put("role", userVO.getRole()); //Path of image location e.g. D:/picture/...jpeg
            userCollection.insertOne(doc);
        }
    }

    /**
     * Update the collection record
     *
     * @param userVO
     */
    public void updateUser(UserVO userVO){

        MongoCollection<Document> userCollection = mongoFactory.getCollection(mongocollection);

        Document whereQuery = new Document("email", userVO.getEmail());

        //update the document and insert in the collection
        Document newDoc = new Document();
        newDoc.put("email", userVO.getEmail());
        newDoc.put("password", userVO.getPassword());
        newDoc.put("role", userVO.getRole());

        userCollection.findOneAndReplace(whereQuery, newDoc);;
    }

}
