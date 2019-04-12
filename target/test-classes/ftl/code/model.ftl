package ${appPackage}.dao.model;
import java.io.Serializable;
import java.sql.Timestamp;
public class ${model} extends AbstractModel implements Serializable {
 
    <#list properties as field>
        // ${field.fieldRemarks}
        private ${field.fieldType} ${field.fieldName};
    </#list>
 
    <#list properties as field>
        public ${field.fieldType} get${field.fieldNameUpperFirstLetter}() {
            return ${field.fieldName};
        }
 
        public void set${field.fieldNameUpperFirstLetter}(${field.fieldType} ${field.fieldName}) {
            this.${field.fieldName} = ${field.fieldName};
        }
    </#list>
}