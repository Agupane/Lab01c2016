package ar.edu.utn.frsf.isi.dam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;

public class Actividad extends AppCompatActivity implements View.OnClickListener{
    float montoPlazo;
    String montoPlazoFijo;
    int colorMsg;
    String msg
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button botonPlazoFijo = (Button) findViewById(R.id.botonPlazoFijo);
        botonPlazoFijo.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        EditText mail = (EditText) findViewById(R.id.editTextMail);
        TextView msgError = (TextView) findViewById(R.id.textViewError);
        if(mail.getText().toString().isEmpty())
        {
            colorMsg = getResources().getColor(R.color.ROJO);
            msgError.setTextColor(colorMsg);
            msgError.setText(R.string.msgErrorPlazoFijo);
        }
        else
        {
            montoPlazoFijo = Float.toString(montoPlazo);
            colorMsg = getResources().getColor(R.color.VERDE);
            msgError.setTextColor(colorMsg);
            msgError.setText(R.string.msgExitoPlazoFijo1+R.string.msgExitoPlazoFijo2);
           // msgError.setText(R.string.msgExitoPlazoFijo1+montoPlazoFijo+R.string.msgExitoPlazoFijo2);
        }
    }


}
