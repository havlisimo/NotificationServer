package cz.cvut.fit.havlito4.notification_server.hibernate;

import org.hibernate.AssertionFailure;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

    private static final String ENTITY_SUFFIX = "_entity";

    @Override
    public String classToTableName(String className) {
        final String tableName = super.classToTableName(className);
        if (tableName.endsWith(ENTITY_SUFFIX)) {
            return tableName.substring(0, tableName.length() - ENTITY_SUFFIX.length());
        }
        return tableName;
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
                                      String associatedEntityTable, String propertyName) {
        return tableName(associatedEntityTable);
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName,
                                       String referencedColumnName) {
        String header = referencedColumnName != null
                ? referencedColumnName
                : super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, null);
        if (header == null) throw new AssertionFailure("NamingStrategy not properly filled");
        return columnName(header); //+ "_" + referencedColumnName not used for backward compatibility
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable,
                                             String associatedEntityTable, String propertyName) {
        return tableName != null ? tableName : associatedEntityTable;
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        return columnName != null && !columnName.isEmpty() ? columnName : this.propertyToColumnName(propertyName);
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return columnName;
    }
}
