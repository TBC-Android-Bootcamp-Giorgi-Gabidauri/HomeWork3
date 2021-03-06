package com.gabo.profileinfo

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.gabo.profileinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val personalInfoFunctions = PersonalInfoFunctions(this@MainActivity, binding)
        with(binding) {
            val list = listOf(tiedUserName, tiedFirstName, tiedLastName, tiedEmail, tiedPassword, tiedAge)
            tiedUserName.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.userNameCheck(tiedUserName, tilUserName)
            }
            tiedEmail.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.emailCheck(tiedEmail, tilEmail)
            }
            tiedFirstName.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.emptyError(tiedFirstName, tilFirstName)
            }
            tiedLastName.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.emptyError(tiedLastName, tilLastName)
            }
            tiedPassword.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.emptyError(tiedPassword, tilPassword)
            }
            tiedAge.doOnTextChanged { text, start, before, count ->
                personalInfoFunctions.ageCheck(tiedAge, tilAge)
            }
            tilAge.setStartIconOnClickListener {
                val datePickerDialog = personalInfoFunctions.createDatePickerDialog(tiedAge)
                datePickerDialog.show()
            }
            btnSave.setOnClickListener {
                personalInfoFunctions.save(list)
            }
            btnClear.setOnLongClickListener {
                personalInfoFunctions.clearAll(list)
                true
            }
        }
    }
}
