package util.orm.hibernate;

import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;


public class LCNamingStrategy extends DefaultComponentSafeNamingStrategy {
    
    @Override
    public String propertyToColumnName(String propertyName) {
        propertyName = propertyName.replaceAll("([A-Z])", "_$1");
        return addUnderscores(propertyName);
    }
    @Override
    public String classToTableName(String className) {
        return tableName(super.classToTableName(className));
    }
    
    @Override
    public String tableName(String tableName) {
        return tableName.toLowerCase();
    }
    
    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName).toLowerCase();
    }
    
}
