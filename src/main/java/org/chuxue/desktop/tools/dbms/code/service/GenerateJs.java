package org.chuxue.desktop.tools.dbms.code.service;

import java.util.List;

import org.chuxue.desktop.tools.common.utils.TxtFilesWriter;
import org.chuxue.desktop.tools.dbms.code.po.SysDbmsGenerateCodeInfo;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsColsInfo;
import org.chuxue.desktop.tools.dbms.tabs.po.SysDbmsTabsInfo;

/**
 * 文件名 GenerateJs.java
 * 包名 org.danyuan.application.dbms.code.service
 * 描述 TODO(用一句话描述该文件做什么)
 * 时间 2019年1月17日 下午2:27:44
 * author Administrator
 * 版本 V1.0
 */
public class GenerateJs {

	/**
	 * 方法名 generate
	 * 功能 TODO(这里用一句话描述这个方法的作用)
	 * 参数  sysDbmsGenerateCodeInfo
	 * 参数  tabsInfo
	 * 参数  colsInfos
	 * 参数  username
	 * 参数  pathString
	 * 返回 void
	 * 作 者: Administrator
	 * 
	 */
	public static void generate(SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo, SysDbmsTabsInfo tabsInfo,
			List<SysDbmsTabsColsInfo> colsInfos, String username, String pathString) {
		String thirdString = "";
		String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
		for (int i = 0; i < 3; i++) {
			thirdString += subpathString[i] + ".";
		}
		String tabsNameString = sysDbmsGenerateCodeInfo.getClassName().substring(0, 1).toLowerCase()
				+ sysDbmsGenerateCodeInfo.getClassName().substring(1);
		String subNameIdString = sysDbmsGenerateCodeInfo.getClassPath().toLowerCase().replace(thirdString, "")
				.replace(".", "_") + "_" + tabsNameString;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("window." + tabsNameString + "OperateEvents = {\r\n");
		stringBuilder.append("	// 修改\r\n");
		stringBuilder.append("	'click #clickA ': function (e, value, row, index) {\r\n");
		stringBuilder.append("		var url=\"/" + tabsNameString + "/detail/\"+row['uuid'];\r\n");
		stringBuilder.append("		loadPage(url);\r\n");
		stringBuilder.append("	},\r\n");
		stringBuilder.append("	// 下载\r\n");
		stringBuilder.append("	'click #clickB ': function (e, value, row, index) {\r\n");
		stringBuilder.append("		var url=\"/" + tabsNameString + "/exportOne/\"+row['uuid'];\r\n");
		stringBuilder.append("		window.open(url);\r\n");
		stringBuilder.append("	},\r\n");
		stringBuilder.append("	// 导出\r\n");
		stringBuilder.append("	'click #clickC ': function (e, value, row, index) {\r\n");
		stringBuilder.append("		var url =\"/" + tabsNameString + "/exportModal/\"+row['uuid'];\r\n");
		stringBuilder.append("		loadPage(url);\r\n");
		stringBuilder.append("	},\r\n");
		stringBuilder.append("	// 删除\r\n");
		stringBuilder.append("	'click #clickD': function (e, value, row, index) {\r\n");
		stringBuilder.append("		bootbox.setLocale(\"zh_CN\");\r\n");
		stringBuilder.append("		bootbox.confirm({\r\n");
		stringBuilder.append("			message : \"确定要删除选定行\",\r\n");
		stringBuilder.append("			title : \"系统提示\",\r\n");
		stringBuilder.append("			callback : function(result) {\r\n");
		stringBuilder.append("				if (result) {\r\n");
		stringBuilder.append("					// 删除\r\n");
		stringBuilder.append("					var url = \"/" + tabsNameString + "/delete\";	\r\n");
		stringBuilder.append(
				"					ajaxPost(url, row, refresh" + sysDbmsGenerateCodeInfo.getClassName() + ");	\r\n");
		stringBuilder.append("				}\r\n");
		stringBuilder.append("			}\r\n");
		stringBuilder.append("		});\r\n");
		stringBuilder.append("	}\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("$(function() {\r\n");
		stringBuilder.append("	// 初始化\r\n");
		stringBuilder.append("	init();\r\n");
		stringBuilder.append("});\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("// 初始化\r\n");
		stringBuilder.append("function init() {\r\n");
		stringBuilder.append("	// 条件查询 \r\n");
		stringBuilder.append(
				"	$(\"#make_sure_search_" + subNameIdString + "_button_id\").bind(\"click\",function(){ \r\n");
		stringBuilder.append(
				"		$('#" + subNameIdString + "_datagrid').bootstrapTable(\"refreshOptions\",{pageNumber:1});\r\n");
		stringBuilder.append("	})\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("	// 弹出编辑窗口\r\n");
		stringBuilder.append("	$('#addnew_" + subNameIdString + "').click(function() {\r\n");

		stringBuilder.append("		$(\"#" + subNameIdString + "_uuid\").val(\"\");\r\n");
		stringBuilder.append("		$(\"#" + subNameIdString + "_deleteFlag\").val(\"\");\r\n");
		stringBuilder.append("		$(\"#" + subNameIdString + "_discription\").val(\"\");\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			// 属性
			String colsName = sysDbmsTabsColsInfo.getColsName().toLowerCase();
			if ("uuid".equals(colsName) || "discription".equals(colsName) || "create_time".equals(colsName)
					|| "create_user".equals(colsName) || "update_time".equals(colsName)
					|| "update_user".equals(colsName) || "delete_flag".equals(colsName)) {
				continue;
			}
			String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
			String colsNameString = "";
			for (int i = 0; i < colsNames.length; i++) {
				if (i > 0) {
					colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				} else {
					colsNameString = colsNames[i];
				}
			}
			stringBuilder.append("		$(\"#" + subNameIdString + "_" + colsNameString + "\").val(\"\");\r\n");
		}

		stringBuilder.append("		$('#" + subNameIdString + "_modal').modal({\r\n");
		stringBuilder.append("			show:true,\r\n");
		stringBuilder.append("		});\r\n");
		stringBuilder.append("	});\r\n");
		stringBuilder.append("	// 反填数据并弹出编辑窗口\r\n");
		stringBuilder.append("	$('#editold_" + subNameIdString + "').click(function() {\r\n");

		stringBuilder.append(
				"		var data = $('#" + subNameIdString + "_datagrid').bootstrapTable('getAllSelections');\r\n");
		stringBuilder.append("		if(data.length == 0||data.length >1){\r\n");
		stringBuilder.append("			alert(\"必须选中一条数据\");\r\n");
		stringBuilder.append("		}else if(data.length > 0){\r\n");

		stringBuilder.append("			$(\"#" + subNameIdString + "_uuid\").val(data[0].uuid);\r\n");
		stringBuilder.append("			$(\"#" + subNameIdString + "_deleteFlag\").val(data[0].deleteFlag);\r\n");
		stringBuilder.append("			$(\"#" + subNameIdString + "_discription\").val(data[0].discription);\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			// 属性
			String colsName = sysDbmsTabsColsInfo.getColsName().toLowerCase();
			if ("uuid".equals(colsName) || "discription".equals(colsName) || "create_time".equals(colsName)
					|| "create_user".equals(colsName) || "update_time".equals(colsName)
					|| "update_user".equals(colsName) || "delete_flag".equals(colsName)) {
				continue;
			}
			String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
			String colsNameString = "";
			for (int i = 0; i < colsNames.length; i++) {
				if (i > 0) {
					colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				} else {
					colsNameString = colsNames[i];
				}
			}
			stringBuilder.append("			$(\"#" + subNameIdString + "_" + colsNameString + "\").val(data[0]."
					+ colsNameString + ");\r\n");
		}

		stringBuilder.append("			\r\n");
		stringBuilder.append("			// 模态框\r\n");
		stringBuilder.append("			$('#" + subNameIdString + "_modal').modal({\r\n");
		stringBuilder.append("				show:true,\r\n");
		stringBuilder.append("			});\r\n");
		stringBuilder.append("		}\r\n");
		stringBuilder.append("	});\r\n");
		stringBuilder.append("	// 删除记录\r\n");
		stringBuilder.append("	$('#deleteold_" + subNameIdString + "').click(function() {\r\n");
		stringBuilder.append(
				"		var data = $('#" + subNameIdString + "_datagrid').bootstrapTable('getAllSelections');\r\n");
		stringBuilder.append("		if(data.length == 0){\r\n");
		stringBuilder.append("			alert(\"先选中一条数据\");\r\n");
		stringBuilder.append("		}else if(data.length>0){\r\n");
		stringBuilder.append("			bootbox.setLocale(\"zh_CN\");\r\n");
		stringBuilder.append("			bootbox.confirm({\r\n");
		stringBuilder.append("			message : \"确定要导出代码\",\r\n");
		stringBuilder.append("			title : \"系统提示\",\r\n");
		stringBuilder.append("			callback : function(result) {\r\n");
		stringBuilder.append("					if (result) {\r\n");
		stringBuilder.append("						var url = \"/" + tabsNameString + "/deleteAll\";\r\n");
		stringBuilder.append("						var param={list:data};\r\n");
		stringBuilder.append("						ajaxPost(url, param, refresh"
				+ sysDbmsGenerateCodeInfo.getClassName() + ");\r\n");
		stringBuilder.append("					}\r\n");
		stringBuilder.append("				}\r\n");
		stringBuilder.append("			});\r\n");
		stringBuilder.append("			\r\n");
		stringBuilder.append("		}\r\n");
		stringBuilder.append("	});\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("	// 更新数据\r\n");
		stringBuilder.append("	$('#" + subNameIdString + "_save_button').click(function() {\r\n");
		stringBuilder.append("		\r\n");
		stringBuilder.append("		var url = \"/" + tabsNameString + "/save\";\r\n");
		stringBuilder.append("		var	info={\r\n");
		stringBuilder.append("			uuid:$(\"#" + subNameIdString + "_uuid\").val(),\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			// 属性
			String colsName = sysDbmsTabsColsInfo.getColsName().toLowerCase();
			if ("uuid".equals(colsName) || "discription".equals(colsName) || "create_time".equals(colsName)
					|| "create_user".equals(colsName) || "update_time".equals(colsName)
					|| "update_user".equals(colsName) || "delete_flag".equals(colsName)) {
				continue;
			}
			String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
			String colsNameString = "";
			for (int i = 0; i < colsNames.length; i++) {
				if (i > 0) {
					colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				} else {
					colsNameString = colsNames[i];
				}
			}
			stringBuilder.append("			" + colsNameString + ":$(\"#" + subNameIdString + "_" + colsNameString
					+ "\").val(),\r\n");
		}
		stringBuilder.append("			deleteFlag:$(\"#" + subNameIdString + "_deleteFlag\").val(),\r\n");
		stringBuilder.append("			discription:$(\"#" + subNameIdString + "_discription\").val(),\r\n");
		stringBuilder.append("			createUser:username,\r\n");
		stringBuilder.append("			updateUser:username,\r\n");
		stringBuilder.append("		};\r\n");
		stringBuilder.append("		ajaxPost(url, info, refresh" + sysDbmsGenerateCodeInfo.getClassName() + " );\r\n");
		stringBuilder.append("		$('#" + subNameIdString + "_modal').modal(\"hide\");\r\n");
		stringBuilder.append("	});\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("	// bootstrap table 初始化\r\n");
		stringBuilder.append("	$('#" + subNameIdString + "_datagrid').bootstrapTable({\r\n");
		stringBuilder.append("		url : \"/" + tabsNameString + "/page\",\r\n");
		stringBuilder.append("		dataType : \"json\",\r\n");
		stringBuilder.append("		toolbar : '#" + subNameIdString + "_toolbar', // 工具按钮用哪个容器\r\n");
		stringBuilder.append("		cache : true, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）\r\n");
		stringBuilder.append("		sortable : true, // 是否启用排序\r\n");
		stringBuilder.append("		sortOrder : \"asc\", // 排序方式\r\n");
		stringBuilder.append("		pagination : true, // 分页\r\n");
		stringBuilder.append("		pageNumber : 1, // 初始化加载第一页，默认第一页\r\n");
		stringBuilder.append("		pageSize : 10, // 每页的记录行数（*）\r\n");
		stringBuilder.append("		pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）\r\n");
		stringBuilder.append("		strictSearch : true,\r\n");
		stringBuilder.append("		showColumns : true, // 是否显示所有的列\r\n");
		stringBuilder.append("		showRefresh : true, // 是否显示刷新按钮\r\n");
		stringBuilder.append("		minimumCountColumns : 2, // 最少允许的列数\r\n");
		stringBuilder.append("		clickToSelect : true, // 是否启用点击选中行\r\n");
		stringBuilder.append("		height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度\r\n");
		stringBuilder.append("		uniqueId : \"uuid\", // 每一行的唯一标识，一般为主键列\r\n");
		stringBuilder.append("		showToggle : true, // 是否显示详细视图和列表视图的切换按钮\r\n");
		stringBuilder.append("		cardView : false, // 是否显示详细视图\r\n");
		stringBuilder.append("		detailView : false, // 是否显示父子表\r\n");
		stringBuilder.append("		singleSelect : false,\r\n");
		stringBuilder.append("		locales : \"zh-CN\", // 表格汉化\r\n");
		stringBuilder.append("		search : false, // 显示搜索框\r\n");
		stringBuilder.append("		sidePagination : \"server\", // 服务端处理分页\r\n");
		stringBuilder.append("		//设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  \r\n");
		stringBuilder.append("		//设置为limit可以获取limit, offset, search, sort, order  \r\n");
		stringBuilder.append("		queryParamsType : \"undefined\",\r\n");
		stringBuilder.append("		contentType : \"application/json\",\r\n");
		stringBuilder.append("		method : \"post\", // 使用get请求到服务器获取数据\r\n");
		stringBuilder.append("		queryParams: function queryParams(params) {  \r\n");
		stringBuilder.append("			var param = {  \r\n");
		stringBuilder.append("				pageNumber: params.pageNumber,    \r\n");
		stringBuilder.append("				pageSize: params.pageSize,\r\n");
		stringBuilder.append("				sortOrder:params.sortOrder,\r\n");
		stringBuilder.append("				sortName:params.sortName,\r\n");
		stringBuilder.append("				filter:params.filter,\r\n");
		stringBuilder.append("				info:{\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			if (sysDbmsTabsColsInfo.getUserIndex() != null && !"".equals(sysDbmsTabsColsInfo.getUserIndex())) {
				String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
				String colsNameString = "";
				for (int i = 0; i < colsNames.length; i++) {
					if (i > 0) {
						colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
					} else {
						colsNameString = colsNames[i];
					}
				}
				stringBuilder.append("					" + colsNameString + ": $(\"#search_" + subNameIdString + "_"
						+ colsNameString + "\").val()==\"\"?null:$(\"#search_" + subNameIdString + "_" + colsNameString
						+ "\").val(),\r\n");
			}
		}
		stringBuilder.append("				}\r\n");
		stringBuilder.append("			}; \r\n");
		stringBuilder.append("			return param;\r\n");
		stringBuilder.append("		},\r\n");
		stringBuilder.append("		columns : [ \r\n");
		stringBuilder.append("			{title : '全选',	checkbox : true,align : 'center',valign : 'middle'}, \r\n");
		stringBuilder.append(
				"			{title : 'id',field : 'uuid',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			// 属性
			String colsName = sysDbmsTabsColsInfo.getColsName().toLowerCase();
			if ("uuid".equals(colsName) || "discription".equals(colsName) || "create_time".equals(colsName)
					|| "create_user".equals(colsName) || "update_time".equals(colsName)
					|| "update_user".equals(colsName) || "delete_flag".equals(colsName)) {
				continue;
			}
			String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
			String colsNameString = "";
			for (int i = 0; i < colsNames.length; i++) {
				if (i > 0) {
					colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				} else {
					colsNameString = colsNames[i];
				}
			}
			String alignString = "left";
			if (sysDbmsTabsColsInfo.getColsType().contains("int")
					|| sysDbmsTabsColsInfo.getColsType().contains("number")
					|| sysDbmsTabsColsInfo.getColsType().contains("double")
					|| sysDbmsTabsColsInfo.getColsType().contains("decimal")
					|| sysDbmsTabsColsInfo.getColsType().contains("time")
					|| sysDbmsTabsColsInfo.getColsType().contains("date")) {
				alignString = "right";
			}
			String colsDescString = sysDbmsTabsColsInfo.getColsDesc();
			if (colsDescString == null || "".equals(colsDescString)) {
				colsDescString = colsNameString;
			}
			Integer widthInteger = new Integer(150);
			if (sysDbmsTabsColsInfo.getColsWidth() != null && sysDbmsTabsColsInfo.getColsWidth().intValue() > 0) {
				widthInteger = sysDbmsTabsColsInfo.getColsWidth();
			}
			Boolean colsSortable = false;
			if (sysDbmsTabsColsInfo.getColsSort() != null && sysDbmsTabsColsInfo.getColsSort().equals("true")) {
				colsSortable = true;
			}
			String colsValign = sysDbmsTabsColsInfo.getColsValign();
			if (colsValign == null || "".equals(colsValign)) {
				colsValign = "middle";
			}
			Boolean colsSwitchable = sysDbmsTabsColsInfo.getColsSwitchable();
			if (colsSwitchable == null) {
				colsSwitchable = true;
			}
			Boolean colsVisible = sysDbmsTabsColsInfo.getColsVisible();
			if (colsVisible == null) {
				colsVisible = true;
			}

			stringBuilder.append("			{title : '" + colsDescString + "',	field : '" + colsNameString
					+ "','width':" + widthInteger.intValue() + ",align : '" + alignString + "',sortable : "
					+ colsSortable + ",valign : '" + colsValign + "',switchable:" + colsSwitchable + ",visible:"
					+ colsVisible + "},\r\n");
		}
		stringBuilder.append(
				"			{title : '项目描述',	field : 'discription',align : 'left',sortable : true,	valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder.append(
				"			{title : '创建时间',	field : 'createTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder.append(
				"			{title : '创建者',	field : 'createUser',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder.append(
				"			{title : '更新时间',	field : 'updateTime',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder.append(
				"			{title : '更新者',	field : 'updateUser',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder.append(
				"			{title : '标记',		field : 'deleteFlag',align : 'left',sortable : true,valign : 'middle',switchable:true,visible:false},\r\n");
		stringBuilder
				.append("			{title : '操作',align : 'left','width':180,sortable : true,valign : 'middle',switchable:true,visible:true,events: "
						+ tabsNameString + "OperateEvents,formatter : function(value, row, index) {\r\n");
		stringBuilder.append(
				"				var A = \"<i  type='button' id='clickA'  class=' btn btn-default fa fa-edit' title='编辑' >详细内容</i>  \";\r\n");
		stringBuilder.append(
				"				var B = \"<i  type='button' id='clickB'  class=' btn btn-default fa fa-download' title='下载' >下载</i>  \";\r\n");
		stringBuilder.append(
				"				var C = \"<i  type='button' id='clickC'  class=' btn btn-default fa fa-arrow-circle-o-right' title='导出' >导出</i>  \";\r\n");
		stringBuilder.append(
				"				var D = \"<i  type='button' id='clickD'  class=' btn btn-danger fa fa-circle-thin fa-remove' title='删除' >删除</i>  \";\r\n");
		stringBuilder.append("				return A+D;\r\n");
		stringBuilder.append("			}}\r\n");
		stringBuilder.append("		],\r\n");
		stringBuilder.append("		responseHandler: function(result){  // 成功时执行\r\n");
		stringBuilder
				.append("			return {rows:result.data.content,total:result.data.totalElements}; // 绑定数据 \r\n");
		stringBuilder.append("		},\r\n");
		stringBuilder.append("		onLoadError: function(status,result){  // 成功时执行\r\n");
		stringBuilder.append("			if($(result.responseText).find(\"form\").attr(\"action\")==\"/login\"){\r\n");
		stringBuilder.append("				window.location.href=\"/\";\r\n");
		stringBuilder.append("			}\r\n");
		stringBuilder.append("		},\r\n");
		stringBuilder.append("		contextMenu: '#context-menu', // 右键菜单绑定\r\n");
		stringBuilder.append("		onContextMenuItem: function(row,$ele){ // 右键菜单事件\r\n");
		stringBuilder.append("		}\r\n");

		stringBuilder.append("	}).on('dbl-click-row.bs.table', function (e, row, ele,field) { // 行双击事件 \r\n");
		stringBuilder.append("	}).on('click-row.bs.table', function (e, row, ele,field) { // 行单击事件\r\n");
		stringBuilder.append("	});\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("// 表格重载 事件\r\n");
		stringBuilder.append("function refresh" + sysDbmsGenerateCodeInfo.getClassName() + "(){\r\n");
		stringBuilder.append("	$('#" + subNameIdString + "_datagrid').bootstrapTable('refresh');\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("// 窗口大小改变时 重设表头\r\n");
		stringBuilder.append("$(window).resize(function() {\r\n");
		stringBuilder.append("	$('#" + subNameIdString + "_datagrid').bootstrapTable('resetView');\r\n");
		stringBuilder.append("});\r\n");
		stringBuilder.append("\r\n");
		// 文件写入
		String fineName = pathString + "/" + sysDbmsGenerateCodeInfo.getClassName().toLowerCase() + ".js";
		TxtFilesWriter.writeToFile(stringBuilder.toString(), fineName);
	}

	/**
	 * 方法名 generateDetail
	 * 功能 TODO(这里用一句话描述这个方法的作用)
	 * 参数 sysDbmsGenerateCodeInfo
	 * 参数  tabsInfo
	 * 参数 colsInfos
	 * 参数  username
	 * 参数  pathtempString
	 * 返回 void
	 * 作 者: Administrator
	 
	 */
	public static void generateDetail(SysDbmsGenerateCodeInfo sysDbmsGenerateCodeInfo, SysDbmsTabsInfo tabsInfo,
			List<SysDbmsTabsColsInfo> colsInfos, String username, String pathString) {
		String thirdString = "";
		String[] subpathString = sysDbmsGenerateCodeInfo.getClassPath().split("\\.");
		for (int i = 0; i < 3; i++) {
			thirdString += subpathString[i] + ".";
		}
		String tabsNameString = sysDbmsGenerateCodeInfo.getClassName().substring(0, 1).toLowerCase()
				+ sysDbmsGenerateCodeInfo.getClassName().substring(1);
		String subNameIdString = sysDbmsGenerateCodeInfo.getClassPath().toLowerCase().replace(thirdString, "")
				.replace(".", "_") + "_" + tabsNameString;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("$(function(){\r\n");
		stringBuilder.append("	init();\r\n");
		stringBuilder.append("})\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("// 初始化方法\r\n");
		stringBuilder.append("function init(){\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("	// 编辑按钮事件\r\n");
		stringBuilder.append("	$(\"#" + subNameIdString + "_edit_button\").bind(\"click\",function(){\r\n");
		stringBuilder.append("		$(\"#section_" + subNameIdString
				+ "\").find(\".box-body\").find('.row input').removeAttr(\"disabled\");\r\n");
		stringBuilder.append("		$(\"#" + subNameIdString + "_save_button\").css({\"display\":\"\"});\r\n");
		stringBuilder.append("		$(this).css({\"display\":\"none\"});\r\n");
		stringBuilder.append("	})\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("	// 保存按钮事件\r\n");
		stringBuilder.append("	$(\"#" + subNameIdString + "_save_button\").bind(\"click\",function(){\r\n");
		stringBuilder.append("		var url = \"/" + tabsNameString + "/save\";\r\n");
		stringBuilder.append("		var	info={\r\n");
		stringBuilder.append("			uuid:$(\"#" + subNameIdString + "_uuid\").val(),\r\n");
		for (SysDbmsTabsColsInfo sysDbmsTabsColsInfo : colsInfos) {
			// 属性
			String colsName = sysDbmsTabsColsInfo.getColsName().toLowerCase();
			if ("uuid".equals(colsName) || "discription".equals(colsName) || "create_time".equals(colsName)
					|| "create_user".equals(colsName) || "update_time".equals(colsName)
					|| "update_user".equals(colsName) || "delete_flag".equals(colsName)) {
				continue;
			}
			String[] colsNames = sysDbmsTabsColsInfo.getColsName().toLowerCase().split("_");
			String colsNameString = "";
			for (int i = 0; i < colsNames.length; i++) {
				if (i > 0) {
					colsNameString += colsNames[i].substring(0, 1).toUpperCase() + colsNames[i].substring(1);
				} else {
					colsNameString = colsNames[i];
				}
			}
			stringBuilder.append("			" + colsNameString + ":$(\"#" + subNameIdString + "_" + colsNameString
					+ "\").val(),\r\n");
		}

		stringBuilder.append("			createUser:username,\r\n");
		stringBuilder.append("			updateUser:username,\r\n");
		stringBuilder.append("		};\r\n");
		stringBuilder
				.append("		ajaxPost(url, info, reload" + sysDbmsGenerateCodeInfo.getClassName() + "Detail);\r\n");
		stringBuilder.append("	})\r\n");
		stringBuilder.append("	\r\n");
		stringBuilder.append("	// 退出详细信息 跳转列表页 点击事件\r\n");
		stringBuilder.append("	$(\"#" + subNameIdString + "_exit_button\").bind(\"click\",function(){\r\n");
		stringBuilder.append("		loadPage(\"" + pathString.substring(pathString.indexOf("/pages/")) + "/"
				+ sysDbmsGenerateCodeInfo.getClassName().toLowerCase() + ".html\")\r\n");
		stringBuilder.append("	})\r\n");

		stringBuilder.append("\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("\r\n");
		stringBuilder.append("// 提交按钮成功后 状态修改，\r\n");
		stringBuilder.append("function reload" + sysDbmsGenerateCodeInfo.getClassName() + "Detail(result){\r\n");
		stringBuilder.append("	if(result.code==\"200\"){\r\n");
		stringBuilder.append("		$(\"#section_" + subNameIdString
				+ "\").find(\".box-body\").find('.row input').attr(\"disabled\",\"disabled\");\r\n");
		stringBuilder.append("		$(\"#" + subNameIdString + "_edit_button\").css({\"display\":\"\"});\r\n");
		stringBuilder.append("		$(\"#" + subNameIdString + "_save_button\").css({\"display\":\"none\"});\r\n");
		stringBuilder.append("	}else{\r\n");
		stringBuilder.append("		toastr.error(result.message,\"error\");\r\n");
		stringBuilder.append("	}\r\n");
		stringBuilder.append("}\r\n");
		stringBuilder.append("\r\n");
		// 文件写入
		String fineName = pathString + "/" + sysDbmsGenerateCodeInfo.getClassName().toLowerCase() + "detail.js";
		TxtFilesWriter.writeToFile(stringBuilder.toString(), fineName);

	}

}
