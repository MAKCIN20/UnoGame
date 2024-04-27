package LoginPage;
/**
 * construction hatalı  olabilir bu kısıma bakılması gerekiyor
 * ek olarak login kısmındaki write to file kısmı buraya çekilip düzeltilmeli
 * istatistiklerin txt ye yazılması için bir fonksiyonda oraya yazılması gerekiyor
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private String[] usersList ;
    private String[] passwordsList;
    protected boolean isLogged;


    public User(boolean isLogged) {
        this.usersList =readUsername();
        this.passwordsList=readPasswords();
        this.isLogged=false;
    }

    public String[] readUsername() {
        String[] userList = new String[0];
        try {
            File myObj = new File("usernameList.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String username = myReader.next();
                if(userList.length==0){
                    userList[0]=username;
                }
                else {
                    userList[userList.length + 1] = username;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return userList;
    }
    public String[] readPasswords() {
        String[] passwordList = new String[0];
        try {
            File myObj = new File("passwordList.txt");
            Scanner myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {
                String password = myReader.next();
                if (passwordList.length == 0) {
                    passwordList[0] = password;
                } else {
                    passwordList[passwordList.length + 1] = password;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return passwordList;
    }

    public String[] getUsersList() {
        return usersList;
    }

    public void setUsersList(String[] usersList) {
        this.usersList = usersList;
    }

    public String[] getPasswordsList() {
        return passwordsList;
    }

    public void setPasswordsList(String[] passwordsList) {
        this.passwordsList = passwordsList;
    }

}
