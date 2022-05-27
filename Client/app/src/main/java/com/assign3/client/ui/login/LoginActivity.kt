package com.assign3.client.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.assign3.client.databinding.ActivityLoginBinding

import com.assign3.client.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityLoginBinding.inflate(layoutInflater)
     setContentView(binding.root)

        val signup_username = binding.signupUsername
        val signup_password = binding.signupPassword
        val signup = binding.signup

        val login_username = binding.loginUsername
        val login_password = binding.loginPassword
        val login = binding.login

        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                login_username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                login_password.error = getString(loginState.passwordError)
            }

            // disable signup button unless both username / password is valid
            signup.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                signup_username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                signup_password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        signup_username.afterTextChanged {
            loginViewModel.loginDataChanged(
                signup_username.text.toString(),
                signup_password.text.toString()
            )
        }

        signup_password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    signup_username.text.toString(),
                    signup_password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.signup(
                            signup_username.text.toString(),
                            signup_password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(login_username.text.toString(), login_password.text.toString())
            }

            signup.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.signup(signup_username.text.toString(), signup_password.text.toString())
            }
        }

        login_username.afterTextChanged {
            loginViewModel.loginDataChanged(
                login_username.text.toString(),
                login_password.text.toString()
            )
        }

        login_password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    login_username.text.toString(),
                    login_password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            login_username.text.toString(),
                            login_password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(login_username.text.toString(), login_password.text.toString())
            }

            signup.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.signup(signup_username.text.toString(), signup_password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}