package cn.edu.cug.cs.gtl.protos;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentifiersTest {

    @Test
    public void allTest() {
        Identifier.Builder builder = Identifier.newBuilder();
        builder.setData(12l);
        Identifier identifier = builder.build();
        assertEquals(12l, identifier.getData());
    }
}