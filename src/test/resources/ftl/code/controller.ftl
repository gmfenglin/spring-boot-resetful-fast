package ${appPackage}.web.controller;
import ${appPackage}.service.${model}Service;
import ${appPackage}.dao.model.${model};
import ${appPackage}.dao.dto.query.${model}Condition;
import ${appPackage}.dao.dto.query.Pager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/${instance}s")
@RestController
public class ${model}Controller {
	@Autowired
	private ${model}Service ${instance}Service;
	
	@GetMapping("/{id}")
	@EnAsyable
	public Object getById(@PathVariable Integer id){
		return ${instance}Service.get${model}ById(id);
	}
	@GetMapping
	@EnAsyable
	public Object getByCondition(${model}Condition condition,Pager pager){
		int count=${instance}Service.count(condition);
		Map<String,Object> map=new HashMap(2);
		map.put("count",count);
		if(count>0){
			List<${model}> ${instance}List=${instance}Service.page(condition,pager);
			map.put("list",${instance}List);
		}
		return map;
	}
	@PostMapping
	@EnAsyable
	public Object save(${model} ${instance}){
		return	${instance}Service.save${model}(${instance});
	}
	@PutMapping("/{id}")
	@EnAsyable
	public Object modify(${model} ${instance}){
		return ${instance}Service.modify${model}(${instance});
	}

}