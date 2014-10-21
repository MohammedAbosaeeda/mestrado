package keso.communication.authserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import keso.communication.common.KesoMD5;

public class KesoAuthenticationMenu {
	public static void menu() {
		while(true) {
			System.out.println("Keso Authentication Server V1.0");
			System.out.println("----------------------------------------------------------------");
			System.out.println("Menu:");
			System.out.println("1. Create new Account");
			System.out.println("2. Edit Account");
			System.out.println("3. Delete Account");
			System.out.println("4. View Account");
			System.out.println("5. List all accounts");
			System.out.println("");
			System.out.println("X. Exit");
			System.out.println("");
			System.out.print("INPUT: ");
			String input = KesoAuthenticationMenu.readLine();
			
			input = input.trim();
			if (input.equals("1")) {
				createNewAccount();
			} else if (input.equals("2")) {
				editAccount();
			} else if (input.equals("3")) {
				deleteAccount();
			} else if (input.equals("4")) {
				viewAccount();
			} else if (input.equals("5")) {
				listAllAccounts();
			} else if (input.toLowerCase().equals("x")) {
				break;
			} else {
				System.out.println("Illegal Command!");
			}
			System.out.println("");
			
			
		}
	}
	
	private static void listAllAccounts() {
		try {
			int counter = 0;
			String username;
			int priority;
			Properties properties = new Properties();
			properties.load(new FileInputStream(KesoAuthenticationServer.accountfile));
			System.out.println("List of all Accounts:");
			for (Enumeration e = properties.keys(); e.hasMoreElements(); ) {
				String key = (String) e.nextElement();
				if (key.startsWith("client.name.")) {
					username = key.substring(("client.name.").length());
					try {
						priority = Integer.parseInt(properties.getProperty("client.priority." + username, Integer.toString(Integer.MAX_VALUE)));
					} catch(Exception ex) {
						priority = Integer.MAX_VALUE;
					}
					System.out.println("\t" + (++counter) + ": " + username +  " - Priority: " + priority);
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void viewAccount() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(KesoAuthenticationServer.accountfile));
			System.out.print("Information about User: ");
			String username = KesoAuthenticationMenu.readLine();
			if (username != null && username.length() > 0) {
				String info = properties.getProperty("client.name." + username, null);
				if (info == null) {
					System.out.println("User not found!");
				} else {
					String priority = properties.getProperty("client.priority." + username, Integer.toString(Integer.MAX_VALUE));
					System.out.println("Username: " + username + " - Priority: " + priority);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void deleteAccount() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(KesoAuthenticationServer.accountfile));
			System.out.print("Delete Account of User: ");
			String username = KesoAuthenticationMenu.readLine();
			if (username != null && username.length() > 0) {
				String info = properties.getProperty("client.name." + username, null);
				if (info == null) {
					System.out.println("User not found!");
				} else {
					properties.remove("client.name." + username);
					properties.remove("client.priority." + username);
					System.out.println("Account deleted!");
					properties.store(new FileOutputStream(KesoAuthenticationServer.accountfile), "");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void editAccount() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(KesoAuthenticationServer.accountfile));
			System.out.print("Edit Account of User: ");
			String username = KesoAuthenticationMenu.readLine();
			if (properties.getProperty("client.name." + username, null) == null) {
				System.out.println("User not found!");
			} else {
				while (true) {
					System.out.println("What do you want to edit?");
					System.out.println("1. Username");
					System.out.println("2. Password");
					System.out.println("3. Priority");
					System.out.println("");
					System.out.println("B. Back");
					
					String input = KesoAuthenticationMenu.readLine();
					input = input.trim().toLowerCase();
					if (input.equals("1")) {
						System.out.print("Type in the new Username: " );
						String newusername = KesoAuthenticationMenu.readLine().trim();
						if (newusername != null && newusername.length() > 0) {
							String pwd = properties.getProperty("client.name." + username);
							properties.remove("client.name." + username);
							properties.setProperty("client.name." + newusername, pwd);
							System.out.println("Username changed!");
							username = newusername;
						} else {
							System.out.println("Username was not changed!");
						}
					} else if (input.equals("2")) {
						System.out.print("Type in the new Password: ");
						String newpassword = KesoAuthenticationMenu.readLine().trim();
						if (newpassword != null && newpassword.length() > 0) {
							properties.setProperty("client.name." + username, KesoMD5.getMD5Hash(newpassword));
							System.out.println("Password changed!");
						} else {
							System.out.println("Password was not changed!");
						}
					} else if (input.equals("3")) {
						System.out.print("Type in the new Priority: ");
						String priority = KesoAuthenticationMenu.readLine().trim();
						if (priority != null && priority.length() > 0) {
							properties.setProperty("client.priority." + username, priority);
							System.out.println("Priority changed!");
						} else {
							System.out.println("Priority was not changed!");
						}
					} else if (input.equals("b")) {
						break;
					} else {
						System.out.println("Illegal Command!");
					}
				}
			}
			properties.store(new FileOutputStream(KesoAuthenticationServer.accountfile), "");
		} catch(Exception e) {
			
		}
	}

	private static void createNewAccount() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(KesoAuthenticationServer.accountfile));
			System.out.println("Create new Account!");
			System.out.print("Username: ");
			String username = KesoAuthenticationMenu.readLine().trim();
			if (username != null && username.length() > 0) {
				if (properties.getProperty("client.name." + username, null) == null) {
					System.out.print("Password: ");
					String password = KesoAuthenticationMenu.readLine().trim();
					if (password != null && password.length() > 0) {
						System.out.print("Priority: ");
						String priority = KesoAuthenticationMenu.readLine().trim();
						if (priority != null && priority.length() > 0) {
							properties.setProperty("client.name." + username, KesoMD5.getMD5Hash(password));
							properties.setProperty("client.priority." + username, priority);
							properties.store(new FileOutputStream(KesoAuthenticationServer.accountfile), "");
							System.out.println("New account created!");
						} else {
							System.out.println("Could not create new account!");
						}
					} else {
						System.out.println("Could not create new account!");
					}
				} else {
					System.out.println("Account already exists!");
				}
			} else {
				System.out.println("Could not create new account!");
			}
			properties.store(new FileOutputStream(KesoAuthenticationServer.accountfile), "");
		} catch(Exception e) {
			
		}
		
	}

	public static String readLine() {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String text = null;
		try {
			text = read.readLine();
		} catch(Exception e) {
			text = null;
		}
		return text;
	}
}
