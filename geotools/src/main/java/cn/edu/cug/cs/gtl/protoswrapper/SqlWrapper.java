package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.SqlDeleteStatement;
import cn.edu.cug.cs.gtl.protos.SqlInsertStatement;
import cn.edu.cug.cs.gtl.protos.SqlQueryStatement;
import cn.edu.cug.cs.gtl.protos.SqlUpdateStatement;

import java.util.Map;
import java.util.Set;

public class SqlWrapper {

    /**
     *
     * @param st
     * @return
     */
    public static String toSqlStatement(SqlQueryStatement st){
        return SqlQueryStatementWrapper.toSqlStatement(st);
    }

    /**
     *
     * @param st
     * @return
     */
    public static String toSqlStatement(SqlUpdateStatement st){
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(st.getTableName());
        sb.append(" SET ");

        Set<String> keySey = st.getSetFieldMap().keySet();
        int i=0;
        int c=keySey.size();
        for(String s:keySey){
            sb.append(s);
            sb.append("=");
            sb.append(st.getSetFieldMap().get(s));
            if(i<c-1)
                sb.append(",");
            else
                sb.append(" ");
        }

        c = st.getWhereFieldCount();
        if(c>0){
            sb.append("where ");
            for(i=0;i<c;++i){
                sb.append(st.getWhereField(i));
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    /**
     *
     * @param st
     * @return
     */
    public static String toSqlStatement(SqlInsertStatement st){
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(st.getTableName());
        sb.append(" (");

        StringBuilder sb2 = new StringBuilder(" VALUES(");

        int c = st.getColumnValueCount();
        int i=0;

        Set<String> keySey = st.getColumnValueMap().keySet();
        for(String s:keySey){
            sb.append(s);
            sb2.append(st.getColumnValueMap().get(s));
            if(i<c-1) {
                sb.append(",");
                sb2.append(",");
            }
            else {
                sb.append(")");
                sb2.append(")");
            }
        }
        return sb.append(sb2.toString()).toString();
    }

    /**
     *
     * @param st
     * @return
     */
    public static String toSqlStatement(SqlDeleteStatement st){
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(st.getTableName());
        sb.append(" ");

        int c = st.getWhereFieldCount();
        int i=0;
        if(c>0){
            sb.append("WHERE ");
            for(i=0;i<c;++i){
                sb.append(st.getWhereField(i));
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    /**
     *
     * @param st
     * @return
     */
    public static Map<String,String> toSolrQueryParameterMap(SqlQueryStatement st) throws Exception{
        return SqlQueryStatementWrapper.toSolrQueryParameterMap( st);
    }

    /**
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public static Map<String,String> toSolrQueryParameterMap(String sql) throws Exception {
        return SqlQueryStatementWrapper.toSolrQueryParameterMap( sql);
    }

    }
