package cn.edu.cug.cs.gtl.cxf.sql;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.protos.SqlResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Path("/resultservice/")
@Produces("text/xml")
public class ResultService {
    Session session;
    ResultService(){
        session=Session.open();
    }
    @GET
    @Path("/execute/{id}/")
    public Result execute(@PathParam("id") String id) {
        Result r = new Result();
        try {
            String sql = URLDecoder.decode(id,"utf-8");
            System.out.println("----invoking execute, param is: " + sql);
            r.setValue(session.execute(sql).toString());
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return r;
    }
}
