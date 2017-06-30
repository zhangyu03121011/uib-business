package com.uib.ecmanager.modules.product.entity;

import java.util.Date;

public class ProductCommentImg {
    private String id;

    private String orderTableItemId;

    private String path;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderTableItemId() {
        return orderTableItemId;
    }

    public void setOrderTableItemId(String orderTableItemId) {
        this.orderTableItemId = orderTableItemId == null ? null : orderTableItemId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}