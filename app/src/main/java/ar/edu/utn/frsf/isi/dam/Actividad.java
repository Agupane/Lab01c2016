package ar.edu.utn.frsf.isi.dam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.regex.Pattern;

public class Actividad extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private float montoRecibido,montoDepositado,cantDias;
    private int  colorMsg;
    private String stringMontoDepositado;
    private EditText editTextMontoDepositado,editTextMail;
    private TextView textViewMontoRecibido,textViewCantDias;
    private Toolbar toolbar;
    private Button botonPlazoFijo;
    private SeekBar barraPlazoFijo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);
        cargarVariables();
        setSupportActionBar(toolbar);
        botonPlazoFijo.setOnClickListener(this);
        barraPlazoFijo.setOnSeekBarChangeListener(this);

    }
    @Override
    public void onClick(View view)
    {
        EditText mail = (EditText) findViewById(R.id.editTextMail);
        TextView msgError = (TextView) findViewById(R.id.textViewError);
        if(validarDatos() == false)
        {
            colorMsg = getResources().getColor(R.color.ROJO);
            msgError.setTextColor(colorMsg);
            msgError.setText(R.string.msgErrorPlazoFijo);
        }
        else
        {

            colorMsg = getResources().getColor(R.color.VERDE);
            msgError.setTextColor(colorMsg);
//            msgError.setText(R.string.msgExitoPlazoFijo1+R.string.msgExitoPlazoFijo2);
           // msgError.setText(R.string.msgExitoPlazoFijo1+montoPlazoFijo+R.string.msgExitoPlazoFijo2);
        }
    }
    private boolean validarDatos()
    {
        return ValidatorGeneric.getInstance().validarMail(editTextMail);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        cantDias=progress;
        textViewCantDias = (TextView) findViewById(R.id.textViewCantDias);
        textViewCantDias.setText(Float.toString(cantDias));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        cantDias=seekBar.getProgress();
        stringMontoDepositado = ((TextView) findViewById(R.id.editTextMontoDepositado)).getText().toString();
        montoRecibido = calcularMontoPlazoFijo();
        textViewMontoRecibido.setText("Monto a recibir: $"+Float.toString(montoRecibido));
    }

    /**
     * Devuelve el monto del plazo fijo incluyendo los intereses
     * @return
     */
    private float calcularMontoPlazoFijo()
    {
        float taza,base,exponente,potencia;
        double interes,montoRecibido;
        montoDepositado = obtenerMontoDepositado();
        taza = calcularTazaInteres();
        base = (1f+taza);
        exponente = cantDias/360f;
        potencia = (float) Math.pow(base, exponente);
        interes = ( montoDepositado * (potencia-1f) );
        montoRecibido = montoDepositado+interes;
        return (float) montoRecibido;
    }

    /**
     * Devuelve la taza de interes dependiendo del monto depositado y la cantidad de dias
     * @return
     */
    private float calcularTazaInteres()
    {
        float taza;
        if(cantDias < 30)
        {
            if(montoDepositado <= 5000)
            {
                taza=(float) R.dimen.minMontoMinDias;
            }
            else
            {
                if(montoDepositado <= 99999)
                {
                    taza=(float) R.dimen.medMontoMinDias;
                }
                else
                {
                    taza=(float) R.dimen.maxMontoMinDias;
                }
            }
        }
        else
        {
            if(montoDepositado <= 5000)
            {
                taza=(float) R.dimen.minMontoMaxDias;
            }
            else
            {
                if(montoDepositado <= 99999)
                {
                    taza=(float) R.dimen.medMontoMaxDias;
                }
                else
                {
                    taza=(float) R.dimen.maxMontoMaxDias;
                }
            }
        }
        return taza;
    }

    /**
     * Permite inicializar las variables auxiliares
     */
    private void cargarVariables()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        botonPlazoFijo = (Button) findViewById(R.id.botonPlazoFijo);
        barraPlazoFijo = (SeekBar) findViewById(R.id.seekBarPlazoFijo);
        textViewCantDias = (TextView) findViewById(R.id.textViewCantDeDias);
        textViewMontoRecibido = (TextView) findViewById(R.id.textViewMontoRecibido);
        editTextMontoDepositado = (EditText) findViewById(R.id.editTextMontoDepositado);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
    }

    /**
     * Transforma el string del monto depositado con el simbolo $ a valor flotante
     * @return
     */
    @Deprecated
    private float obtenerMontoDepositado2()
    {
        float monto;
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        ParsePosition ps = new ParsePosition(1);
        long numero =(long) nf.parseObject(stringMontoDepositado,ps);
        monto = new Float(numero);
        return monto;
    }

    /**
     * Dado el valor del monto depositado en string, devuelve el valor flotante
     * @return
     */
    private float obtenerMontoDepositado()
    {
        String stringMontoDepositado;
        stringMontoDepositado = ( editTextMontoDepositado ).getText().toString();
        return Float.parseFloat(stringMontoDepositado);
    }


}
