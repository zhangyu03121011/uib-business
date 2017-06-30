package com.uib.mobile.dto;

/**
 * @Todo 评论统计
 * @date 2015年11月10日
 * @author Ly
 */
public class CommentCountDto {

	private String id;//
	private String productId;//
	private int praise;// 好评人数
	private double praiseRatio;// 好评率
	private int general;// 中评人数
	private double generalRatio;// 中评率
	private int bad;// 差评人数
	private double badRatio;// 差评率
	private int totalScore;// 总评分数
	private double productScore;// 商品分数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public double getPraiseRatio() {
		return praiseRatio;
	}

	public void setPraiseRatio(double praiseRatio) {
		this.praiseRatio = praiseRatio;
	}

	public int getGeneral() {
		return general;
	}

	public void setGeneral(int general) {
		this.general = general;
	}

	public double getGeneralRatio() {
		return generalRatio;
	}

	public void setGeneralRatio(double generalRatio) {
		this.generalRatio = generalRatio;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

	public double getBadRatio() {
		return badRatio;
	}

	public void setBadRatio(double badRatio) {
		this.badRatio = badRatio;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public double getProductScore() {
		return productScore;
	}

	public void setProductScore(double productScore) {
		this.productScore = productScore;
	}

}
