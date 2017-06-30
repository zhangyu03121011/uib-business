package com.uib.mobile.dto;

public class MemberLoginStatusDto {
	

	    private String sessionId;

	    private String memberId;
	    
	    private String createTime;
	    
	    private String userName;
	    
	    private String phone;
	    
	    private String approveFlag;


		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getMemberId() {
			return memberId;
		}

		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getApproveFlag() {
			return approveFlag;
		}

		public void setApproveFlag(String approveFlag) {
			this.approveFlag = approveFlag;
		}
		
		

	
	    
	
	    
	    

}
