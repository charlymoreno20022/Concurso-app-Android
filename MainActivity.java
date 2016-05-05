package cmpsystem.quizactivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//declaraciones
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mcheatbutton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private static final String key_index= "index";
    private static final String key_cheat= "cheat";
    private static final String key_puntuacion= "points";
    private boolean veracidad;
    private int pulsacionesretroceso = 0;
    private int pulsacionesavance = 0;
    private int correcto =0;
    private int incorrecto = 0;
    private double puntuacionfinal=0;
    private int contadorcheat = 3;
    private TextView mpuntuacion;
    public MediaPlayer Reproductor = null;
    public static int controlReproduccion= 0;






    //banco de preguntas del juego

    private Question[] mQuestionBank = new Question[]{

    new Question(R.string.pre1, false),
    new Question(R.string.pre2,true),
    new Question(R.string.pre3, true),
    new Question(R.string.pre4, true),
    new Question(R.string.pre5, false),
    new Question(R.string.pre6, false),
    new Question(R.string.pre7, false),
    new Question(R.string.pre8, true),
    new Question(R.string.pre9, true),

    };



    //funcion de actualizacion del view de android

    public void controlapp(){

        //comprobamos la longitud del numero de preguntas para cambiar hasta un maximo y que no devuelva error
        //se actualiza la puntuacion y la pregunta a la que corresponda

        if(mCurrentIndex < mQuestionBank.length-1) {

           Updatepuntuacion();
           mCurrentIndex++;
           UpdateQuestion();




        }else if (mCurrentIndex < mQuestionBank.length){

            Updatepuntuacion();
            Intent j = new Intent(MainActivity.this, EndParty.class);
            j.putExtra("PuntuacionFinalPartida", puntuacionfinal);
            startActivity(j);


        }



    }
    //control de puntuacion de la app de preguntas

    private void Updatepuntuacion(){
        //si la puntuacion final es negativa se corrige seteando todo a cero.

        if(puntuacionfinal<0){

            puntuacionfinal=0;
            correcto=0;
            pulsacionesretroceso=0;
            pulsacionesavance=0;
            incorrecto=0;
        }

        //aqui he seteado un sistema de penalizacion en la puntuacion por volver atras, o avanzar a la siguiente pregunta o por fallo

        puntuacionfinal = correcto- (pulsacionesretroceso*2)-(pulsacionesavance*0.35)-(incorrecto*0.75);

    }

    //actualizacion de las preguntas y puntuacion

    private void UpdateQuestion(){


            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
            mpuntuacion.setText("puntuacion" + ":" + String.valueOf(puntuacionfinal));




            //control para desactivar cambios de preguntas si es ultima o primera para que no devuelva error

            if(mCurrentIndex < 1){
                mPrevButton.setVisibility(View.INVISIBLE);
            }else{

                mPrevButton.setVisibility(View.VISIBLE);
            }
            if(mCurrentIndex != mQuestionBank.length-1){
                mNextButton.setVisibility(View.VISIBLE);
            }else{

                mNextButton.setVisibility(View.INVISIBLE);
            }

        //solo se dispone de un numero determinado de trucos, por lo que si llega a cero se anula

            if (contadorcheat>0){

                mcheatbutton.setVisibility(View.VISIBLE);
                mcheatbutton.setText(String.valueOf(contadorcheat));
            }else{

                mcheatbutton.setVisibility(View.INVISIBLE);
            }





    }
//para guardar el estado en el caso de cambio de orientacion

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
       savedInstanceState.putInt(key_index, mCurrentIndex);
       savedInstanceState.putInt(key_cheat, contadorcheat);
       savedInstanceState.putDouble(key_puntuacion, puntuacionfinal);
       super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //seleccion iconos en el xml
        mFalseButton = (Button) findViewById(R.id.FalseButton);
        mFalseButton.setBackgroundColor(Color.RED);
        mTrueButton = (Button) findViewById(R.id.TrueButton);
        mTrueButton.setBackgroundColor(Color.GREEN);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton=(ImageButton) findViewById(R.id.previus_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mcheatbutton= (Button) findViewById(R.id.cheat_button);
        mpuntuacion = (TextView) findViewById(R.id.puntuacion);
        UpdateQuestion();

        //control de musica para que no se encienda sola cada vez que se crea la actividad

         if (controlReproduccion == 0 ){

            Reproductor = MediaPlayer.create(this,R.raw.bandicoot);
            Reproductor.setLooping(true);
            Reproductor.setVolume(100,100);
             Reproductor.start();
             controlReproduccion=1;


        }




        //Recupera indice tras cerrar y abrir activity a traves de savedinstancestate
        if (savedInstanceState != null) {

            mCurrentIndex = savedInstanceState.getInt(key_index);
            contadorcheat = savedInstanceState.getInt(key_cheat);
            puntuacionfinal = savedInstanceState.getDouble(key_puntuacion);
            UpdateQuestion();
            Updatepuntuacion();



        }


        //Text view de preguntas

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentIndex++;
                UpdateQuestion();
            }
        });


    //boton de retroceso a la pregunta

      mPrevButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


                  mPrevButton.setVisibility(View.VISIBLE);
                  pulsacionesretroceso++;
                  mCurrentIndex--;
                  Updatepuntuacion();
                  UpdateQuestion();

          }
      });




    //botton de verdadero
      mTrueButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              veracidad = mQuestionBank[mCurrentIndex].isAnswerTrue();
              if(veracidad){

                  Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                  correcto++;
              }else{

                  Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                  incorrecto++;
              }

              Updatepuntuacion();
              controlapp();

          }
      });



        //boton de trampas

        mcheatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean truco = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = TrickActivity.newIntent(MainActivity.this, truco);
                startActivity(i);
                contadorcheat--;
                UpdateQuestion();
                Updatepuntuacion();




            }
        });




        //boton de falso

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean veracidad = mQuestionBank[mCurrentIndex].isAnswerTrue();
                if (veracidad) {

                    Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                    incorrecto++;
                } else {

                    Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                    correcto++;
                }


                controlapp();
                Updatepuntuacion();
            }
        });




        //boton de siguiente pregunta

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    mNextButton.setVisibility(View.VISIBLE);
                    pulsacionesavance++;
                    mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                    UpdateQuestion();
                    Updatepuntuacion();


            }

        });




    }


}
