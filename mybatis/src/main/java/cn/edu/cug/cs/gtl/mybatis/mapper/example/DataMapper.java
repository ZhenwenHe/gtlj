package cn.edu.cug.cs.gtl.mybatis.mapper.example;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface DataMapper {
    @Select("select * from origin_dataset_infos where traincount<=30")
    public List<Map<Object, Object>> getInfos();
}
