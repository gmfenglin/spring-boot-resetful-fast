<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper
	namespace="${appPackage}.dao.mapper.${model}Mapper">
	<insert id="save${model}" keyProperty="id" useGeneratedKeys="true">
		insert into ${tableName}(
		<#assign index=0>
		<#list fileds as field>
			<#if (field.fieldName!="id" && field.fieldName!="status" && field.fieldName!="is_deleted")>
			   <#assign index=index +1 >
				<#if index ==fileds?size-3>${field.fieldName}<#else>${field.fieldName},</#if>
			</#if>
		 </#list>
		 )
		values (
		<#assign indexProperty=0>
		<#list mapProperties as field>
			<#if (field.fieldName!="id" && field.fieldName!="status" && field.fieldName!="isDeleted")>
			 <#assign indexProperty=indexProperty + 1>
				<#if indexProperty ==mapProperties?size-3>
				<#switch field.fieldName>
					<#case "gmtCreate">
    					now()
     				<#break>
     				<#case "gmtModified">
    					now()
     				<#break>
     				<#default>
     					<#noparse>#{</#noparse>${field.fieldName}<#noparse>}</#noparse>
				</#switch>
				<#else>
					<#switch field.fieldName>
						<#case "gmtCreate">
	    					now(),
	     				<#break>
	     				<#case "gmtModified">
	    					now(),
	     				<#break>
	     				<#default>
	     					<#noparse>#{</#noparse>${field.fieldName}<#noparse>}</#noparse>,
					</#switch>
				</#if>
			</#if>
		 </#list>
		);
	</insert>
	<update id="modify${model}" >
		update ${tableName}
		set
			<#list relation?keys as k>
				<#if (k!="id" && k!="gmt_modified" && k!="user_modified" && k!="gmt_create" && k!="user_create")>
					<if test="${relation[k]}!=null">
						${k}=<#noparse>#{</#noparse>${relation[k]}<#noparse>}</#noparse>,
					</if>
				</#if>
			</#list>
			gmt_modified=now(),
			user_modified=<#noparse>#{</#noparse>userModified<#noparse>}</#noparse>
		where id=<#noparse>#{</#noparse>id<#noparse>}</#noparse>
	</update>
	<select id="get${model}ById" resultType="${appPackage}.dao.model.${model}">
		select
		 <#list relation?keys as k>
		 	<#if k_index ==relation?size-1>
		 		${tableName}.${k} as ${relation[k]}
		 	<#else>
		 		${tableName}.${k} as ${relation[k]},
		 	</#if>
		 </#list>
		from ${tableName}
		where id=<#noparse>#{</#noparse>id<#noparse>}</#noparse>
	</select>
	<select id="count" resultType="int">
		select count(*) from ${tableName}
	</select>
	<select id="page" resultType="${appPackage}.dao.model.${model}">
		select 
		 <#list relation?keys as k>
		 	<#if k_index ==relation?size-1>
		 		${tableName}.${k} as ${relation[k]}
		 	<#else>
		 		${tableName}.${k} as ${relation[k]},
		 	</#if>
		 </#list>
		from ${tableName}
	</select>
</mapper>