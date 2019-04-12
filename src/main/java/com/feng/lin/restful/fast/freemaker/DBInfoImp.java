package com.feng.lin.restful.fast.freemaker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.feng.lin.restful.fast.config.ProjectProperty;

@Component
public class DBInfoImp implements DBInfo {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ProjectProperty projectProperty;
	@Value("${spring.datasource.db-name}")
	private String dbName;
	private Connection conn;

	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	public final static String abatractModelFileds[] = { "id", "status", "is_deleted", "gmt_create", "gmt_modified",
			"user_modified", "user_create" };

	protected void getTables() throws Exception {
		ResultSet rs = conn.getMetaData().getTables(dbName, null, null, new String[] { "TABLE" });
		ResultSetMetaData rmd=rs.getMetaData();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			String tableName = rs.getString("TABLE_NAME"); // 表名
			map.put(Const.TABLE_TAG, tableName);
			map.put(Const.MODEL_TAG, tableNameToClassName(tableName));
			map.put(Const.INSTANCE_TAG, lowerFirstLetter(tableNameToClassName(tableName)));
			data.add(map);

		}
		rs.close();
	}

	private String getFiledName(String src) {
		StringBuilder filedName = new StringBuilder();
		// aa_bb_cc AaBbCc
		String[] split = src.split("_");
		int count = 0;
		for (String item : split) {
			if (count > 0) {
				filedName.append(upperFirstLetter(item));
			} else {
				filedName.append((item));
			}
			count++;
		}
		return filedName.toString();

	}

	private String lowerFirstLetter(String src) {
		String firstLetter = src.substring(0, 1).toLowerCase();
		String otherLetters = src.substring(1);
		return firstLetter + otherLetters;
	}

	private String upperFirstLetter(String src) {
		String firstLetter = src.substring(0, 1).toUpperCase();
		String otherLetters = src.substring(1);
		return firstLetter + otherLetters;
	}

	private String tableNameToClassName(String tableName) {
		StringBuilder className = new StringBuilder();
		// aa_bb_cc AaBbCc
		String[] split = tableName.split("_");
		for (String item : split) {
			className.append(upperFirstLetter(item));
		}
		return className.toString();
	}

	protected void getColByTableName() throws Exception {
		for (Map<String, Object> map : data) {
			map.put(Const.APP_PACKAGE_TAG, projectProperty.getGroupId() + "." + projectProperty.getArtifactId());
			List<Field> fieldList = new ArrayList<Field>();
			List<Field> propertyList = new ArrayList<Field>();
			List<Field> mapPropertyList = new ArrayList<Field>();
			Map<String, String> relation = new HashMap<String, String>();
			map.put(Const.FILEDS_TAG, fieldList);
			map.put(Const.PROPERTIES_TAG, propertyList);
			map.put(Const.RELATION_TAG, relation);
			map.put(Const.MAP_PROPERTIES_TAG, mapPropertyList);
			ResultSet rsField = conn.getMetaData().getColumns(null, "%", map.get(Const.TABLE_TAG).toString(), "%");
			while (rsField.next()) {
				// 列名
				String columnName = rsField.getString("COLUMN_NAME");
				// 类型
				String typeName = rsField.getString("TYPE_NAME");
				// 注释
				String remarks = rsField.getString("REMARKS");
				Field field = new Field();
				field.setFieldName(columnName);
				field.setFieldType(columnTypeToFieldType(typeName));
				field.setFieldRemarks(remarks);
				fieldList.add(field);
				relation.put(columnName, getFiledName(columnName));
				Field mapProperty = new Field();
				mapProperty.setFieldName(getFiledName(columnName));
				mapProperty.setFieldType(columnTypeToFieldType(typeName));
				mapProperty.setFieldRemarks(remarks);
				mapProperty.setFieldNameUpperFirstLetter(upperFirstLetter(columnName));
				mapPropertyList.add(mapProperty);
				if (isSupperFiled(columnName)) {
					continue;
				}
				Field property = new Field();
				property.setFieldName(getFiledName(columnName));
				property.setFieldType(columnTypeToFieldType(typeName));
				property.setFieldRemarks(remarks);
				property.setFieldNameUpperFirstLetter(upperFirstLetter(getFiledName(columnName)));
				propertyList.add(property);
			}
			rsField.close();
		}

	}

	private boolean isSupperFiled(String filedName) {
		List<String> supperFiledList = new ArrayList<String>();
		for (int i = 0; i < abatractModelFileds.length; i++) {
			supperFiledList.add(abatractModelFileds[i]);
		}

		return supperFiledList.contains(filedName);
	}

	private String columnTypeToFieldType(String columnType) {
		String fieldType = null;
		switch (columnType) {
		case "INT UNSIGNED":
			fieldType = "Integer";
			break;
		case "VARCHAR":
			fieldType = "String";
			break;
		case "CHAR":
			fieldType = "String";
			break;
		case "DATETIME":
			fieldType = "Date";
			break;
		case "SMALLINT UNSIGNED":
			fieldType = "Integer";
			break;
		case "BIGINT":
			fieldType = "Long";
			break;
		case "DECIMAL":
			fieldType = "BigDecimal";
			break;
		case "TIMESTAMP":
			fieldType = "Timestamp";
			break;
		case "TINYINT":
			fieldType = "Integer";
			break;
		default:
			fieldType = "String";
			break;
		}
		return fieldType;
	}

	@Override
	public List<Map<String, Object>> getData() {
		init();
		try {
			getTables();
			getColByTableName();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destory();
		}

		return data;
	}

	protected void init() {
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void destory() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
