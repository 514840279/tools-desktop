package org.chuxue.desktop.tools.dbms.tabs.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.chuxue.desktop.tools.common.base.BaseService;
import org.chuxue.desktop.tools.common.base.BaseServiceImpl;
import org.chuxue.desktop.tools.common.base.Pagination;
import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsTabsColsInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsTabsInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsColsInfo;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 文件名 ： SysDbmsTabsColsInfoService.java
 * 包 名 ：tk.ainiyue.danyuan.application.dbm.column.service.impl
 * 描 述 ：TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： wang
 * 时 间 ： 2017年8月3日 下午3:52:36
 * 版 本 ：V1.0
 */
@Service("sysDbmsTabsColsInfoService")
public class SysDbmsTabsColsInfoService extends BaseServiceImpl<SysDbmsTabsColsInfo>
		implements BaseService<SysDbmsTabsColsInfo> {
	private static final Logger logger = LoggerFactory.getLogger(SysDbmsTabsColsInfoService.class);
	//
	@Autowired
	private SysDbmsTabsColsInfoDao sysDbmsTabsColsInfoDao;
	@Autowired
	private SysDbmsTabsInfoDao sysDbmsTabsInfoDao;
	@Autowired
	private SysDbmsTabsInfoService sysDbmsTabsInfoService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 分页查询
	public Page<SysDbmsTabsColsInfo> findAllByTableUuid(int pageNumber, int pageSize, String searchText,
			String tableUuid) {
		logger.info(tableUuid, SysDbmsTabsColsInfoService.class);
		// Page<SysDbmsTabsColsInfo> list =
		// sysDbmsTabsColsInfoDao.findAllByTableUuid(tableUuid);
		SysDbmsTabsColsInfo info = new SysDbmsTabsColsInfo();
		info.setTabsUuid(tableUuid);
		Example<SysDbmsTabsColsInfo> example = Example.of(info);
		Sort sort = Sort.by(new Order(Direction.ASC, "colsOrder"));
		PageRequest request = this.buildPageRequest(pageNumber, pageSize, sort);
		Page<SysDbmsTabsColsInfo> sourceCodes = sysDbmsTabsColsInfoDao.findAll(example, request);
		return sourceCodes;

	}

	// 构建PageRequest
	private PageRequest buildPageRequest(int pageNumber, int pageSize, Sort sort) {
		return PageRequest.of(pageNumber - 1, pageSize, sort);
	}

	// 更新
	public void change(SysDbmsTabsColsInfo info) {
		try {
			SysDbmsTabsInfo tab = sysDbmsTabsInfoDao.findById(info.getTabsUuid()).get();
			Optional<SysDbmsTabsColsInfo> old = sysDbmsTabsColsInfoDao.findById(info.getUuid());
			if (old != null && old.isPresent()) {
				String sql = "alter table " + tab.getTabsName() + " CHANGE " + old.get().getColsName() + " "
						+ info.getColsName() + " " + info.getColsType() + "(" + info.getColsLength() + ")";
				jdbcTemplate.execute(sql);
			} else {

				String sql = "alter table " + tab.getTabsName() + " add " + info.getColsName() + " "
						+ info.getColsType() + "(" + info.getColsLength() + ")";
				jdbcTemplate.execute(sql);
			}
		} finally {
			sysDbmsTabsColsInfoDao.save(info);

		}
	}

	public void deleteSysDbmsTabsColsInfo(List<SysDbmsTabsColsInfo> list) {

		Optional<SysDbmsTabsInfo> tab = sysDbmsTabsInfoDao.findById(list.get(0).getTabsUuid());
		if (tab.isPresent()) {
			for (SysDbmsTabsColsInfo SysDbmsTabsColsInfo : list) {
				try {
					// alter table user DROP COLUMN new2;
					String sql = "alter table " + tab.get().getTabsName() + " DROP COLUMN "
							+ SysDbmsTabsColsInfo.getColsName();
					jdbcTemplate.execute(sql);
				} finally {
					sysDbmsTabsColsInfoDao.delete(SysDbmsTabsColsInfo);
				}
			}
		}
	}

	public List<SysDbmsTabsColsInfo> findAllBySysDbmsTabsColsInfo(SysDbmsTabsColsInfo info) {
		Example<SysDbmsTabsColsInfo> example = Example.of(info);
		return sysDbmsTabsColsInfoDao.findAll(example);
	}

	public void saveSysDbmsTabsColsInfo(List<SysDbmsTabsColsInfo> list) {
		sysDbmsTabsColsInfoDao.saveAll(list);
	}

	/**
	 * 方法名 ： findAll
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： info
	 * 参 数 ：
	 * 参 考 ： tk.ainiyue.danyuan.application.common.base.BaseService#findAll(java.lang.Object)
	 * 作 者 ： Administrator
	 */

	@Override
	public List<SysDbmsTabsColsInfo> findAll(SysDbmsTabsColsInfo info) {
		Sort sort = Sort.by(Order.asc("colsOrder"));
		Example<SysDbmsTabsColsInfo> example = Example.of(info);
		return sysDbmsTabsColsInfoDao.findAll(example, sort);
	}

	/**
	 * 方法名 ： page
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： pageNumber
	 * 参 数 ： pageSize
	 * 参 数 ： info
	 * 参 数 ： map
	 * 参 数 ： order
	 * 参  数 ：
	 * 参 考 ： tk.ainiyue.danyuan.application.common.base.BaseService#page(int, int,java.lang.Object, java.util.Map,org.springframework.data.domain.Sort.Order[])
	 * 作 者 ： Administrator
	 */

	@Override
	public Page<SysDbmsTabsColsInfo> page(Pagination<SysDbmsTabsColsInfo> vo) {
		Order order;
		if (vo.getSortName() != null) {
			if (vo.getSortOrder().equals("desc")) {
				order = Order.desc(vo.getSortName());
			} else {
				order = Order.asc(vo.getSortName());
			}
		} else {
			order = new Order(Direction.DESC, "createTime");
		}
		if (vo.getInfo() == null) {
			vo.setInfo(new SysDbmsTabsColsInfo());
		}

		Example<SysDbmsTabsColsInfo> example = Example.of(vo.getInfo());
		Sort sort = Sort.by(order);
		PageRequest request = PageRequest.of(vo.getPageNumber() - 1, vo.getPageSize(), sort);
		Page<SysDbmsTabsColsInfo> page = sysDbmsTabsColsInfoDao.findAll(example, request);
		return page;
	}

	/**
	 * 方法名 ： save
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： list
	 * 参 考 ： tk.ainiyue.danyuan.application.common.base.BaseService#save(java.util.List)
	 * 作 者 ： Administrator
	 */

	@Override
	public void saveAll(List<SysDbmsTabsColsInfo> list) {
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : list) {
			if (sysDbmsTabsColsInfo.getUuid() == null) {
				sysDbmsTabsColsInfo.setUuid(UUID.randomUUID().toString());
			}
			change(sysDbmsTabsColsInfo);
		}
	}

	/**
	 * 方法名 ： delete
	 * 功 能 ： TODO(这里用一句话描述这个方法的作用)
	 * 参 数 ： list
	 * 参 考 ： tk.ainiyue.danyuan.application.common.base.BaseService#delete(java.util.List)
	 * 作 者 ： Administrator
	 */

	@Override
	public void deleteAll(List<SysDbmsTabsColsInfo> list) {
		sysDbmsTabsColsInfoDao.deleteAll(list);
	}

	/**
	 * 方法名 pagev
	 * 功能 TODO(这里用一句话描述这个方法的作用)
	 * 参数  vo
	 * 参数
	 * 返回 List<Map<String,Object>>
	 * author Administrator
	 * 
	 */
	public List<SysDbmsTabsColsInfo> pagev(String uuid) {
		SysDbmsTabsInfo tabs = new SysDbmsTabsInfo();
		tabs.setUuid(uuid);
		tabs = sysDbmsTabsInfoService.findOne(tabs);

		// SysDbmsTabsJdbcInfo jdbc = new SysDbmsTabsJdbcInfo();
		// jdbc.setUuid(tabs.getJdbcUuid());
		// jdbc = sysDbmsTabsJdbcInfoService.findOne(jdbc);
		// Map<String, String> param = new HashMap<>();
		StringBuilder pageSql = new StringBuilder();
		pageSql.append(" SELECT  ");
		pageSql.append("   UUID() AS 'uuid', ");
		pageSql.append("   '" + tabs.getUuid() + "' as tabsUuid,");
		pageSql.append("   CONCAT(t.`TABLE_SCHEMA`, '.', t.`TABLE_NAME`) AS tabsName, ");
		pageSql.append("   t.`COLUMN_NAME` AS colsName, ");
		pageSql.append("   t.`ORDINAL_POSITION` AS colsOrder, ");
		pageSql.append("   t.`DATA_TYPE` AS colsType, ");
		pageSql.append("   t.`COLUMN_COMMENT` AS colsDesc  ");
		pageSql.append(" FROM  `information_schema`.`COLUMNS` t  ");
		pageSql.append(" WHERE CONCAT(t.`TABLE_SCHEMA`,'.',t.`TABLE_NAME`) = '" + tabs.getTabsName() + "'  ");
		pageSql.append(" and t.`COLUMN_NAME` not in( ");
		pageSql.append(" 		select c.cols_name from application.sys_dbms_tabs_cols_info c ");
		pageSql.append(" 		where c.tabs_uuid='" + tabs.getUuid() + "'");
		pageSql.append(" 	)");

		pageSql.append(" ORDER BY t.`ORDINAL_POSITION`  ");

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		// List<Map<String, Object>> list =
		// template.queryForList(pageSql.toString(), param);
		List<SysDbmsTabsColsInfo> list = template.getJdbcOperations().query(pageSql.toString(),
				new BeanPropertyRowMapper<>(SysDbmsTabsColsInfo.class));
		return list;
	}
}
