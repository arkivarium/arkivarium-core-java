package nikita.webapp.odata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static nikita.common.config.ODataConstants.*;

/**
 * Extending ODataWalker to handle events so we can convert OData filter
 * command to SQL.
 */

public class ODataToSQL {
    // There is not time right now to get this code also in place. The 
    // concept is definitely nice, to convert OData to HQL, SQL and ES, but HQL
    // is all we require at the moment. Leaving it in the repo commented out
    // so the we may try and piece it together when we have some extra time
    // now and again

    private static final Logger logger =
            LoggerFactory.getLogger(ODataToSQL.class);

    private SQLStatementBuilder sqlStatementBuilder;
    private Map<String, String> comparatorMap;

    public ODataToSQL() {
        comparatorMap = new HashMap<>();
        comparatorMap.put(ODATA_EQ, SQL_EQ);
        comparatorMap.put(ODATA_GT, SQL_GT);
        comparatorMap.put(ODATA_LT, SQL_LT);
        comparatorMap.put(ODATA_GE, SQL_GE);
        comparatorMap.put(ODATA_LE, SQL_LE);

        sqlStatementBuilder = new SQLStatementBuilder();
    }

    /**
     * processResource
     * <p>
     * When dealing with the following example URL:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=startsWith(tittel,'hello')
     * <p>
     * The 'arkiv' entity is identified as a resource and picked out and is
     * identified as the 'from' part. We always add to the where clause to
     * filter out rows that actually belong to the user first and then can add
     * extra filtering as the walker progresses.
     * <p>
     * Note this will cause some problems when dealing with ownership of objects
     * via groups. Probably have to some lookup or something. But we are
     * currently just dealing with getting OData2SQL to work.
     *
     * @param entity       The entity/table you wish to search
     * @param loggedInUser The name of the user whose tupples you want to
     *                     retrieve
     */

    public void processResource(String entity, String loggedInUser) {
        // sqlStatementBuilder.addFrom(getInternalNameAttribute(entity), DM_OWNED_BY,
        //         loggedInUser);
    }


    public void processNikitaObjects(
            String parentResource, String resource, String systemId, String loggedInUser) {
        logger.error("processNikitaObjects SQL not implemented");
    }

    /**
     * processContains
     * <p>
     * Convert 'contains' to a LIKE in SQL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=contains(tittel, 'hello')
     * <p>
     * contains(tittel, 'hello') becomes the following SQL:
     * tittel LIKE %hello%
     *
     * @param attribute The attribute/column you wish to filter on
     * @param value     The value you wish to filter on
     */

    public void processContains(String attribute, String value) {
        //sqlStatementBuilder.addWhere(getInternalNameAttribute(attribute) +
        //        " LIKE '%" + value + "%'");
    }

    /**
     * processStartsWith
     * <p>
     * Convert 'startsWith' to a LIKE in SQL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=contains(tittel, 'hello')
     * <p>
     * startsWith(tittel, 'hello') becomes the following SQL:
     * tittel LIKE hello%
     * <p>
     * This is achieved by looking at the children and picking out the
     * attribute (in this case 'tittel') and value (in this case) 'hello'.
     *
     * @param attribute The attribute/column you wish to filter on
     * @param value     The value you wish to filter on
     */

    public void processStartsWith(String attribute, String value) {
        //sqlStatementBuilder.addWhere(
        //        getInternalNameAttribute(attribute) + " LIKE '" + value + "%'");
    }

    /**
     * processComparatorCommand
     * <p>
     * Convert a general Odata attribute comparator value command to SQL. In
     * the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$filter=tittel eq 'hello'
     * <p>
     * tittel eq 'hello' becomes the following SQL:
     * tittel = 'hello'
     * <p>
     * This is achieved by looking at the children and picking out the
     * attribute (in this case 'tittel'), the comparator (in this case eq) and
     * value (in this case 'hello').
     *
     * @param attribute  The attribute/column you wish to filter on
     * @param comparator The type of comparison you want to undertake e.g eq,
     *                   lt etc
     * @param value      The value you wish to filter on
     */

    public void processComparatorCommand(String attribute, String comparator,
                                         String value) {
        //sqlStatementBuilder.addWhere(getInternalNameAttribute(attribute) + " " +
        //        getSQLComparator(comparator) + " '" +
        //        value + "'");
    }

    /**
     * processOrderByCommand
     * <p>
     * Convert the OData orderBy command to SQL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$orderBy tittel desc
     * <p>
     * $orderBy tittel desc becomes the following SQL:
     * order by tittel desc
     * <p>
     * This is achieved by looking at the children and picking out the
     * attribute (in this case 'tittel'), the sort order (in this case desc).*
     * <p>
     * This can handle multiple orderBy
     *
     * @param attribute The attribute/column you wish to filter on
     * @param sortOrder The type of comparison you want to undertake e.g eq,
     *                  lt etc
     */

    public void processOrderByCommand(String attribute, String sortOrder) {
        //sqlStatementBuilder.addOrderBy(getInternalNameAttribute(attribute), sortOrder);
    }

    /**
     * processTopCommand
     * <p>
     * Convert the OData top command to SQL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$top=2
     * <p>
     * $top=2 becomes the following SQL:
     * limit 0, 2
     * <p>
     * Note this command is commonly used with $skip. The combination works.
     * <p>
     * $top=2&$skip=10 becomes the following SQL:
     * limit 10, 2
     *
     * @param top The number of rows in the result set
     */

    public void processTopCommand(Integer top) {
        sqlStatementBuilder.addLimitby_top(top);
    }

    /**
     * processSkipCommand
     * <p>
     * Convert the OData skip command to SQL. In the following example:
     * <p>
     * [contextPath][api]/arkivstruktur/arkiv?$skip=2
     * <p>
     * $skip=5 becomes the following SQL:
     * limit 5, PAGE_SIZE
     * <p>
     * Note this command is commonly used with $skip. The combination works.
     *
     * @param skip The number of rows in the result set to jump over
     */

    public void processSkipCommand(Integer skip) {
        sqlStatementBuilder.addLimitby_skip(skip);
    }


    protected String getSQLComparator(String oDataComparator) {
        return comparatorMap.get(oDataComparator);
    }

    public String getSqlStatement() {
        return sqlStatementBuilder.buildSQLStatement();
    }
}
