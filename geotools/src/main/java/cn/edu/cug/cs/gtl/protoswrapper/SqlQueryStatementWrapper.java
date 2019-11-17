package cn.edu.cug.cs.gtl.protoswrapper;
import cn.edu.cug.cs.gtl.protos.SqlQueryStatement;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOrderBy;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParser;

import java.util.HashMap;
import java.util.Map;

class SqlQueryStatementWrapper {
    /**
     *
     * @param st
     * @return
     */
    public static String toSqlStatement(SqlQueryStatement st){
        StringBuilder builder = new StringBuilder("select ");
        int i=0;
        int c = st.getSelectFieldCount();
        for(i=0;i<c;++i){
            builder.append(st.getSelectField(i));
            if(i!=c-1)
                builder.append(',');
            else
                builder.append(' ');
        }
        builder.append(" from ");
        c = st.getFromFieldCount();
        for(i=0;i<c;++i){
            builder.append(st.getFromField(i));
            if(i!=c-1)
                builder.append(',');
            else
                builder.append(' ');
        }

        c = st.getWhereFieldCount();
        if(c>0){
            builder.append(" where ");
            for(i=0;i<c;++i){
                builder.append(st.getWhereField(i));
                builder.append(' ');
            }
        }

        c = st.getOrderByFieldCount();
        if(c>0){
            builder.append(" order by ");
            for(i=0;i<c;++i){
                builder.append(st.getOrderByField(i));
                if(i!=c-1)
                    builder.append(',');
                else
                    builder.append(' ');
            }
        }

        c = st.getGroupByFieldCount();
        if(c>0){
            builder.append(" group by ");
            for(i=0;i<c;++i){
                builder.append(st.getGroupByField(i));
                if(i!=c-1)
                    builder.append(',');
                else
                    builder.append(' ');
            }

            c = st.getHavingFieldCount();
            if(c>0){
                builder.append(" having ");
                for(i=0;i<c;++i){
                    builder.append(st.getHavingField(i));
                    if(i!=c-1)
                        builder.append(',');
                    else
                        builder.append(' ');
                }
            }
        }
        return builder.toString();
    }

    /**
     *
     * @param sql
     * @return
     */
    public static Map<String,String> toSolrQueryParameterMap(String sql) throws Exception{
        String sqlSelectField=null;
        String sqlWhereField=null;
        String sqlOrderByField=null;
        String sqlFromField=null;
        // 解析配置
        SqlParser.Config sqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        // 创建解析器
        SqlParser parser = SqlParser.create(sql, sqlConfig);
        // 解析sql
        SqlNode sqlNode = parser.parseQuery();
        if (sqlNode instanceof SqlOrderBy) {
            SqlOrderBy sqlOrderBy = (SqlOrderBy) sqlNode;
            SqlSelect sqlSelect = (SqlSelect) sqlOrderBy.query;
            sqlSelectField =sqlSelect.getSelectList().toString();
            sqlWhereField = sqlSelect.getWhere().toString();
            sqlOrderByField = sqlOrderBy.orderList.toString();
            sqlFromField = sqlSelect.getFrom().toString();
        } else if (sqlNode instanceof SqlSelect) {
            SqlSelect sqlSelect = (SqlSelect) sqlNode;
            sqlSelectField =sqlSelect.getSelectList().toString();
            sqlWhereField = sqlSelect.getWhere().toString();
            sqlFromField = sqlSelect.getFrom().toString();
        }
        else{
            throw new Exception("unknown sql");
        }


        Map<String, String> queryParamMap = new HashMap<>();

        if(sqlSelectField!=null) {
            queryParamMap.put("fl",select2fl(sqlSelectField));
        }
        if(sqlFromField!=null) {
            queryParamMap.put("q",from2sort(sqlFromField));
        }
        if(sqlWhereField!=null) {
            queryParamMap.put("fq",where2fq(sqlWhereField));
        }
        if(sqlOrderByField!=null) {
            queryParamMap.put("sort",orderBy2sort(sqlWhereField));
        }


        return queryParamMap;
    }

