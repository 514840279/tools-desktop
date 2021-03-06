package org.chuxue.desktop.tools.common.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsTabsJdbcInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsJdbcInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiDatasourceConfig {
	@Autowired
	SysDbmsTabsJdbcInfoDao sysDbmsTabsJdbcInfoDao;

	@SuppressWarnings("unchecked")
	public Map<String, DataSource> multiDatasource() {
		Map<String, DataSource> map = new HashMap<>();
		try {
			List<SysDbmsTabsJdbcInfo> list = sysDbmsTabsJdbcInfoDao.findAll();
			Class<DataSource> type = (Class<DataSource>) Class.forName("org.apache.tomcat.jdbc.pool.DataSource");
			for (SysDbmsTabsJdbcInfo sysZhcxAddr : list) {
				String driverClassName = "";
				String url = "";
				switch (sysZhcxAddr.getType()) {
				case "oracle":
					driverClassName = "oracle.jdbc.driver.OracleDriver";
					url = "jdbc:oracle:thin:@" + sysZhcxAddr.getIp() + ":" + sysZhcxAddr.getPort() + ":"
							+ sysZhcxAddr.getDatabaseName();
					break;
				case "mysql":
					// driverClassName = "com.mysql.jdbc.Driver";
					driverClassName = "com.mysql.cj.jdbc.Driver";
					url = "jdbc:mysql://" + sysZhcxAddr.getIp() + ":" + sysZhcxAddr.getPort() + "/"
							+ sysZhcxAddr.getDatabaseName()
							+ "?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false";
					break;
				default:
					driverClassName = "oracle.jdbc.driver.OracleDriver";
					url = "jdbc:oracle:thin:@" + sysZhcxAddr.getIp() + ":" + sysZhcxAddr.getPort() + ":"
							+ sysZhcxAddr.getDatabaseName();
					break;
				}
				DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
						.username(sysZhcxAddr.getUsername()).password(sysZhcxAddr.getPassword()).type(type).build();

				map.put(sysZhcxAddr.getUuid(), dataSource);
			}
		} catch (ClassNotFoundException e) {
			System.err.println(
					"???????????????jar??? ???tomcat-jdbc??? ???springboot2??????????????????????????? ???com.zaxxer.hikari.HikariDataSource????????????????????????????????????????????????");
		}
		return map;
	}

	/**
	 *  SQLException
	 * 	?????????: destroyMultiDatasource
	 * ??? ???:TODO(?????????????????????????????????????????????)
	 * ??? ??? : multiDatasource
	 * ??? ??????void
	 * ??? ???: Administrator
	 */
	void destroyMultiDatasource(Map<String, DataSource> multiDatasource) throws SQLException {
		Set<String> keySet = multiDatasource.keySet();
		for (String string : keySet) {
			multiDatasource.get(string).getConnection().close();
		}
	}
}
