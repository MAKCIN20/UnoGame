/************** Pledge of Honor ******************************************
 I hereby certify that I have completed this programming project on my own
 without any help from anyone else. The effort in the project thus belongs
 completely to me. I did not search for a solution, or I did not consult any
 program written by others or did not copy any program from other sources. I
 read and followed the guidelines provided in the project description.
 READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
 SIGNATURE: <Mehmet Emin Akçin, 76517>
 *************************************************************************/


package test;

import GUI.WelcomePage;

import Login.User;
import Login.UserStatistics;
import cards.Cards;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String username;
        Cards cards=new Cards();
        cards.initializer();
        System.out.println(cards.deck);

        UserStatistics userStatistics = new UserStatistics();
        WelcomePage x=new WelcomePage();


    }

}