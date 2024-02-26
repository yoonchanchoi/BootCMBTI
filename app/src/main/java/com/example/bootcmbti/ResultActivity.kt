package com.example.bootcmbti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bootcmbti.databinding.ActivityMainBinding
import com.example.bootcmbti.databinding.ActivityResultBinding
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val results = intent.getIntegerArrayListExtra("results") ?: arrayListOf()
        // 선택된 값에 따른 결과 알파벳 할당
        val resultTypes = listOf(
            listOf("E", "I"),
            listOf("N", "S"),
            listOf("T", "F"),
            listOf("J", "P")
        )

        var resultString = ""
        for (i in results.indices) {
            // 선택된 값(1 또는 2)에 따른 알파벳을 결과 문자열에 추가
            resultString += resultTypes[i][results[i] - 1]
        }

        // 결과 TextView 업데이트
        binding.tvResValue.text = resultString

        // 알파벳 결과에 따른 이미지 설정

        val imageResource = resources.getIdentifier("ic_${resultString.lowercase(Locale.ROOT)}", "drawable", packageName)
        binding.ivResImg.setImageResource(imageResource)
        binding.btnResRetry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}