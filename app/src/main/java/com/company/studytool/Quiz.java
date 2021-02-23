package com.company.studytool;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton choice1;
    RadioButton choice2;
    RadioButton choice3;
    RadioButton choice4;
    RadioButton checkedButton;
    ImageButton nextQuestion;
    TextView question;
    TextView questionNum;
    TextView timer;
    TextView score;
    String rightAnswer;
    int time = 10; // for testing purpose
    int scoreNum = 0;
    int position = 0;
    static int highScore = 0;
    private ArrayList<QuestionModel> dSQuiz = new ArrayList<>();
    Drawable pressedButton;
    Drawable disabledButton;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        pressedButton = getResources().getDrawable(R.drawable.button_pressed);
        disabledButton = getResources().getDrawable(R.drawable.button_disabled);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        radioGroup = findViewById(R.id.radioGroup);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        question = findViewById(R.id.question);
        nextQuestion = findViewById(R.id.nextQuestion);
        questionNum = findViewById(R.id.questionNum);
        timer = findViewById(R.id.timer);
        score = findViewById(R.id.score);
        nextQuestion.setEnabled(false);
        prepareDsQuiz();
        rightAnswer = dSQuiz.get(position).rightAnswer;
        questionNum.setText("Questions : " + (position + 1) + "/" + dSQuiz.size());
        question.setText(dSQuiz.get(position).question);
        choice1.setText(dSQuiz.get(position).choice1);
        choice2.setText(dSQuiz.get(position).choice2);
        choice3.setText(dSQuiz.get(position).choice3);
        choice4.setText(dSQuiz.get(position).choice4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedButton = findViewById(checkedId);
                if (checkedButton.getText().equals(dSQuiz.get(position).rightAnswer)) {
                    nextQuestion.setEnabled(true);
                    System.out.println(checkedButton.getId());
                    if (choice1.getId() == checkedButton.getId()) {
                        choice2.setBackground(disabledButton);
                        choice3.setBackground(disabledButton);
                        choice4.setBackground(disabledButton);
                        choice2.setEnabled(false);
                        choice3.setEnabled(false);
                        choice4.setEnabled(false);
                    } else if (choice2.getId() == checkedButton.getId()) {
                        choice1.setBackground(disabledButton);
                        choice3.setBackground(disabledButton);
                        choice4.setBackground(disabledButton);
                        choice1.setEnabled(false);
                        choice3.setEnabled(false);
                        choice4.setEnabled(false);
                    } else if (choice3.getId() == checkedButton.getId()) {
                        choice1.setBackground(disabledButton);
                        choice2.setBackground(disabledButton);
                        choice4.setBackground(disabledButton);
                        choice1.setEnabled(false);
                        choice2.setEnabled(false);
                        choice4.setEnabled(false);
                    } else if (choice4.getId() == checkedButton.getId()) {
                        choice1.setBackground(disabledButton);
                        choice2.setBackground(disabledButton);
                        choice3.setBackground(disabledButton);
                        choice1.setEnabled(false);
                        choice2.setEnabled(false);
                        choice3.setEnabled(false);
                    }
                    scoreNum++;
                    countDownTimer.cancel();
                    score.setText("Score : " + scoreNum);
                    Toast.makeText(Quiz.this, "RightAnswer", Toast.LENGTH_SHORT).show();
                }


            }
        });
        startTimer();
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (position < dSQuiz.size() - 1) {
//                    if (position >= dSQuiz.size()) {
//                        alertDialog("Do you wanna repeat this quiz ?", "Quiz Complete");
//                    }
                    position++;
                    questionNum.setText("Questions : " + (position + 1) + "/" + dSQuiz.size());
                    choice1.setBackground(pressedButton);
                    choice2.setBackground(pressedButton);
                    choice3.setBackground(pressedButton);
                    choice4.setBackground(pressedButton);
                    enableAllChoices();
                    question.setText(dSQuiz.get(position).question);
                    choice1.setText(dSQuiz.get(position).choice1);
                    choice2.setText(dSQuiz.get(position).choice2);
                    choice3.setText(dSQuiz.get(position).choice3);
                    choice4.setText(dSQuiz.get(position).choice4);
                    nextQuestion.setEnabled(false);
                    time = 10;
                    countDownTimer.start();

                } else alertDialog();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("0:" + time);
                time--;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timer.setText("0:0");
                choice1.setBackground(disabledButton);
                choice2.setBackground(disabledButton);
                choice3.setBackground(disabledButton);
                choice4.setBackground(disabledButton);
                disableAllChoices();
                nextQuestion.setEnabled(true);
                rightAnswer = dSQuiz.get(position).rightAnswer;
                Toast.makeText(Quiz.this, "i think >>" + rightAnswer + "<< is the right answer ..", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void alertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(Quiz.this)
                .setMessage("Do you wanna repeat this quiz ?").setTitle("Quiz Complete").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = 0;
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Quiz.this, ShowScoreActivity.class).putExtra("score", scoreNum);
                        startActivity(intent);
                    }
                }).show();
    }

    private boolean isCheckedChoiceRight(RadioButton button) {
        if (!button.getText().equals(dSQuiz.get(position).rightAnswer)) {
            return true;
        } else return false;
    }

    private void enableAllChoices() {
        choice1.setEnabled(true);
        choice2.setEnabled(true);
        choice3.setEnabled(true);
        choice4.setEnabled(true);
    }

    private void showRightChoice(RadioButton checkedOn) {
        if (checkedOn.getText().equals(dSQuiz.get(position).rightAnswer)) {
            choice2.setBackground(disabledButton);
            choice3.setBackground(disabledButton);
            choice4.setBackground(disabledButton);
            disableAllChoices();
        } else if (checkedOn.getText().equals(dSQuiz.get(position).rightAnswer)) {
            checkedOn.setBackground(disabledButton);
            choice3.setBackground(disabledButton);
            choice4.setBackground(disabledButton);
            disableAllChoices();
        } else if (checkedOn.getText().equals(dSQuiz.get(position).rightAnswer)) {
            checkedOn.setBackground(disabledButton);
            choice2.setBackground(disabledButton);
            choice4.setBackground(disabledButton);
            disableAllChoices();
        } else if (checkedOn.getText().equals(dSQuiz.get(position).rightAnswer)) {
            checkedOn.setBackground(disabledButton);
            choice2.setBackground(disabledButton);
            choice3.setBackground(disabledButton);
            disableAllChoices();
        }
    }

    private void disableAllChoices() {
        choice1.setEnabled(false);
        choice2.setEnabled(false);
        choice3.setEnabled(false);
        choice4.setEnabled(false);
    }

    public void prepareDsQuiz() {
        QuestionModel question1 = new QuestionModel("Two main measures for the efficiency of an algorithm are", "Processor and memory",
                "Complexity and capacity", "Data and space", "Time and space", "Time and space");
        QuestionModel question2 = new QuestionModel("The time factor when determining the efficiency of algorithm is measured by",
                "Counting the kilobytes of algorithm", "Counting the number of statements", "Counting microseconds",
                "Counting the number of key operations", "Counting the number of key operations");
        QuestionModel question3 = new QuestionModel("The space factor when determining the efficiency of algorithm is measured by",
                "Counting the maximum disk space needed by the algorithm", "Counting the average memory needed by the algorithm",
                "Counting the minimum memory needed by the algorithm", "Counting the maximum memory needed by the algorithm", "Counting the maximum memory needed by the algorithm");
        QuestionModel question4 = new QuestionModel("Which of the following cases does not exist in complexity theory", "Best case ",
                "Worst case", "Average case", "Null case", "Null case");
        QuestionModel question5 = new QuestionModel("The Worst case occur in linear search algorithm when", " Item is somewhere in the middle of the array",
                "Item is not in the array at all ", " Item is the last element in the array ", "Item is the last element in the array or is not there at all",
                "Item is the last element in the array or is not there at all");
        QuestionModel question6 = new QuestionModel("The Average case occur in linear search algorithm", "When Item is the last element in the array or is not there at all",
                "When Item is the last element in the array ", "When Item is not in the array at all ",
                "When Item is somewhere in the middle of the array", "When Item is somewhere in the middle of the array");
        QuestionModel question7 = new QuestionModel("The complexity of the average case of an algorithm is", "Much more simpler to analyze than that of worst case",
                "Sometimes more complicated and some other times simpler than that of worst case", "None of them ",
                "Much more complicated to analyze than that of worst case", "Much more complicated to analyze than that of worst case");
        QuestionModel question8 = new QuestionModel("The complexity of linear search algorithm is", "O(log n)", "O(n2)", "O(n log n) ", "O(n)", "O(n)");
        QuestionModel question9 = new QuestionModel("The complexity of Binary search algorithm is", "O(n)", "O(n log n)", "O(n2)", "O(log n)", "O(log n)");
        QuestionModel question10 = new QuestionModel(" The complexity of Bubble sort algorithm is", "O(n log n)", "O(log n)", "O(n)", "O(n2)", "O(n2)");
        QuestionModel question11 = new QuestionModel("The complexity of merge sort algorithm is", "O(n)", "O(log n)", "O(n2)", "O(n log n)", "O(n log n)");
        QuestionModel question12 = new QuestionModel(" The complexity of selection sort algorithm is", "O(log n)", "O(n)", "O(n log n)", "O(n2)", "O(n2)");
        QuestionModel question13 = new QuestionModel("What is the worst case running time complexity of merge sort?", "O(n2)", "O(n)",
                "O(log n)", "O(n log n)", "O(n log n)");
        QuestionModel question14 = new QuestionModel("What is the best case running time complexity of quicksort?", "O(n2)", "O(n)", "O(log n)", "O(n log n)", "O(n log n)");
        QuestionModel question15 = new QuestionModel("What is the average case running time complexity of quicksort?", "O(n2)", "O(n)", "O(log n)", "O(n log n)", "O(n log n)");
        QuestionModel question16 = new QuestionModel("The best case running time  complexity of insertion sort algorithm is", "O(n log n)", "O(n2)",
                "O(log n)", "O(n)", "O(n)");
        QuestionModel question17 = new QuestionModel("A sort which compares adjacent elements in a list and switches where necessary is", "selection sort ",
                "quick sort", "insertion sort", "bubble sort", "bubble sort");
        QuestionModel question18 = new QuestionModel("As part of the maintenance work, you are entrusted with the work of rearranging the library books in a shelf in proper order, at the end of each day. The ideal choice will be",
                "bubble sort", "Merge sort", "Selection sort", "Insertion sort", "Insertion sort");
        QuestionModel question19 = new QuestionModel("Which among the following is the best when the list is already sorted", "Selection sort",
                " Merge sort", "Bubble sort", "Insertion sort", "Insertion sort");
        QuestionModel question20 = new QuestionModel("You have a sorted array and now you are given an element to be placed in that array so that the resulting array is also sorted, the best sorting technique in this case is",
                " bubble sort", "merge sort", "quick sort", "Insertion sort", "Insertion sort");
        QuestionModel question21 = new QuestionModel("The running time of quick sort largely depends on",
                "arrangement of elements", "size of element ", "number of inputs", "selection of pivot element ", "selection of pivot element ");
        dSQuiz.add(question1);
        dSQuiz.add(question2);
        dSQuiz.add(question3);
        dSQuiz.add(question4);
//        dSQuiz.add(question5);
//        dSQuiz.add(question6);
//        dSQuiz.add(question7);
//        dSQuiz.add(question8);
//        dSQuiz.add(question9);
//        dSQuiz.add(question10);
//        dSQuiz.add(question11);
//        dSQuiz.add(question12);
//        dSQuiz.add(question13);
//        dSQuiz.add(question14);
//        dSQuiz.add(question15);
//        dSQuiz.add(question16);
//        dSQuiz.add(question17);
//        dSQuiz.add(question18);
//        dSQuiz.add(question19);
//        dSQuiz.add(question20);
//        dSQuiz.add(question21);
    }

//    public String checkDigit(int number) {
//        return number <= 9 ? "0" + number : String.valueOf(number);
//    }


}
