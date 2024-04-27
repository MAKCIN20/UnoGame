package LoginPage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Login extends User{
    public String username;
    public String password;

    public Login(String[] usersList, String[] passwords, boolean isLogged, String username, String password) {
        super(isLogged);
        this.username = username;
        this.password = password;
    }


    public void login() {
        int index=0;
        for (String i:getUsersList()){
            if(username==i && getPasswordsList()[index]==password){
                isLogged=true;
                break;
           }
            else{
                index++;
            }
       }
    }
    public void createAccount(String username, String password){
        String[] usersList = getUsersList();
        String[] passwordsList = getPasswordsList();
        usersList = Arrays.copyOf(usersList, usersList.length + 1);
        passwordsList = Arrays.copyOf(passwordsList, passwordsList.length + 1);
        usersList[usersList.length+1] = username;
        passwordsList[passwordsList.length+1] = password;
        setUsersList(usersList);
        setPasswordsList(passwordsList);
    }
    public void logout(){
        writeUserList(getUsersList());
        writePasswordsList(getPasswordsList());
        isLogged=false;
    }

    public void writeUserList(String[] usersList){
        try {
            FileWriter myWriter = new FileWriter("usernameList.txt");
            for (String string : usersList) {
                myWriter.write(string);
                myWriter.write(" ");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void writePasswordsList( String[] passwordsList){
        try {
            FileWriter myWriter = new FileWriter("passwordList.txt");
            for (String string : passwordsList) {
                myWriter.write(string);
                myWriter.write(" ");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
