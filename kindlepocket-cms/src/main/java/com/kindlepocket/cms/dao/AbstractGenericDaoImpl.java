package com.kindlepocket.cms.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.CommandResult;

/**
 * 
 * This abstract class must be inherited by all daoImpl class
 * 
 * @author jchen
 *
 * @param <T>
 */
public abstract class AbstractGenericDaoImpl<T> implements GenericDao<T> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected T t;

	@Autowired
	private MongoTemplate mongoTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hbo.woodmark.common.dao.GenericDao#query(org.springframework.data.
	 * mongodb.core.query.Query, java.lang.Class)
	 */
	@Override
	public List<T> query(Query query, Class<T> entityClass) {
		return mongoTemplate.find(query, entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hbo.woodmark.common.dao.GenericDao#querySingle(org.springframework.
	 * data.mongodb.core.query.Query, java.lang.Class)
	 */
	@Override
	public T querySingle(Query query, Class<T> entityClass) {
		return mongoTemplate.findOne(query, entityClass);
	}

	/*
	 * covered by aop to set modifiedBy and modifiedDate
	 * 
	 * @see com.hbo.woodmark.common.dao.GenericDao#saveFields(java.lang.String,
	 * org.springframework.data.mongodb.core.query.Update, java.lang.Class)
	 */
	@Override
	public void saveFields(String id, Update update, Class<T> entityClass) {
		mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), update, entityClass);
	}

	/*
	 * Only used to update, covered by aop to set modifiedBy and modifiedDate
	 * 
	 * @see
	 * com.hbo.woodmark.common.dao.GenericDao#findAndModify(org.springframework.
	 * data.mongodb.core.query.Query,
	 * org.springframework.data.mongodb.core.query.Update, java.lang.Class)
	 */
	@Override
	public T findAndModify(Query query, Update update, Class<T> entityClass) {
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.remove(false);// don't allow to delete by use this methon
		options.upsert(false);// only can be used to update instead of insert
		options.returnNew(true);// always return new object
		return mongoTemplate.findAndModify(query, update, options, entityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hbo.woodmark.common.dao.GenericDao#count(org.springframework.data.
	 * mongodb.core.query.Query, java.lang.Class)
	 */
	@Override
	public long count(Query query, Class<T> entityClass) {
		return mongoTemplate.count(query, entityClass);
	}

	@Override
	public boolean exists(Query query, Class<T> entityClass) {
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public CommandResult executeCommand(String jsonCommand) {
		CommandResult commandResult = mongoTemplate.executeCommand(jsonCommand);
		return commandResult;
	}

}
