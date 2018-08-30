package com.recycle.mongo.dao.impl;

import com.mongodb.WriteResult;
import com.recycle.mongo.dao.BaseDao;
import com.recycle.mongo.page.Page;
import com.recycle.mongo.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

    private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Autowired
    protected MongoTemplate template;

    /**
     * 获得泛型类
     */
    private Class<T> getEntityClass() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }

    @Override
    public T save(T entity) {
      template.insert(entity);
      return entity;
    }

    @Override
    public T findById(String id) {
       return template.findById(id,this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return template.findById(id,this.getEntityClass(),collectionName);
    }

    @Override
    public List<T> findAll() {
        return template.findAll(this.getEntityClass());
    }

    @Override
    public List<T> findAll(String collectionName) {
        return template.findAll(this.getEntityClass(),collectionName);
    }

    @Override
    public List<T> find(Query query) {
        return template.find(query,this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return template.findOne(query,this.getEntityClass());
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query) {
        query = (query==null)?new Query(Criteria.where("_id").exists(true)):query;
        long count = this.count(query);
        page.setTotalCount((int) count);
        int currentPage = page.getCurrentPage();
        int pageSize =  page.getPageSize();
        query.skip((currentPage-1)*pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.build(rows);
        return page;
    }

    @Override
    public long count(Query query) {
        return template.count(query,this.getEntityClass());
    }

    @Override
    public WriteResult update(Query query, Update update) {
      if (update == null){
          return null;
      }
      return template.updateMulti(query,update,this.getEntityClass());
    }

    @Override
    public T updateOne(Query query, Update update) {
        if (update == null){
            return null;
        }
        return template.findAndModify(query,update,this.getEntityClass());
    }

    @Override
    public WriteResult update(T entity) {
        Field[] fields = this.getEntityClass().getDeclaredFields();
        if (fields == null || fields.length == 0){
            return  null;
        }
        Field idField = null;
        for (Field field : fields){
            if (field.getName() != null && "id".equals(field.getName().toLowerCase())){
                idField = field;
                break;
            }
        }
        if (idField == null){
            return null;
        }
        idField.setAccessible(true);
        String id = null;
        try {
            id = (String) idField.get(entity);
        } catch (IllegalAccessException e) {
           log.error(e.getMessage(),e);
        }

        if (id == null || "".equals(id.trim())){
            return null;
        }

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = ReflectionUtils.getUpdateObj(entity);
        if (update == null){
            return null;
        }
        return template.updateFirst(query,update,getEntityClass());
    }

    @Override
    public void remove(Query query) {
        template.remove(query,this.getEntityClass());
    }
}
