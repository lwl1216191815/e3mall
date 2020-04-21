package cn.e3mall.mapper;

import cn.e3mall.pojo.TbFile;
import cn.e3mall.pojo.TbFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbFileMapper {
    int countByExample(TbFileExample example);

    int deleteByExample(TbFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbFile record);

    int insertSelective(TbFile record);

    List<TbFile> selectByExample(TbFileExample example);

    TbFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbFile record, @Param("example") TbFileExample example);

    int updateByExample(@Param("record") TbFile record, @Param("example") TbFileExample example);

    int updateByPrimaryKeySelective(TbFile record);

    int updateByPrimaryKey(TbFile record);
}