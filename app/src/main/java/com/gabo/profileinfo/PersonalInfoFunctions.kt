package com.gabo.profileinfo

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.gabo.profileinfo.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PersonalInfoFunctions(
    private val context: Context,
    private val binding: ActivityMainBinding
) {
    fun emptyError(tied: TextInputEditText, til: TextInputLayout): Boolean {
        val startIcon: Int
        val startIconAndBoxStrokeColor: Int
        val errorMsg: String?
        var helperTextEnabled = true
        val readyToSave: Boolean
        with(til){
             if (tied.text.toString().isEmpty()) {
                errorMsg = "Required* Please Fill This Field"
                startIcon = if(this == binding.tilPassword){ (R.drawable.ic_lock_open) }else {
                    R.drawable.ic_person
                }
                startIconAndBoxStrokeColor = (R.color.red_required)
                readyToSave = false
            } else {
                errorMsg = null
                 startIcon = if (this == binding.tilPassword) { (R.drawable.ic_lock) } else {
                     (R.drawable.ic_baseline_person_24)
                 }
                startIconAndBoxStrokeColor = (R.color.green)
                helperTextEnabled = false
                readyToSave = true
            }
        }
        with(til){
            error = errorMsg
            setStartIconDrawable(startIcon)
            setStartIconTintList(AppCompatResources.getColorStateList(context,startIconAndBoxStrokeColor))
            boxStrokeColor = ContextCompat.getColor(context,startIconAndBoxStrokeColor)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }

    fun emailCheck(tied: TextInputEditText, til: TextInputLayout): Boolean {
        val startIcon: Int
        val startIconAndBoxStrokeColor: Int
        val errorMsg: String?
        var helperTextEnabled = true
        val readyToSave: Boolean
        when {
            tied.text.toString().isEmpty() -> {
                startIcon = (R.drawable.ic_mail)
                startIconAndBoxStrokeColor = R.color.red_required
                errorMsg = "Required* Please Fill This Field"
               readyToSave = false
            }
            android.util.Patterns.EMAIL_ADDRESS.matcher(tied.text.toString()).matches() -> {
                errorMsg = null
                startIcon = (R.drawable.ic_baseline_mail_24)
                startIconAndBoxStrokeColor = R.color.green
                helperTextEnabled = false
                readyToSave = true
            }
            else -> {
                errorMsg = "Required* Does Not matches Email Pattern!"
                startIcon = (R.drawable.ic_mail)
                startIconAndBoxStrokeColor = R.color.red_required
               readyToSave = false
            }
        }
        with(til){
            error = errorMsg
            setStartIconDrawable(startIcon)
            setStartIconTintList(AppCompatResources.getColorStateList(context,startIconAndBoxStrokeColor))
            boxStrokeColor = ContextCompat.getColor(context,startIconAndBoxStrokeColor)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }

    fun userNameCheck(tied: TextInputEditText, til: TextInputLayout): Boolean {
        val startIcon: Int
        val startIconAndBoxStrokeColor: Int
        val errorMsg: String?
        val helperTextEnabled: Boolean
        val readyToSave: Boolean
        when {
            tied.text.toString().isEmpty() -> {
                errorMsg = "Required* Please Fill This Field"
                startIcon = (R.drawable.ic_person)
                startIconAndBoxStrokeColor = (R.color.red_required)
                helperTextEnabled = true
                readyToSave = false
            }
            tied.text!!.length < 10 -> {
                errorMsg = "Required* can't be less than 10 characters"
                startIcon = (R.drawable.ic_person)
                startIconAndBoxStrokeColor =(R.color.red_required)
                helperTextEnabled = true
                readyToSave = false
            }
            else -> {
                errorMsg = null
                startIcon = (R.drawable.ic_baseline_person_24)
                startIconAndBoxStrokeColor = (R.color.green)
                helperTextEnabled =  false
                readyToSave = true
            }
        }
        with(til){
            error = errorMsg
            setStartIconDrawable(startIcon)
            setStartIconTintList(AppCompatResources.getColorStateList(context,startIconAndBoxStrokeColor))
            boxStrokeColor = ContextCompat.getColor(context,startIconAndBoxStrokeColor)
            isHelperTextEnabled = helperTextEnabled
        }
        return readyToSave
    }

    fun ageCheck(tied: TextInputEditText, til: TextInputLayout): Boolean {
        val startIcon: Int
        val startIconAndBoxStrokeColor: Int
        val errorMsg: String?
        var helperTextEnabled = true
        if (tied.text.toString().isEmpty()) {
            errorMsg = "Required* Please Fill This Field"
            startIcon = (R.drawable.ic_baseline_calendar_today_24)
            startIconAndBoxStrokeColor = (R.color.red_required)
        } else if (dateFormatter(tied.text.toString()) == "") {
            startIcon = (R.drawable.ic_baseline_calendar_today_24)
            startIconAndBoxStrokeColor = (R.color.red_required)
            errorMsg = "Required* Please enter date or Click on calendar and choose"
        } else {
            errorMsg = null
            startIcon = (R.drawable.ic_baseline_calendar_month_24)
            startIconAndBoxStrokeColor = (R.color.green)
            helperTextEnabled = false
        }
        with(til){
            error = errorMsg
            setStartIconDrawable(startIcon)
            setStartIconTintList(AppCompatResources.getColorStateList(context,startIconAndBoxStrokeColor))
            boxStrokeColor = ContextCompat.getColor(context,startIconAndBoxStrokeColor)
            isHelperTextEnabled = helperTextEnabled
        }
        return errorMsg == null
    }

    fun createDatePickerDialog(tied: TextInputEditText): DatePickerDialog {
        tied.setText(SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()))
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
                tied.setText(sdf.format(cal.time))
            }
        return DatePickerDialog(
            context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun clearField(tied: TextInputEditText) {
        tied.setText("")
    }

    fun clearAll(list: List<TextInputEditText>) {
        list.map {
            for (element in list) {
                clearField(element)
            }
        }
    }

    private fun dateFormatter(input: String): String {
        var date = ""
        try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val dateCheck = LocalDate.parse(input, formatter)
            date = dateCheck.toString()
        } catch (e: Exception) {
        }
        return date
    }
}
