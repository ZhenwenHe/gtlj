package cn.edu.cug.cs.gtl.mybatis;

import cn.edu.cug.cs.gtl.mybatis.mapper.common.*;
import cn.edu.cug.cs.gtl.protos.SqlCommand;
import cn.edu.cug.cs.gtl.protos.SqlDataSet;
import cn.edu.cug.cs.gtl.protos.SqlRecord;
import cn.edu.cug.cs.gtl.protos.SqlResult;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;

public class Session {
    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    public static Session open() {
        return new Session();
    }

    /**
     * @param resource mybatis-config.xml
     * @return
     */
    public static Session open(String resource) {
        return new Session(resource);
    }

    /**
     *
     */
    private Session() {
        String resource = "mybatis-config.xml";
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            factory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
            sqlSession = factory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param resource mybatis-config.xml
     */
    private Session(String resource) {
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            factory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
            sqlSession = factory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cmd
     * @return
     */
    public SqlResult execute(SqlCommand cmd) {

        return execute(cmd.getCommandText());
    }

    /**
     *
     */
    public void commit() {
        this.sqlSession.commit();
    }

    /**
     *
     */
    public void close() {
        this.sqlSession.close();
    }

    /**
     * @param cmd
     * @return
     */
    public SqlResult execute(String cmd) {
        String commandText = cmd.trim();
        String commandType = StringUtils.split(commandText, " ")[0].toUpperCase();

        if (commandType.equals("SELECT")) {
            return query(commandText);
        } else {
            return modify(commandText);
        }

    }

    /**
     * query statement
     *
     * @param commandText
     * @return
     */
    private SqlResult query(String commandText) {
        SqlResult.Builder builder = SqlResult.newBuilder();
        builder.setCommandText(commandText);
        try {
            SelectMapper mapper = this.sqlSession.getMapper(SelectMapper.class);
            List<LinkedHashMap<String, Object>> ls = mapper.query(commandText);
            SqlDataSet.Builder dsBuilder = SqlDataSet.newBuilder();
            //set column infos
            LinkedHashMap<String, Object> m = ls.get(0);
            for (String s : m.keySet()) {
                dsBuilder.addColumnName(s);
            }
            //set records
            SqlRecord.Builder recBuilder = SqlRecord.newBuilder();
            for (LinkedHashMap<String, Object> lhm : ls) {
                for (Object o : lhm.values()) {
                    recBuilder.addElement(o.toString());
                }
                dsBuilder.addRecord(recBuilder.build());
                recBuilder.clearElement();
            }
            builder.setDataset(dsBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
            builder.setStatus(false);
            return builder.build();
        }
        builder.setStatus(true);
        return builder.build();
    }

    /**
     * 更新或修改数据库结构或数据
     *
     * @param commandText
     * @return
     */
    private SqlResult modify(String commandText) {
        String commandType = StringUtils.split(commandText, " ")[0].toUpperCase();
        SqlResult.Builder builder = SqlResult.newBuilder();
        builder.setCommandText(commandText);
        try {
            if (commandText.equals("ALTER")) {
                AlterMapper mapper = this.sqlSession.getMapper(AlterMapper.class);
                mapper.execute(commandText);
            } else if (commandText.equals("CREATE")) {
                CreateMapper mapper = this.sqlSession.getMapper(CreateMapper.class);
                mapper.execute(commandText);
            } else if (commandText.equals("DELETE")) {
                DeleteMapper mapper = this.sqlSession.getMapper(DeleteMapper.class);
                mapper.execute(commandText);
            } else if (commandText.equals("DROP")) {
                DropMapper mapper = this.sqlSession.getMapper(DropMapper.class);
                mapper.execute(commandText);
            } else if (commandText.equals("INSERT")) {
                InsertMapper mapper = this.sqlSession.getMapper(InsertMapper.class);
                mapper.execute(commandText);
            } else if (commandText.equals("UPDATE")) {
                UpdateMapper mapper = this.sqlSession.getMapper(UpdateMapper.class);
                mapper.execute(commandText);
            } else {
                throw new Exception("error command type");
            }
        } catch (Exception e) {
            e.printStackTrace();
            builder.setStatus(false);
            return builder.build();
        }

        builder.setStatus(true);
        return builder.build();
    }
}
