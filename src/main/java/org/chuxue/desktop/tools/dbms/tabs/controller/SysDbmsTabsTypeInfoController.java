package org.chuxue.desktop.tools.dbms.tabs.controller;

import java.util.List;

import org.chuxue.desktop.tools.common.base.BaseController;
import org.chuxue.desktop.tools.common.base.BaseControllerImpl;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsTypeInfo;
import org.chuxue.desktop.tools.dbms.tabs.service.SysDbmsTabsTypeInfoService;
import org.chuxue.desktop.tools.dbms.tabs.vo.SysDbmsTabsTypeInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件名 ： SysDbmsTabsTypeInfoController.java 
 * 包 名 ：tk.ainiyue.danyuan.application.dbm.type.controller 
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称： 
 * 技能ID ： 
 * 作 者 ： wang 
 * 时 间 ： 2017年8月3日 下午3:58:03 
 * 版 本 ： V1.0
 */
public class SysDbmsTabsTypeInfoController extends BaseControllerImpl<SysDbmsTabsTypeInfo>
		implements BaseController<SysDbmsTabsTypeInfo> {
	//
	private static final Logger logger = LoggerFactory.getLogger(SysDbmsTabsTypeInfoController.class);
	//
	@Autowired
	private SysDbmsTabsTypeInfoService sysDbmsTabsTypeInfoService;

	/**
	 * 方法名： findAll 
	 * 功 能： TODO(这里用一句话描述这个方法的作用) 
	 * 参 数： 返 回 : 
	 * 返 回：List<SysSeedInfo> 
	 * 作 者 ： Tenghui.Wang 
	 * 
	 */
	public List<SysDbmsTabsTypeInfo> findAll() {
		logger.info("findAll", SysDbmsTabsTypeInfoController.class);
		return sysDbmsTabsTypeInfoService.findAll();
	}

	public String sysTableTypeDeleteAll(SysDbmsTabsTypeInfoVo vo) {
		logger.info("sysTableTypeDeleteAll", SysDbmsTabsTypeInfoController.class);
		try {
			sysDbmsTabsTypeInfoService.deleteAll(vo.getList());
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

}
