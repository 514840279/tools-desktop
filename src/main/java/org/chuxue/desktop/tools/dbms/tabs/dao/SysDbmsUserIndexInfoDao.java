package org.chuxue.desktop.tools.dbms.tabs.dao;

import java.util.List;

import org.chuxue.desktop.tools.common.base.BaseDao;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsUserIndexInfo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 文件名 ： SysDicUserIndexCodeDao.java
 * 包 名 ： com.shumeng.application.zhcx.dao
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2018年3月8日下午1:48:02
 * 版 本 ： V1.0
 */
@Repository("sysDbmsUserIndexInfoDao")
@DynamicUpdate(true)
@DynamicInsert(true)
public interface SysDbmsUserIndexInfoDao extends BaseDao<SysDbmsUserIndexInfo> {

	/**
	 * 方法名： findAllByDeleteFlag
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数：
	 * 返 回：{@code List<SysDicUserIndexCode>}
	 * 作 者 ： Administrator
	 *
	 */
	@Query("select t from SysDbmsUserIndexInfo t where t.deleteFlag =1 order by userOrder ")
	List<SysDbmsUserIndexInfo> findAllByDeleteFlag();

	/**
	 * 方法名： findAllByChart
	 * 功能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参数 ：
	 * 返 回： List<SysDbmsUserIndexInfo>
	 * 作 者 ： Administrator
	 *
	 */
	@Query("select t from SysDbmsUserIndexInfo t where t.chart >0  order by userOrder ")
	List<SysDbmsUserIndexInfo> findAllByChart();

}
