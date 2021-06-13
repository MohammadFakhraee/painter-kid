package ir.mohammadhf.painterkid.feature.parent.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

import io.reactivex.subjects.BehaviorSubject;
import ir.mohammadhf.painterkid.R;

public class ParentControlFragment extends Fragment {
    private final int BOUND = 20;
    private final int MIN_BOUND = 6;

    private BehaviorSubject<Boolean> answerCallBackBehaveSub = BehaviorSubject.create();
    private BehaviorSubject<Boolean> exitBehaveSun = BehaviorSubject.create();

    private TextView titleTv;
    private TextView questionTv;
    private Button ans1Btn;
    private Button ans2Btn;
    private Button ans3Btn;
    private ImageButton exitIb;

    private int answer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_parent_control, container, false
        );

        titleTv = view.findViewById(R.id.tv_control_parental);
        questionTv = view.findViewById(R.id.tv_control_question);
        ans1Btn = view.findViewById(R.id.btn_control_answer1);
        ans2Btn = view.findViewById(R.id.btn_control_answer2);
        ans3Btn = view.findViewById(R.id.btn_control_answer3);
        exitIb = view.findViewById(R.id.ib_control_exit);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Random random = new Random();
        answer = random.nextInt(BOUND) + MIN_BOUND;
        int num1 = random.nextInt(answer);

        questionTv.setText(num1 + " + " + (answer - num1) + " =");

        int[] fakeAnswers = {answer, random.nextInt(BOUND) + MIN_BOUND, random.nextInt(BOUND) + MIN_BOUND};
        for (int i = 0; i < fakeAnswers.length; i++) {
            int randPos = random.nextInt(fakeAnswers.length);
            if (randPos != i) {
                fakeAnswers[i] += fakeAnswers[randPos];
                fakeAnswers[randPos] = fakeAnswers[i] - fakeAnswers[randPos];
                fakeAnswers[i] -= fakeAnswers[randPos];
            }
        }

        ans1Btn.setText(String.valueOf(fakeAnswers[0]));
        ans1Btn.setOnClickListener(view1 -> {
            checkSelectedAnswer(Integer.parseInt(ans1Btn.getText().toString()));
        });

        ans2Btn.setText(String.valueOf(fakeAnswers[1]));
        ans2Btn.setOnClickListener(view2 -> {
            checkSelectedAnswer(Integer.parseInt(ans2Btn.getText().toString()));
        });

        ans3Btn.setText(String.valueOf(fakeAnswers[2]));
        ans3Btn.setOnClickListener(view3 -> {
            checkSelectedAnswer(Integer.parseInt(ans3Btn.getText().toString()));
        });

        exitIb.setOnClickListener(view1 -> exitBehaveSun.onNext(true));
    }

    private void checkSelectedAnswer(int selectedAns) {
        if (selectedAns == answer) {
            answerCallBackBehaveSub.onNext(true);
        } else {
            answerCallBackBehaveSub.onNext(false);
        }
    }

    public BehaviorSubject<Boolean> getAnswerCallBackBehaveSub() {
        return answerCallBackBehaveSub;
    }

    public BehaviorSubject<Boolean> getExitBehaveSun() {
        return exitBehaveSun;
    }
}
