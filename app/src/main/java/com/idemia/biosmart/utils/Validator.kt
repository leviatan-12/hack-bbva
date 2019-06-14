package com.idemia.biosmart.utils

import android.support.design.widget.TextInputLayout
import android.widget.EditText
import java.util.regex.Pattern

class Validator {
    companion object {
        private const val NAME_VALIDATION_MSG = "This field only can contains letters"
        private const val USERNAME_VALIDATION_MSG = "This field only can contains letters, numbers, dash (-) and underscores (_)"


        /**
         * Checks if the name is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the username is valid.
         */
        fun validateName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.isNotBlank() && str.isNotEmpty() && Pattern.compile("[a-zA-Z\\s]+").matcher(str).matches()

            // Set error if required
            if(updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the username is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the username is valid.
         */
        fun validateUsername(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.isNotBlank() && str.isNotEmpty() && Pattern.compile("[a-zA-Z-_\\d]+").matcher(str).matches()

            // Set error if required
            if(updateUI) {
                val error: String? = if (valid) null else USERNAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Retrieve string data from the parameter.
         * @param data - can be EditText or String
         * @return - String extracted from EditText or data if its data type is Strin.
         */
        private fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString()
            } else if (data is String) {
                str = data
            }
            return str
        }

        /**
         * Sets error on EditText or TextInputLayout of the EditText.
         * @param data - Should be EditText
         * @param error - Message to be shown as error, can be null if no error is to be set
         */
        private fun setError(data: Any, error: String?) {
            if (data is EditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).setError(error)
                } else {
                    data.setError(error)
                }
            }
        }
    }
}