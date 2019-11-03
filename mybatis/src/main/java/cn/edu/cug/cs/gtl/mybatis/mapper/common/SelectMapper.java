package cn.edu.cug.cs.gtl.mybatis.mapper.common;

import java.util.LinkedHashMap;
import java.util.List;

public interface SelectMapper {
    List<LinkedHashMap<String, Object>> query(String sqlSelect);
}
