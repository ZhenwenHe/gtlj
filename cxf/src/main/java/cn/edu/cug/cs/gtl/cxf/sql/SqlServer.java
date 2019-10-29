package cn.edu.cug.cs.gtl.cxf.sql;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class SqlServer {
    protected SqlServer() throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(ResultService.class);
        sf.setResourceProvider(ResultService.class,
                new SingletonResourceProvider(new ResultService()));
        sf.setAddress("http://localhost:9000/");
        sf.create();
    }

    public static void main(String[] args) throws Exception {
        new SqlServer();
        System.out.println("Sql Command Service Server ready...");
        Thread.sleep(Long.MAX_VALUE);
        System.out.println("Sql Command Service Server exiting");
        System.exit(0);
    }
}
