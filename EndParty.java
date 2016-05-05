package cmpsystem.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndParty extends AppCompatActivity {

    private TextView PuntuacionFinal;
    private Double ResultadoPuntuacion;
    private Button Exit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);
        PuntuacionFinal = (TextView) findViewById(R.id.PuntuacionFinal);
        ResultadoPuntuacion =  getIntent().getDoubleExtra("PuntuacionFinalPartida", 0.00);
        PuntuacionFinal.setText("Enhorabuena, has conseguido tras todo tu esfuerzo una puntuación de" +
                ResultadoPuntuacion.toString()+ "puntos.");


        Exit = (Button) findViewById(R.id.buttonExit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //codigo para cerrar todas las actividades
                // extraido de http://stackoverflow.com/questions/8615431/close-all-running-activities-in-an-android-application

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
