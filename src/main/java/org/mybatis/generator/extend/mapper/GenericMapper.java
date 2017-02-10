package org.mybatis.generator.extend.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.generator.extend.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bob Jiang on 2017/2/10.
 */
public interface GenericMapper<T extends BaseModel<PK>, PK extends Serializable> {

    int deleteByPrimaryKey(PK id);

    int insert(T model);

    int insertSelective(T model);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(T model);

    int updateByPrimaryKey(T model);

}
