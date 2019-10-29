package cn.edu.cug.cs.gtl.grpc;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.mybatis.mapper.common.*;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.util.StringUtils;
import io.grpc.stub.StreamObserver;
import org.apache.ibatis.session.SqlSession;

import java.util.LinkedHashMap;
import java.util.List;

public class SqlCommandService extends SqlCommandServiceGrpc.SqlCommandServiceImplBase {
    private Session sqlSession;

    public SqlCommandService(Session sqlSession) {
        super();
        this.sqlSession = sqlSession;
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void execute(SqlCommand request, StreamObserver<SqlResult> responseObserver) {
        responseObserver.onNext(sqlSession.execute(request));
        responseObserver.onCompleted();
    }

}