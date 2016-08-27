package ar.edu.utn.frsf.isi.dam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class Actividad extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private float montoRecibido,montoDepositado;
    private int cantDias, colorMsg;
    private String stringMontoDepositado;
    private EditText editTextMontoDepositado;
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
        return false;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        cantDias=progress;
        textViewCantDias = (TextView) findViewById(R.id.textViewCantDias);
        textViewCantDias.setText(Integer.toString(cantDias));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        stringMontoDepositado = ((TextView) findViewById(R.id.editTextMontoDepositado)).getText().toString();
        montoRecibido = calcularMontoPlazoFijo();
        textViewMontoRecibido.setText(Float.toString(montoRecibido));

    }

    /**
     * Devuelve el monto del plazo fijo incluyendo los intereses
     * @return
     */
    private float calcularMontoPlazoFijo()
    {
        float monto,taza;
        montoDepositado = obtenerMontoDepositado();
        taza = calcularTazaInteres();
        System.out.println(Float.toString(taza));
        System.out.println(Float.toString(montoDepositado));
        monto = (float) (montoDepositado * ( Math.pow((1+(taza/100)),(cantDias/360)) - 1 ));
        return monto;
    }

    /**
     * Devuelve la taza de interes dependiendo del monto y la cantidad de dias
     * @return
     */
    private float calcularTazaInteres()
    {
        float taza;
        if(cantDias < 30)
        {
            if(montoDepositado <= 5000)
            {
                taza=0.25f;
            }
            else
            {
                if(montoDepositado <= 99999)
                {
                    taza=0.3f;
                }
                else
                {
                    taza=0.35f;
                }
            }
        }
        else
        {
            if(montoDepositado <= 5000)
            {
                taza=0.275f;
            }
            else
            {
                if(montoDepositado <= 99999)
                {
                    taza=0.323f;
                }
                else
                {
                    taza=0.385f;
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
    }

    /**
     * Transforma el string del monto depositado con el simbolo $ a valor flotante
     * @return
     */
    private float obtenerMontoDepositado()
    {
        float monto;
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        ParsePosition ps = new ParsePosition(1);
        System.out.println(stringMontoDepositado);
        long numero =(long) nf.parseObject(stringMontoDepositado,ps);
        monto = new Float(numero);
        return monto;
    }
}
