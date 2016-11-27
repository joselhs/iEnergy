import org.junit.*;

import controllers.Secure;
import controllers.Security;
import controllers.Users;

import java.util.*;
import play.test.*;
import models.*;

public class Tests extends UnitTest {


    @Test
    public void userConnect() {
    	//Usuario y contraseña correctos
        assertEquals(Security.authenticate("josholsan@gmail.com", "josejose"),true);
        
        //Usuario correcto, contraseña incorrecta
        assertEquals(Security.authenticate("josholsan@gmail.com", "badpassword"),false);
        
        //Usuario inexistente
        assertEquals(Security.authenticate("falseuser@gmail.com", "josejose"),false);
    }

}
