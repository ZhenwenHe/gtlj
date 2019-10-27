package cn.edu.cug.cs.gtl.mybatis.rpc;

import cn.edu.cug.cs.gtl.mybatis.mapper.common.*;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.util.StringUtils;
import io.grpc.stub.StreamObserver;
import org.apache.ibatis.session.SqlSession;

import java.util.LinkedHashMap;
import java.util.List;

public class SqlCommandService extends SqlCommandServiceGrpc.SqlCommandServiceImplBase{
    private SqlSession sqlSession ;

    public SqlCommandService(SqlSession sqlSession){
        super();
        this.sqlSession=sqlSession;
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void execute(SqlCommand request, StreamObserver<SqlResult> responseObserver) {
        //super.execute(request, responseObserver);
        String commandText = request.getCommandText().trim();
        String commandType = StringUtils.split(commandText," ")[0].toUpperCase();

        if(commandType.equals("SELECT")){
            responseObserver.onNext(query(commandText));
        }
        else{
            responseObserver.onNext(modify(commandText));
        }
        responseObserver.onCompleted();
    }

    /**
     * query statement
     * @param commandText
     * @return
     */
    private SqlResult query(String commandText){

        SqlResult.Builder  builder = SqlResult.newBuilder();
        builder.setCommandText(commandText);
        try {
            SelectMapper mapper = this.sqlSession.getMapper(SelectMapper.class);
            List<LinkedHashMap<String,Object>> ls = mapper.query(commandText);
            SqlDataSet.Builder dsBuilder = SqlDataSet.newBuilder();
            //set column infos
            LinkedHashMap<String,Object> m = ls.get(0);
            for(String s: m.keySet()){
                dsBuilder.addColumnName(s);
            }
            //set records
            SqlRecord.Builder recBuilder = SqlRecord.newBuilder();
            for(LinkedHashMap<String,Object> lhm: ls){
                for(Object o: lhm.values()){
                    recBuilder.addElement(o.toString());
                }
                dsBuilder.addRecord(recBuilder.build());
                recBuilder.clearElement();
            }
            builder.setDataset(dsBuilder.build());
        }
        catch (Exception e){
            e.printStackTrace();
            builder.setStatus(false);
            return builder.build();
        }
        builder.setStatus(true);
        return builder.build();
    }

    /**
     * 更新或修改数据库结构或数据
     * @param commandText
     * @return
     */
    private SqlResult modify(String commandText){
        String commandType = StringUtils.split(commandText," ")[0].toUpperCase();
        SqlResult.Builder builder=SqlResult.newBuilder();
        builder.setCommandText(commandText);
        try {
            if(commandText.equals("ALTER")){
                AlterMapper mapper = this.sqlSession.getMapper(AlterMapper.class);
                mapper.execute(commandText);
            }
            else if(commandText.equals("CREATE")){
                CreateMapper mapper = this.sqlSession.getMapper(CreateMapper.class);
                mapper.execute(commandText);
            }
            else if(commandText.equals("DELETE")){
                DeleteMapper mapper = this.sqlSession.getMapper(DeleteMapper.class);
                mapper.execute(commandText);
            }
            else if(commandText.equals("DROP")){
                DropMapper mapper = this.sqlSession.getMapper(DropMapper.class);
                mapper.execute(commandText);
            }
            else if(commandText.equals("INSERT")){
                InsertMapper mapper = this.sqlSession.getMapper(InsertMapper.class);
                mapper.execute(commandText);
            }
            else if(commandText.equals("UPDATE")){
                UpdateMapper mapper = this.sqlSession.getMapper(UpdateMapper.class);
                mapper.execute(commandText);
            }
            else{
                throw new Exception("error command type");
            }
        }catch (Exception e){
            e.printStackTrace();
            builder.setStatus(false);
            return builder.build();
        }

        builder.setStatus(true);
        return builder.build();
    }
}