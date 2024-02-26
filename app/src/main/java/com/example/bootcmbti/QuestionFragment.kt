package com.example.bootcmbti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.bootcmbti.databinding.ActivityMainBinding
import com.example.bootcmbti.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding

    private var questionType: Int = 0

    private val questionTitles = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )
    private val questionTexts = listOf(
        listOf(R.string.question1_1, R.string.question1_2, R.string.question1_3),
        listOf(R.string.question2_1, R.string.question2_2, R.string.question2_3),
        listOf(R.string.question3_1, R.string.question3_2, R.string.question3_3),
        listOf(R.string.question4_1, R.string.question4_2, R.string.question4_3),
    )
    private val questionAnswers = listOf(
        listOf(
            listOf(R.string.question1_1_answer1, R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1, R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1, R.string.question1_3_answer2)
        ),
        listOf(
            listOf(R.string.question2_1_answer1, R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1, R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1, R.string.question2_3_answer2)
        ),
        listOf(
            listOf(R.string.question3_1_answer1, R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1, R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1, R.string.question3_3_answer2)
        ),
        listOf(
            listOf(R.string.question4_1_answer1, R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1, R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1, R.string.question4_3_answer2)
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionType = it.getInt(ARG_QUESTION_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)

        binding.tvQuestionTitle.text = getString(questionTitles[questionType])

        val questionTextViews = listOf<TextView>(
            binding.tvQuestion1,
            binding.tvQuestion2,
            binding.tvQuestion3
        )

        val answerRadioGroups = listOf<RadioGroup>(
            binding.rgAnswer1,
            binding.rgAnswer2,
            binding.rgAnswer3,

            )

        for (i in questionTextViews.indices) {
            questionTextViews[i].text = getString(questionTexts[questionType][i])
            val radioButton1 = answerRadioGroups[i].getChildAt(0) as RadioButton
            val radioButton2 = answerRadioGroups[i].getChildAt(1) as RadioButton
            radioButton1.text = getString(questionAnswers[questionType][i][0])
            radioButton2.text = getString(questionAnswers[questionType][i][1])
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answerRadioGroups = listOf(
            binding.rgAnswer1,
            binding.rgAnswer2,
            binding.rgAnswer3
        )

        binding.btnNext.setOnClickListener {

            //모든 질문에 대한 응답이 완료되었는지 확인
            val isAllAnswered = answerRadioGroups.all { it.checkedRadioButtonId != -1 }

            if (isAllAnswered) {
                val responses = answerRadioGroups.map { radioGroup ->
                    val firstRadioButton = radioGroup.getChildAt(0) as RadioButton
                    if (firstRadioButton.isChecked) 1 else 2
                }

                (activity as? TestActivity)?.questionnaireResults?.addResponses(responses)
                (activity as? TestActivity)?.moveToNextQuestion()
            } else {
                Toast.makeText(context, "모든 질문에 답해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        if(questionType==3){
            binding.btnNext.text = "결과 확인"
        }
    }


    companion object {
        private const val ARG_QUESTION_TYPE = "questionType"

        fun newInstance(questionType: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION_TYPE, questionType)
            fragment.arguments = args
            return fragment
        }
    }
}