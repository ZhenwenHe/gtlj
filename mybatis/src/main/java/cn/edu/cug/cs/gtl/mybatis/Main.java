package cn.edu.cug.cs.gtl.mybatis;


import cn.edu.cug.cs.gtl.mybatis.mapper.common.CreateMapper;
import cn.edu.cug.cs.gtl.mybatis.mapper.common.DropMapper;
import cn.edu.cug.cs.gtl.mybatis.mapper.common.InsertMapper;
import cn.edu.cug.cs.gtl.mybatis.mapper.example.DataMapper;
import cn.edu.cug.cs.gtl.mybatis.mapper.common.SelectMapper;
import cn.edu.cug.cs.gtl.protos.SqlDataSet;
import cn.edu.cug.cs.gtl.protos.SqlRecord;
import cn.edu.cug.cs.gtl.protos.SqlResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Session session = Session.open();
        //String s = "select h.name, h.w5 from hax_nn_view h, sax_view s where trim(h.name)=trim(s.name) and h.w5>s.w5";
        String s = "select * from DICT_TABINFO";
        SqlResult r = session.execute(s);
        if (r.getStatus()) {
            SqlDataSet ds = r.getDataset();
            for (String str : ds.getColumnNameList()) {
                System.out.print(str);
                System.out.print(" ");
            }
            System.out.println();
            for (SqlRecord record : ds.getRecordList()) {
                System.out.print(record.getElement(0));
                System.out.print(" ");
                System.out.println(record.getElement(1));
            }
        }
    }

    public static void test(String[] args) {

//        String resource = "mybatis-config.xml";
//        try {
//            Reader reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
//            reader.close();
//            SqlSession sqlSession = factory.openSession();
//            DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
//            List<Map<Object, Object>> results = dataMapper.getInfos();
//            for (Map<Object, Object> m : results) {
//                System.out.println("keySet()");
//                System.out.println(m.keySet());
//                System.out.println("values()");
//                System.out.println(m.values());
//            }
//            OriginDatasetInfosMapper originDatasetInfosMapper = sqlSession.getMapper(OriginDatasetInfosMapper.class);
//            List<OriginDatasetInfos> ls = originDatasetInfosMapper.selectAll();
//            for (OriginDatasetInfos o : ls) {
//                System.out.println(o.getName());
//            }
//
//            SelectMapper selectMapper = sqlSession.getMapper(SelectMapper.class);
//            String s = "select h.name, h.w5 from hax_nn_view h, sax_view s where trim(h.name)=trim(s.name) and h.w5>s.w5";
//            List<LinkedHashMap<String, Object>> resultsSelect = selectMapper.query(s);
//            for (LinkedHashMap<String, Object> m : resultsSelect) {
//                System.out.println("keySet()");
//                System.out.println(m.keySet());
//                System.out.println("values()");
//                System.out.println(m.values());
//            }
//
//
//            {
//
//                DropMapper dropMapper = sqlSession.getMapper(DropMapper.class);
//                String str = "drop table test";
//                dropMapper.execute(str);
//
//
//                CreateMapper createMapper = sqlSession.getMapper(CreateMapper.class);
//                str = "create table test (id int, name varchar)";
//                createMapper.execute(str);
//
//
//                InsertMapper insertMapper = sqlSession.getMapper(InsertMapper.class);
//                str = "insert into test values(1,'2')";
//                insertMapper.execute(str);
//
//                sqlSession.commit();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
