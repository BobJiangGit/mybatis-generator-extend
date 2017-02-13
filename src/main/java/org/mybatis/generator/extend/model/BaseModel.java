package org.mybatis.generator.extend.model;

import java.io.Serializable;

/**
 * BaseModel
 *
 * Created by Bob Jiang on 2017/2/10.
 */
public class BaseModel<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -797336545554417159L;

    protected PK id;

    public BaseModel() {
    }

    public BaseModel(PK id) {
        this.id = id;
    }

    public PK getId() {
        return this.id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
