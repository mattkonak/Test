package beans;

import java.util.List;

public class RepoObject {
	private List<String> full_name;
	private String ownerLogin;
	private int loginId;

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public List<String> getFull_name() {
		return full_name;
	}

	public void setFull_name(List<String> full_name) {
		this.full_name = full_name;
	}

	public String getOwnerLogin() {
		return ownerLogin;
	}

	public void setOwnerLogin(String ownerLogin) {
		this.ownerLogin = ownerLogin;
	}

	

	
	

}
