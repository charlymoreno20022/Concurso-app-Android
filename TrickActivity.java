package cmpsystem.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrickActivity extends AppCompatActivity {

    private TextView respuesta;
    private static final String ConocerRespuesta = "cmpsystem.quizactivity.conocerrespuesta";
    private Boolean verdadofalso;
    private Button botonvolver;


    public static Intent newIntent(Context contexto, boolean respuesta) {

        Intent i = new Intent(contexto, TrickActivity.class);
        i.putExtra(ConocerRespuesta, respuesta);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        respuesta = (TextView) findViewById(R.id.Respuesta);
        botonvolver = (Button) findViewById((R.id.botonvolver));


        //gestion del objeto intent

        verdadofalso = getIntent().getBooleanExtra(ConocerRespuesta,false);

        if (verdadofalso){

            respuesta.setText((R.string.true_button));
        }else{

            respuesta.setText((R.string.false_button));
        }

        //definicion del boton volver

        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}