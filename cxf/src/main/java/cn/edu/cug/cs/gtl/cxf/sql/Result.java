package cn.edu.cug.cs.gtl.cxf.sql;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Result")
public class Result {
    private String value;//JSON Format

    public String getValue() {
        return this.value;
    }

    public void setValue(String v) {
        this.value = v;
    }
}
