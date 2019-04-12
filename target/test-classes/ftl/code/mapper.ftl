package ${appPackage}.dao.mapper;

import java.util.List;

import ${appPackage}.dao.dto.query.${model}Condition;
import ${appPackage}.dao.dto.query.Pager;
import ${appPackage}.dao.model.${model};

public interface ${model}Mapper {

	${model} get${model}ById(Integer Id);

	int count(${model}Condition condition);

	List<${model}> page(${model}Condition condition,Pager pager);

	int save${model}(${model} model);

	int modify${model}(${model} model);

}