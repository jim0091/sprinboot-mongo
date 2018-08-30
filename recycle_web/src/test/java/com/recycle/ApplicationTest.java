package com.recycle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.recycle.mongo.dao.impl.BusinessDao;
import com.recycle.mongo.model.Business;
import com.recycle.mongo.page.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

/**
 *  mongoDb_test
 *
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    private static final  String host = "127.0.0.1";
    private static final  int port = 27017;
    private static final String mongo_db_name = "mongo_db";
    private static final String collcetion_name = "mongo_db";

    @Autowired
    private BusinessDao businessDao;

    public DB getDb(){
        Mongo client = new Mongo(host,port);
        DB db  = client.getDB(mongo_db_name);
        return db;
    }

    /**
     * 测试mongoDb链接
     */
    @Test
    public void testMongoConnection(){
        DB db  = getDb();
       Set<String> collectionNames = db.getCollectionNames();
       if (collectionNames != null){
           for (String name : collectionNames){
               System.out.println("collectionName=="+name);
           }
       }
    }

    /**
     * 测试数据插入
     */
    @Test
    public void testInsert(){

      for (int i=1;i<1000000000;i++){
          Business business = new Business();
          business.setLat(22.1234123423);
          business.setLng(113.123423423);
          business.setOpenid(i);
          businessDao.save(business);
      }
    }

    /**
     * 查询所有的数据
     */
    @Test
    public void  findAll(){
        List<Business> list =  businessDao.findAll();
        if (list != null){

            for (Business business : list){
                System.out.println(JSONObject.toJSONString(business));
            }
        }
    }

    /**
     * 查询一条
     */
    @Test
    public void findById(){
        Business business = businessDao.findById("5b5196fd1a33246a30f0c184","business");
        System.out.println(JSONObject.toJSONString(business));
    }

    @Test
    public void remove(){
        Query query = Query.query(Criteria.where("_id").is("5b51959f1a3324a35c7e9dc0"));
        businessDao.remove(query);
    }

    //测试数据获取(page);
    @Test
    public void test8(){
        Query query = new Query();
        Page<Business> page = new Page<Business>();
		page.setPageSize(20); //设置分页记录数
		page.setCurrentPage(2);	//设置当前页面
        Page<Business> pages = businessDao.findPage(page, query);
        List<Business> list = pages.getRows();
        if(list !=null && list.size()>0){
            for(Business business :list){
                System.out.println(JSONObject.toJSONString(business));

            }
        }

    }


    @Test
    public void testUpdate(){
        Query query = new Query(Criteria.where("_id").is("5b5196fd1a33246a30f0c155"));
        Update update = new Update();
        update.set("openid",21);
        businessDao.updateOne(query,update);
    }
    }