    /**
     *
     * @param st
     * @return
     */
    public static Map<String,String> toSolrQueryParameterMap(SqlQueryStatement st) throws Exception{

        String sql = toSqlStatement(st);

        Map<String, String> queryParamMap = new HashMap<>();
        int i=0;
        int c=0;

        //q parameter
        queryParamMap.put("q", "*:*");
//        if(st.getFromFieldCount()<=0){
//            queryParamMap.put("q", "*:*");
//        }
//        else{
//            String s = st.getFromField(0);
//            if(s!=null && !s.trim().isEmpty()){
//                if(s.equals("*"))
//                    queryParamMap.put("q", "*:*");
//                else
//                    queryParamMap.put("q", s+":*");
//            }
//            else{
//                queryParamMap.put("q", "*:*");
//            }
//        }


        //fl parameter
        if(st.getSelectFieldCount()>0){
            StringBuilder builder = new StringBuilder();
            c = st.getSelectFieldCount();
            for(i=0;i<c;++i){
                builder.append(st.getSelectField(i));
                if(i!=c-1)
                    builder.append(',');
            }
            queryParamMap.put("fl", builder.toString());
        }

        //fq parameter
        if(st.getWhereFieldCount()>0){
            String sqlWhereField=null;
            // 解析配置
            SqlParser.Config sqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
            // 创建解析器
            SqlParser parser = SqlParser.create(sql, sqlConfig);
            // 解析sql
            SqlNode sqlNode = parser.parseQuery();
            if (sqlNode instanceof SqlOrderBy) {
                SqlOrderBy sqlOrderBy = (SqlOrderBy) sqlNode;
                SqlSelect sqlSelect = (SqlSelect) sqlOrderBy.query;
                sqlWhereField = sqlSelect.getWhere().toString();
            } else if (sqlNode instanceof SqlSelect) {
                SqlSelect sqlSelect = (SqlSelect) sqlNode;
                sqlWhereField = sqlSelect.getWhere().toString();

            }
            else {
                throw new Exception("unknown sql");
            }

            queryParamMap.put("fq", where2fq(sqlWhereField).trim());
        }

        //sort
        c = st.getOrderByFieldCount();
        if(c>0){
            StringBuilder builder = new StringBuilder();
            for(i=0;i<c;++i){
                builder.append(st.getOrderByField(i));
                if(i!=c-1)
                    builder.append(',');
            }
            queryParamMap.put("sort", "id asc");
        }

        //start
        int limitOffset= st.getLimitOffset();
        if(limitOffset>0) {
            queryParamMap.put("start", String.valueOf(limitOffset));
        }

        //rows
        int limitRows = st.getLimitRows();
        if (limitRows>0){
            queryParamMap.put("rows", String.valueOf(limitRows));
        }

        return queryParamMap;
    }

    /**
     *
     * @param sqlWhereField
     * @return
     * @throws Exception
     */
    private static String where2fq(String sqlWhereField) throws Exception{
            /*
            Operator	Description	Example	     Solr Query
            =	Equals	fielda = 10	fielda:10
            <>	Does not equal	fielda <> 10	-fielda:10
            !=	Does not equal	fielda != 10	-fielda:10
            >	Greater than	fielda > 10	fielda:{10 TO *]
            >=	Greater than or equals	fielda >= 10	fielda:[10 TO *]
            <	Less than	fielda < 10	fielda:[* TO 10}
            <=	Less than or equals	fielda <= 10	fielda:[* TO 10]
             */
        sqlWhereField = sqlWhereField.replace("`", "");
        sqlWhereField = sqlWhereField.replace("'", "");
        //sqlWhereField=sqlWhereField.replace("where","");
        String[] ss = StringUtils.split(sqlWhereField," ");
        int c=ss.length;

        StringBuilder sb = new StringBuilder();
        int i=0;
        while (i<c){
            if(ss[i].equals("=")){
                //sb.append('+');
                sb.append(ss[i-1]);
                sb.append(":");
                sb.append(ss[i+1]);
                sb.append(' ');
                ++i;
            }
            else if(ss[i].equals("<>")||ss.equals("!=")){
                //sb.append('-');
                sb.append("(NOT (");
                sb.append(ss[i-1]);
                sb.append(":");
                sb.append(ss[i+1]);
                sb.append(" ))");
                ++i;
            }
            else if(ss[i].equals(">")){
                //sb.append('+');
                sb.append(ss[i-1]);
                sb.append(":{");
                sb.append(ss[i+1]);
                sb.append(" TO *] ");
                ++i;
            }
            else if(ss[i].equals(">=")){
                //sb.append('+');
                sb.append(ss[i-1]);
                sb.append(":[");
                sb.append(ss[i+1]);
                sb.append(" TO *] ");
                ++i;
            }
            else if(ss[i].equals("<=")){
                //sb.append('+');
                sb.append(ss[i-1]);
                sb.append(":[* TO ");
                sb.append(ss[i+1]);
                sb.append(" ] ");
                ++i;
            }
            else if(ss[i].equals("<")){
                //sb.append('+');
                sb.append(ss[i-1]);
                sb.append(":[* TO ");
                sb.append(ss[i+1]);
                sb.append(" } ");
                ++i;
            }
            else if(ss[i].equals("AND")
                ||ss[i].equals("OR")
                ||ss[i].equals("NOT")
                ||ss[i].equals("(") || ss[i].equals(")")){
                sb.append(ss[i]);
                sb.append(' ');
                ++i;
            }
            else if(ss[i].equals("BETWEEN")){
                //f BETWEEN a AND b
                sb.append(ss[i-1]);
                sb.append(":[");
                sb.append(ss[i+1]);
                sb.append(" TO ");
                sb.append(ss[i+3]);
                sb.append("]");
                i+=4;
            }
            else if(ss[i].equals("LIKE")){
                throw new Exception("do not support like");
            }
            else{
                i++;
            }
        }

        return sb.toString();

    }

    private static String select2fl(String sqlSelectField) throws Exception{
        sqlSelectField = sqlSelectField.replace("`", "");
        sqlSelectField = sqlSelectField.replace("'", "");

        return sqlSelectField;
    }

    private static String orderBy2sort(String sqlOrderByField) throws Exception{

        sqlOrderByField = sqlOrderByField.replace("`", "");
        sqlOrderByField = sqlOrderByField.replace("'", "");

        return sqlOrderByField;
    }

    private static String from2sort(String sqlFromField) throws Exception{
        //sqlFromField = sqlFromField.replace("`", "");
        //sqlFromField = sqlFromField.replace("'", "");
        return "*:*";
    }
}
