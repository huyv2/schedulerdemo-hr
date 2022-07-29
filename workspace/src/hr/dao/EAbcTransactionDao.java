package hr.dao;

public class EAbcTransactionDao {
	private String transactionId;
	private String tokenId;
	private String mobileNo;
	private String account;
	private String pan;
	private long amount;
	private String ccy;
	private String transactionType;
	private String eWalletType;
	private int otpTimes;
	private String transactionResult;
	private String refNo;
	private String errorMessage;
	private String transactionTime;
	private String transactionDate;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTokenId() {
		return tokenId != null ? tokenId : "null";
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getCcy() {
		return ccy != null ? ccy : "null";
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getTransactionType() {
		return transactionType != null ? transactionType : "null";
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String geteWalletType() {
		return eWalletType;
	}
	public void seteWalletType(String eWalletType) {
		this.eWalletType = eWalletType;
	}
	public int getOtpTimes() {
		return otpTimes;
	}
	public void setOtpTimes(int otpTimes) {
		this.otpTimes = otpTimes;
	}
	public String getTransactionResult() {
		return transactionResult;
	}
	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
	}
	public String getRefNo() {
		return refNo != null ? refNo : "null";
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
}
