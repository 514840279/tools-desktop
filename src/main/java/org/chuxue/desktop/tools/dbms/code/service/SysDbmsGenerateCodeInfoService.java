package org.chuxue.desktop.tools.dbms.code.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.chuxue.desktop.tools.common.base.BaseService;
import org.chuxue.desktop.tools.common.base.BaseServiceImpl;
import org.chuxue.desktop.tools.common.utils.CompressFile;
import org.chuxue.desktop.tools.common.utils.FileDelete;
import org.chuxue.desktop.tools.common.utils.TxtFilesWriter;
import org.chuxue.desktop.tools.dbms.code.po.SysDbmsGenerateCodeInfo;
import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsTabsColsInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.dao.SysDbmsTabsInfoDao;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsColsInfo;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 文件名 SysDbmsGenerateCodeInfoService.java
 * 包名 org.danyuan.application.dbms.code.service
 * 描述 TODO(用一句话描述该文件做什么)
 * 时间 2019年1月16日 下午1:25:04
 * 作 者: Administrator
 * 版 本： V1.0
 */
@Service
public class SysDbmsGenerateCodeInfoService extends BaseServiceImpl<SysDbmsGenerateCodeInfo>
		implements BaseService<SysDbmsGenerateCodeInfo> {
	@Autowired
	private SysDbmsTabsColsInfoDao sysDbmsTabsColsInfoDao;
	@Autowired
	private SysDbmsTabsInfoDao sysDbmsTabsInfoDao;

	public String OUTPUTFILE = "outputfile";

	/**
	  FileNotFoundException
	 * 方法名 generate
	 * 功能 TODO(这里用一句话描述这个方法的作用)
	 * 参数  list
	 * 参数  username
	 * 参数  pathString
	 * 返回 void
	 * 作 者: Administrator
	 * 
	 */
	public void generate(List<SysDbmsGenerateCodeInfo> list, String username, String pathString)
			throws FileNotFoundException {
		String path = System.getProperty("user.dir") + "/" + OUTPUTFILE + "/" + pathString;
		File file = new File(path);
		for (SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo : list) {
			// javafile 路径
			String pathtempString = path + "/src/main/java/" + sysDbmsGenerateCodeInfo.getClassPath().replace(".", "/");
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			SysDbmsTabsColsInfo colsInfo = new SysDbmsTabsColsInfo();
			colsInfo.setTabsUuid(sysDbmsGenerateCodeInfo.getUuid());
			Example<SysDbmsTabsColsInfo> example = Example.of(colsInfo);
			List<SysDbmsTabsColsInfo> colsInfos = sysDbmsTabsColsInfoDao.findAll(example);

			SysDbmsTabsInfo tabsInfo = new SysDbmsTabsInfo();
			tabsInfo = sysDbmsTabsInfoDao.findById(sysDbmsGenerateCodeInfo.getUuid()).get();

			// 实体类生成
			if (sysDbmsGenerateCodeInfo.getGenerateEntity() == 1) {
				file = new File(pathtempString + "/po");
				if (!file.exists()) {
					file.mkdirs();
				}
				GenerateEntity.generate(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString + "/po");
			}
			// dao类生成
			if (sysDbmsGenerateCodeInfo.getGenerateDao() == 1) {
				file = new File(pathtempString + "/dao");
				if (!file.exists()) {
					file.mkdirs();
				}
				getGenerateDao(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString + "/dao");
			}
			// service类生成
			if (sysDbmsGenerateCodeInfo.getGenerateService() == 1) {
				file = new File(pathtempString + "/service");
				if (!file.exists()) {
					file.mkdirs();
				}
				getGenerateService(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString + "/service");
			}
			// controller类生成
			if (sysDbmsGenerateCodeInfo.getGenerateController() == 1) {
				file = new File(pathtempString + "/controller");
				if (!file.exists()) {
					file.mkdirs();
				}
				getGenerateController(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username,
						pathtempString + "/controller");
			}

			String thirdString = "";
			String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
			for (int i = 0; i < 3; i++) {
				thirdString += subpathString[i] + ".";
			}
			// html类生成
			if (sysDbmsGenerateCodeInfo.getGenerateHtml() == 1) {
				// static 资源文件路径
				pathtempString = path + "/src/main/resources/static/pages/" + sysDbmsGenerateCodeInfo.getClassPath()
						.replace(thirdString, "").replace(".", "/").toLowerCase();
				file = new File(pathtempString);
				if (!file.exists()) {
					file.mkdirs();
				}
				// html类生成
				GenerateHtml.generate(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);
				// js类生成
				GenerateJs.generate(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);
			}

			// detailhtml类生成
			if (sysDbmsGenerateCodeInfo.getGenerateDetail() == 1) {
				// templates 模板路径
				pathtempString = path + "/src/main/resources/templates/" + sysDbmsGenerateCodeInfo.getClassPath()
						.replace(thirdString, "").replace(".", "/").toLowerCase();
				file = new File(pathtempString);
				if (!file.exists()) {
					file.mkdirs();
				}
				GenerateHtml.generateDetail(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);

				// js类生成
				// static 资源文件路径
				pathtempString = path + "/src/main/resources/static/pages/" + sysDbmsGenerateCodeInfo.getClassPath()
						.replace(thirdString, "").replace(".", "/").toLowerCase();
				file = new File(pathtempString);
				if (!file.exists()) {
					file.mkdirs();
				}
				GenerateJs.generateDetail(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);
			}

			// Sql 语句
			if (sysDbmsGenerateCodeInfo.getGenerateSql() == 1) {
				// sql 脚本文件路径
				pathtempString = path + "/src/main/resources/sql/";
				file = new File(pathtempString);
				if (!file.exists()) {
					file.mkdirs();
				}
				// Sql ddl 语句
				GenerateSql.generate(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);
				// Sql 管理员权限 语句
				GenerateSql.generateConfig(sysDbmsGenerateCodeInfo, tabsInfo, colsInfos, username, pathtempString);
			}

			// 数据文当 接口文档 功能介绍

		}

		// 打包文件
		FileOutputStream fos1 = new FileOutputStream(new File(path + ".zip"));
		CompressFile.toZip(path, fos1, true);

		// 清空 文件夹 TODO
		FileDelete.delFolder(path);
	}

	/**
	 * 方法名 getGenerateController
	 * 功能 生成controller
	 * 参数 sysDbmsGenerateCodeInfo
	 * 参数 tabsInfo
	 * 参数 colsInfos
	 * 参数 username
	 * 参数 pathString
	 * 返回 void
	 * 作 者: Administrator
	 * 
	 */
	private void getGenerateController(SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo, SysDbmsTabsInfo tabsInfo,
			List<SysDbmsTabsColsInfo> colsInfos, String username, String pathString) {
		String thirdString = "";
		String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
		for (int i = 0; i < 3; i++) {
			thirdString += subpathString[i] + ".";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("package " + sysDbmsGenerateCodeInfo.getClassPath() + ".controller;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("import " + thirdString + "common.base.BaseController;\r\n");
		stringBuilder.append("import " + thirdString + "common.base.BaseControllerImpl;\r\n");
		stringBuilder.append("import " + sysDbmsGenerateCodeInfo.getClassPath() + ".po."
				+ sysDbmsGenerateCodeInfo.getClassName() + ";\r\n");
		stringBuilder.append("import " + sysDbmsGenerateCodeInfo.getClassPath() + ".service."
				+ sysDbmsGenerateCodeInfo.getClassName() + "Service;\r\n");
		stringBuilder.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		stringBuilder.append("import org.springframework.web.bind.annotation.GetMapping;\r\n");
		stringBuilder.append("import org.springframework.web.bind.annotation.PathVariable;\r\n");
		stringBuilder.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
		stringBuilder.append("import org.springframework.web.bind.annotation.RestController;\r\n");
		stringBuilder.append("import org.springframework.web.servlet.ModelAndView;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("/**\r\n");
		stringBuilder.append(" * 文件名： " + sysDbmsGenerateCodeInfo.getClassName() + "Controller.java\r\n");
		stringBuilder.append(" * 包 名： " + sysDbmsGenerateCodeInfo.getClassPath() + ".controller\r\n");
		stringBuilder.append(" * 描 述： controller层\r\n");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		stringBuilder.append(" * 时 间： " + simpleDateFormat.format(new Date()) + "\r\n");
		stringBuilder.append(" * 作 者: " + username + "\r\n");
		stringBuilder.append(" * 版 本： V1.0\r\n");
		stringBuilder.append(" */\r\n");
		stringBuilder.append("@RestController\r\n");
		String subServiceNameString = sysDbmsGenerateCodeInfo.getClassName().substring(0, 1).toLowerCase()
				+ sysDbmsGenerateCodeInfo.getClassName().substring(1);
		stringBuilder.append("@RequestMapping(\"/" + subServiceNameString + "\")\r\n");
		stringBuilder.append("public class " + sysDbmsGenerateCodeInfo.getClassName()
				+ "Controller extends BaseControllerImpl<" + sysDbmsGenerateCodeInfo.getClassName()
				+ "> implements BaseController<" + sysDbmsGenerateCodeInfo.getClassName() + "> {\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("	@Autowired\r\n");
		stringBuilder.append(
				"	" + sysDbmsGenerateCodeInfo.getClassName() + "Service " + subServiceNameString + "Service;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("	@GetMapping(\"/detail/{uuid}\")\r\n");
		stringBuilder.append("	public ModelAndView name(@PathVariable(\"uuid\") String uuid) {\r\n");
		stringBuilder.append("		ModelAndView modelAndView = new ModelAndView(\""
				+ sysDbmsGenerateCodeInfo.getClassPath().replace(thirdString, "").replace(".", "/") + "/"
				+ subServiceNameString.toLowerCase() + "detail\");\r\n");
		stringBuilder.append("		" + sysDbmsGenerateCodeInfo.getClassName() + " info = new "
				+ sysDbmsGenerateCodeInfo.getClassName() + "();\r\n");
		stringBuilder.append("		info.setUuid(uuid);\r\n");
		stringBuilder.append("		modelAndView.addObject(\"" + subServiceNameString + "\", " + subServiceNameString
				+ "Service.findOne(info));\r\n");
		stringBuilder.append("		return modelAndView;\r\n");
		stringBuilder.append("	}\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("}");

		// 文件写入
		String fineName = pathString + "/" + sysDbmsGenerateCodeInfo.getClassName() + "Controller.java";
		TxtFilesWriter.writeToFile(stringBuilder.toString(), fineName);
	}

	/**
	 * 方法名 getGenerateService
	 * 功能 service层代码生成
	 * 参数 sysDbmsGenerateCodeInfo
	 * 参数 tabsInfo
	 * 参数 colsInfos
	 * 参数 username
	 * 参数 pathString
	 * 返回 void
	 * 作 者: Administrator
	 * 
	 */
	private void getGenerateService(SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo, SysDbmsTabsInfo tabsInfo,
			List<SysDbmsTabsColsInfo> colsInfos, String username, String pathString) {
		String thirdString = "";
		String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
		for (int i = 0; i < 3; i++) {
			thirdString += subpathString[i] + ".";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("package " + sysDbmsGenerateCodeInfo.getClassPath() + ".service;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("import " + thirdString + "common.base.BaseService;\r\n");
		stringBuilder.append("import " + thirdString + "common.base.BaseServiceImpl;\r\n");
		stringBuilder.append("import " + sysDbmsGenerateCodeInfo.getClassPath() + ".po."
				+ sysDbmsGenerateCodeInfo.getClassName() + ";\r\n");
		// stringBuilder.append("import " +
		// sysDbmsGenerateCodeInfo.getClassPath() + ".dao." +
		// sysDbmsGenerateCodeInfo.getClassName() + "Dao;\r\n");
		stringBuilder.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
		stringBuilder.append("import org.springframework.stereotype.Service;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("/**\r\n");
		stringBuilder.append(" * 文件名： " + sysDbmsGenerateCodeInfo.getClassName() + "Service.java\r\n");
		stringBuilder.append(" * 包 名： " + sysDbmsGenerateCodeInfo.getClassPath() + ".service\r\n");
		stringBuilder.append(" * 描 述： service层\r\n");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		stringBuilder.append(" * 时 间： " + simpleDateFormat.format(new Date()) + "\r\n");
		stringBuilder.append(" * 作 者: " + username + "\r\n");
		stringBuilder.append(" * 版 本： V1.0\r\n");
		stringBuilder.append(" */\r\n");
		stringBuilder.append("@Service\r\n");
		stringBuilder.append("public class " + sysDbmsGenerateCodeInfo.getClassName()
				+ "Service extends BaseServiceImpl<" + sysDbmsGenerateCodeInfo.getClassName()
				+ "> implements BaseService<" + sysDbmsGenerateCodeInfo.getClassName() + "> {\r\n");
		// stringBuilder.append(" @Autowired\r\n");
		// stringBuilder.append(" private " +
		// sysDbmsGenerateCodeInfo.getClassName() + "Dao " +
		// sysDbmsGenerateCodeInfo.getClassName().substring(0, 1).toLowerCase()
		// + sysDbmsGenerateCodeInfo.getClassName().substring(1) + "Dao;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("");

		// 文件写入
		String fineName = pathString + "/" + sysDbmsGenerateCodeInfo.getClassName() + "Service.java";
		TxtFilesWriter.writeToFile(stringBuilder.toString(), fineName);
	}

	/**
	 * 方法名 getGenerateDao
	 * 功能 dao层代码生成
	 * 参数 sysDbmsGenerateCodeInfo
	 * 参数 tabsInfo
	 * 参数 colsInfos
	 * 参数 username
	 * 参数 pathString
	 * 返回 void
	 * author Administrator
	 
	 */
	private void getGenerateDao(SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo, SysDbmsTabsInfo tabsInfo,
			List<SysDbmsTabsColsInfo> colsInfos, String username, String pathString) {
		String thirdString = "";
		String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
		for (int i = 0; i < 3; i++) {
			thirdString += subpathString[i] + ".";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("package " + sysDbmsGenerateCodeInfo.getClassPath() + ".dao;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("import " + thirdString + "common.base.BaseDao;\r\n");
		stringBuilder.append("import " + sysDbmsGenerateCodeInfo.getClassPath() + ".po."
				+ sysDbmsGenerateCodeInfo.getClassName() + ";\r\n");
		stringBuilder.append("import org.springframework.stereotype.Repository;\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("/**\r\n");
		stringBuilder.append(" * 文件名： " + sysDbmsGenerateCodeInfo.getClassName() + "Dao.java\r\n");
		stringBuilder.append(" * 包 名： " + sysDbmsGenerateCodeInfo.getClassPath() + ".dao\r\n");
		stringBuilder.append(" * 描 述： dao层\r\n");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		stringBuilder.append(" * 时 间： " + simpleDateFormat.format(new Date()) + "\r\n");
		stringBuilder.append(" * 作 者: " + username + "\r\n");
		stringBuilder.append(" * 版 本： V1.0\r\n");
		stringBuilder.append(" */\r\n");
		stringBuilder.append("@Repository\r\n");
		stringBuilder.append("public interface " + sysDbmsGenerateCodeInfo.getClassName() + "Dao extends BaseDao<"
				+ sysDbmsGenerateCodeInfo.getClassName() + "> {\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("");

		// 文件写入
		String fineName = pathString + "/" + sysDbmsGenerateCodeInfo.getClassName() + "Dao.java";
		TxtFilesWriter.writeToFile(stringBuilder.toString(), fineName);

	}

}
