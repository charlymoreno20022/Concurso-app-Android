package cmpsystem.quizactivity;

/**
 * Created by Carlos on 05/01/2016.
 */
public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int mTextResId, boolean mAnswerTrue ){

        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;


    }
    public boolean isAnswerTrue() {

        return mAnswerTrue;
    }
    //metodo para futuras implementaciones
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    //metodo para futuras implementaciones
    public void setTextResId(int textResId) {


        mTextResId = textResId;
    }


}
