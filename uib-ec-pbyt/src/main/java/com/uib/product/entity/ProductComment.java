package com.uib.product.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class ProductComment {
    private String id;
    
    @NotNull(message="内容不能为空")  //此处为校验注解
    private String content;

    private String ip;

    private String isGuests;

    private Integer score;
    
    @NotNull(message="订单项编号不能为空")  
    private String orderTableItemId;
    
    @NotNull(message="商品编号不能为空")  
    private String productId;
    
    @NotNull(message="会员编号不能为空")  
    private String memberId;

    private String reUserId;

    private String reContent;

    private String reCommentId;

    private String contentType;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getIsGuests() {
        return isGuests;
    }

    public void setIsGuests(String isGuests) {
        this.isGuests = isGuests == null ? null : isGuests.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getOrderTableItemId() {
        return orderTableItemId;
    }

    public void setOrderTableItemId(String orderTableItemId) {
        this.orderTableItemId = orderTableItemId == null ? null : orderTableItemId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getReUserId() {
        return reUserId;
    }

    public void setReUserId(String reUserId) {
        this.reUserId = reUserId == null ? null : reUserId.trim();
    }

    public String getReContent() {
        return reContent;
    }

    public void setReContent(String reContent) {
        this.reContent = reContent == null ? null : reContent.trim();
    }

    public String getReCommentId() {
        return reCommentId;
    }

    public void setReCommentId(String reCommentId) {
        this.reCommentId = reCommentId == null ? null : reCommentId.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}