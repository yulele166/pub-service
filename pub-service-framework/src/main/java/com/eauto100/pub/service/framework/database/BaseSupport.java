package com.eauto100.pub.service.framework.database;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseSupport<T> {
	protected final Logger logger = Logger.getLogger(getClass());

	private IDBRouter baseDBRouter;
	protected IDaoSupport<T> baseDaoSupport;
//	@Autowired
	protected IDaoSupport<T> daoSupport;

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	protected String getTableName(String moude) {
		return baseDBRouter.getTableName(moude);

	}
 

	public IDaoSupport<T> getBaseDaoSupport() {
		return baseDaoSupport;
	}

	public void setBaseDaoSupport(IDaoSupport<T> baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}

	public void setDaoSupport(IDaoSupport<T> daoSupport) {
		this.daoSupport = daoSupport;
	}

	public IDBRouter getBaseDBRouter() {
		return baseDBRouter;
	}

	public void setBaseDBRouter(IDBRouter baseDBRouter) {
		this.baseDBRouter = baseDBRouter;
	}
}
