package ${appPackage}.service;

import java.util.List;

import ${appPackage}.dao.dto.query.${model}Condition;
import ${appPackage}.dao.dto.query.Pager;
import ${appPackage}.dao.model.${model};

public interface ${model}Service {

	${model} get${model}ById(Integer id);

	int count(${model}Condition condition);

	List<${model}> page(${model}Condition condition,Pager pager);

	${model} save${model}(${model} model);

	int modify${model}(${model} model);

}