package ar.edu.utn.frsf.isi.dam;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Agustin on 8/27/2016.
 */
public class ValidatorGeneric {
    private static ValidatorGeneric ourInstance = new ValidatorGeneric();

    public static ValidatorGeneric getInstance() {
        return ourInstance;
    }

    private ValidatorGeneric() {
    }
    /**
     * Valida si el editText parametro contiene un email
     * @param email editText que contiene email
     * @return
     */
    public boolean validarMail(EditText email)
    {
        try
        {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(email.getText());
            return matcher.matches();
        }
        catch (Exception e) {
            //  e.printStackTrace();
            return false;
        }
    }
}
