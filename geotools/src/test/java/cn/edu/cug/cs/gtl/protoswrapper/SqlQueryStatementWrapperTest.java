package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.SqlQueryStatement;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlQueryStatementWrapperTest {

    @Test
    public void toSqlStatement() {
        SqlQueryStatement.Builder b = SqlQueryStatement.newBuilder();
        b.addSelectField("field1");
        b.addSelectField("field2");
        b.addFromField("tab1");
        b.addFromField("tab2");
        b.addWhereField("field3=1 and field4=2");
        b.addGroupByField("field5");
        b.addHavingField("field6>6");
        System.out.println(SqlQueryStatementWrapper.toSqlStatement(b.build()));
    }

    @Test
    public void toSqlStatementForOrderBy() {
        SqlQueryStatement.Builder b = SqlQueryStatement.newBuilder();
        b.addSelectField("field1");
        b.addSelectField("field2");
        b.addFromField("tab1");
        b.addFromField("tab2");
        b.addWhereField("field3=1 and field4=2");
        b.addOrderByField("field5 asc");
        b.addOrderByField("field6 desc");
        System.out.println(SqlQueryStatementWrapper.toSqlStatement(b.build()));
    }

    @Test
    public void toSolr() throws Exception{
        SqlQueryStatement.Builder b = SqlQueryStatement.newBuilder();
        b.addSelectField("field1");
        b.addSelectField("field2");
        b.addFromField("tab1");
        b.addFromField("tab2");
        b.addWhereField("field3=1 and field4=2");
        b.addOrderByField("field5 asc");
        b.addOrderByField("field6 desc");
        SqlQueryStatement st = b.build();
        System.out.println(SqlQueryStatementWrapper.toSqlStatement(st));

        var m = SqlQueryStatementWrapper.toSolrQueryParameterMap(st);
        System.out.println(m);
    }



}