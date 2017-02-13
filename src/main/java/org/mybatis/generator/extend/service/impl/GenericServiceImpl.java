package org.mybatis.generator.extend.service.impl;

import org.mybatis.generator.extend.mapper.GenericMapper;
import org.mybatis.generator.extend.model.BaseModel;
import org.mybatis.generator.extend.service.GenericService;

import java.io.Serializable;
import java.util.List;

/**
 * GenericServiceImpl
 *
 * Created by Bob Jiang on 2017/2/13.
 */
public abstract class GenericServiceImpl<T extends BaseModel<PK>, PK extends Serializable>
        implements GenericService<T, PK> {

    public abstract GenericMapper<T, PK> getGenericMapper();

    public int deleteByPrimaryKey(PK id) {
        return getGenericMapper().deleteByPrimaryKey(id);
    }

    public int insert(T model) {
        return getGenericMapper().insert(model);
    }

    public int insertSelective(T model) {
        return getGenericMapper().insertSelective(model);
    }

    public T selectByPrimaryKey(PK id) {
        return getGenericMapper().selectByPrimaryKey(id);
    }

    public List<T> selectAll() {
        return getGenericMapper().selectAll();
    }

    public int updateByPrimaryKey(T model) {
        return getGenericMapper().updateByPrimaryKey(model);
    }

    public int updateByPrimaryKeySelective(T model) {
        return getGenericMapper().updateByPrimaryKeySelective(model);
    }
}
