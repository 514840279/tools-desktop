package org.chuxue.desktop.tools.dbms.tabs.service;

import java.util.List;

import org.chuxue.desktop.tools.common.base.BaseService;
import org.chuxue.desktop.tools.common.base.BaseServiceImpl;
import org.chuxue.desktop.tools.common.base.Pagination;
import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsAdviMessInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsAdviMessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 文件名 ： SysDbmsAdviMessInfoService.java
 * 包 名 ：com.shumeng.application.application.zhcx.service
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2018年4月27日 下午5:27:33
 * 版 本 ： V1.0
 */
@Service
public class SysDbmsAdviMessInfoService extends BaseServiceImpl<SysDbmsAdviMessInfo>
		implements BaseService<SysDbmsAdviMessInfo> {
	@Autowired
	SysDbmsAdviMessInfoDao sysDbmsAdviMessInfoDao;

	/**
	 * 方法名 ： findOne
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： info
	 * 参 数 ：
	 * 参 考 ： com.shumeng.application.common.base.BaseService#findOne(java.lang.Object)
	 * 作 者 ： Administrator
	 */

	@Override
	public SysDbmsAdviMessInfo findOne(SysDbmsAdviMessInfo info) {
		return null;
	}

	/**
	 * 方法名 ： findAll
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： info
	 * 参 数 ：
	 * 参 考 ： com.shumeng.application.common.base.BaseService#findAll(java.lang.Object)
	 * 作 者 ： Administrator
	 */

	@Override
	public List<SysDbmsAdviMessInfo> findAll(SysDbmsAdviMessInfo info) {

		return sysDbmsAdviMessInfoDao.findByDeleteFlag(0);
	}

	/**
	 * 方法名 ： save 功 能 ： TODO(这里用一句话描述这个方法的作用) 参 数 ： 参 数 : list 参 考 ： 
	 * com.shumeng.application.common.base.BaseService#save(java.util.List) 作 者
	 * ： Administrator
	 */

	@Override
	public void saveAll(List<SysDbmsAdviMessInfo> list) {
		// TODO Auto-generated method stub

	}

	/**
	 * 方法名 ： delete 功 能 ： TODO(这里用一句话描述这个方法的作用) 参 数 ： 参 数 : info 参 考 ： 
	 * com.shumeng.application.common.base.BaseService#delete(java.lang.Object)
	 * 作 者 ： Administrator
	 */

	@Override
	public void delete(SysDbmsAdviMessInfo info) {
		// TODO Auto-generated method stub

	}

	/**
	 * 方法名 ： delete 功 能 ： TODO(这里用一句话描述这个方法的作用) 参 数 ： 参 数 : list 参 考 ： 
	 * com.shumeng.application.common.base.BaseService#delete(java.util.List) 作
	 * 者 ： Administrator
	 */

	@Override
	public void deleteAll(List<SysDbmsAdviMessInfo> list) {
		// TODO Auto-generated method stub

	}

	/**
	 * 方法名 ： trunc 功 能 ： TODO(这里用一句话描述这个方法的作用) 参 数 ： 参 考 ： 
	 * com.shumeng.application.common.base.BaseService#trunc() 作 者 ：
	 * Administrator
	 */

	@Override
	public void trunc() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<SysDbmsAdviMessInfo> page(Pagination<SysDbmsAdviMessInfo> vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
