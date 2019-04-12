package ${appPackage}.service.imp;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${appPackage}.dao.dto.query.${model}Condition;
import ${appPackage}.dao.dto.query.Pager;
import ${appPackage}.dao.model.${model};
import ${appPackage}.dao.mapper.${model}Mapper;
import ${appPackage}.service.${model}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${model}ServiceImp implements ${model}Service {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ${model}Mapper ${instance}Mapper;
	
	public ${model} get${model}ById(Integer id){
		try{
			return ${instance}Mapper.get${model}ById(id);
		}catch(Exception e){
			logger.error("get${model}ById", e);
			return null;
		}
		
	}

	public int count(${model}Condition condition){
		try{
			return ${instance}Mapper.count(condition);
		}catch(Exception e){
			logger.error("pageCount", e);
			return -1;
		}
	
	}

	public List<${model}> page(${model}Condition condition,Pager pager){
		try{
			return ${instance}Mapper.page(condition,pager);
		}catch(Exception e){
			logger.error("page", e);
			return null;
		}
	}

	public ${model} save${model}(${model} model){
		try{
				int count= ${instance}Mapper.save${model}(model);
				if(count>0){
					return model;
				}else{
					return null;
				}
			}catch(Exception e){
				logger.error("save${model}", e);
				return null;
			}
	}

	public int modify${model}(${model} model){
		try{
				int count= ${instance}Mapper.modify${model}(model);
				return count;
			}catch(Exception e){
				logger.error("modify${model}", e);
				return -1;
			}
	}

	
}