package org.mybatis.generator.extend.service;

import org.mybatis.generator.extend.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * GenericService
 *
 * Created by Bob Jiang on 2017/2/13.
 */
public interface GenericService<T extends BaseModel<PK>, PK extends Serializable> {

    /**
     * 按主键删除数据，物理删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 插入数据
     * @param model
     * @return
     */
    int insert(T model);

    /**
     * 插入数据，为空的属性不插入
     * @param model
     * @return
     */
    int insertSelective(T model);

    /**
     * 按ID查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(PK id);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 按ID更新数据
     * @param model
     * @return
     */
    int updateByPrimaryKey(T model);

    /**
     * 按ID更新数据，为空的属性不更新
     * @param model
     * @return
     */
    int updateByPrimaryKeySelective(T model);

}
