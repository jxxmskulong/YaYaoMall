package com.yayao.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.yayao.bean.MerCategory;
import com.yayao.dao.MerCategoryDao;


/**
 * 商品分类数据访问实现类
 * @author yy
 *
 */
@Repository("merCategoryDao")
public class MerCategoryDaoImpl implements MerCategoryDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
		 //return sessionFactory.openSession();

	}

	/**
	 * 新增商品分类 
	 */
	public void addMerCategory(MerCategory merCategory) {
		getSession().save(merCategory);

	}

	/**
	 * 更新商品分类 
	 */
	public void updateMerCategory(MerCategory merCategory) {
			getSession().update(merCategory);
	}


	/**
	 * 删除指定的商品分类 
	 */
	public void delMerCategory(Integer mercategoryid) {
		MerCategory merCategory = (MerCategory) getSession().get(MerCategory.class, mercategoryid);
			getSession().delete(merCategory);
	}

	/**
	 * 装载指定商户的商品分类 
	 */
	public MerCategory loadMerCategory(Integer mercategoryid) {
		MerCategory merCategory=null;
		Criteria c = getSession().createCriteria(MerCategory.class);
		c.add(Restrictions.eq("merCategoryId", mercategoryid));
		merCategory=(MerCategory) c.uniqueResult();
		return merCategory;
	}
	/**
	 * 浏览商户商品分类 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MerCategory> browseMerCategory(Integer sellerid) {
		List<MerCategory> list = new ArrayList<MerCategory>();
		Criteria userLevel = getSession().createCriteria(MerCategory.class);
		if(sellerid!=0){
			userLevel.add(Restrictions.eq("seller.sellerId", sellerid));
		}
		userLevel.addOrder(Order.asc("merCategoryId"));
		list = userLevel.list();
		return list;
	}
	/**
	 * 检查商户商品分类存在否
	 */
	public boolean chkMerCategory(Integer sellerid,String cateName) {
		boolean status = true;//true代表数据库已经存在
		MerCategory cate = null;
		Criteria c = getSession().createCriteria(MerCategory.class);
		c.add(Restrictions.eq("seller.sellerId", sellerid));
		c.add(Restrictions.eq("merCategoryName", cateName));
		 cate = (MerCategory) c.uniqueResult();
		if(cate==null){
			status=false;
			return status;
		}
		
		return status;
	}

}