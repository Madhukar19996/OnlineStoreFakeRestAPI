package pojo;

public class Login {

	private String username;
	private String password;

	//Constructor
	public Login(String username,String password)
	{
		this.username=username;
		this.password=password;
	}

	//Getters&Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
