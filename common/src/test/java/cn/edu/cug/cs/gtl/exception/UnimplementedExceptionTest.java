package cn.edu.cug.cs.gtl.exception;

import cn.edu.cug.cs.gtl.geom.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class UnimplementedExceptionTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addTest() {
        try {

            Vector v = Vector.create();
            Class<?> cls = v.getClass();
            Method m = cls.getMethod("add", Vector.class);
            UnimplementedException e = new UnimplementedException(cls.getName() + "." + m.getName());
            assertTrue(e.getMessage().compareTo("VectorImpl.add") != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}