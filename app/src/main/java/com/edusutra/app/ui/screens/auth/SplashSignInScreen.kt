
package com.edusutra.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.edusutra.app.data.auth.PasswordPolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashSignInScreen(
    isLoading: Boolean,
    errorMessage: String?,
    infoMessage: String?,
    onSignIn: (String, String) -> Unit,
    onCreateAccount: (String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onPhoneOtp: () -> Unit,
    onForgotPassword: (String) -> Unit = {},
    onClearError: () -> Unit = {}
) {

    val brandBlue = Color(0xFF52669A)
    val illustrationGrey = Color(0xFFEFF1F5)

    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var identifierError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var submitting by remember { mutableStateOf(false) }

    fun validateInputs(): Boolean {
        var valid = true
        if (identifier.isBlank()) {
            identifierError = "Email / student ID is required"
            valid = false
        } else if (!isValidIdentifier(identifier.trim())) {
            identifierError = "Enter a valid email or student ID"
            valid = false
        } else {
            identifierError = null
        }

        if (password.isBlank()) {
            passwordError = "Password is required"
            valid = false
        } else {
            passwordError = PasswordPolicy.validate(password)
            if (passwordError != null) valid = false
        }
        return valid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .width(68.dp)
                .height(68.dp)
                .background(brandBlue.copy(alpha = 0.12f), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = brandBlue
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "EDU SUTRA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = Color(0xFF1A1D26)
        )
        Text(
            text = "Your Personal Learning Path",
            fontSize = 13.sp,
            color = Color(0xFF6B7080)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
                .background(illustrationGrey, RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFE3E6EC), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ILLUSTRATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = Color(0xFF8A8FA3)
                )
                Text(
                    text = "student walking a path map",
                    fontSize = 11.sp,
                    color = Color(0xFF8A8FA3)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "EMAIL / STUDENT ID",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = Color(0xFF3A3E4D)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = identifier,
                onValueChange = { identifier = it; identifierError = null },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                isError = identifierError != null,
                supportingText = {
                    if (identifierError != null) {
                        Text(identifierError!!, color = Color(0xFFB3261E))
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFF8A8FA3))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "PASSWORD",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = Color(0xFF3A3E4D)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                isError = passwordError != null,
                supportingText = {
                    if (passwordError != null) {
                        Text(passwordError!!, color = Color(0xFFB3261E))
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF8A8FA3))
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color(0xFF8A8FA3)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )
        }

        if (infoMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF3FE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = infoMessage,
                    fontSize = 13.sp,
                    color = Color(0xFF52669A),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                )
            }
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEA)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFFB3261E))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(errorMessage, fontSize = 13.sp, color = Color(0xFFB3261E), modifier = Modifier.weight(1f))
                    IconButton(onClick = onClearError) {
                        Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = Color(0xFFB3261E))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateInputs() && !submitting) {
                    submitting = true
                    onSignIn(identifier.trim(), password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading && !submitting,
            colors = ButtonDefaults.buttonColors(containerColor = brandBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading || submitting) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.width(20.dp).height(20.dp))
            } else {
                Text("Sign in", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("First time here?", fontSize = 13.sp, color = Color(0xFF6B7080))
            Text(
                text = "Create account",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = brandBlue,
                modifier = Modifier.clickable {
                    onCreateAccount(identifier.trim(), password)
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Forgot password?",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = brandBlue,
            modifier = Modifier.fillMaxWidth().clickable {
                onForgotPassword(identifier.trim())
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onGoogleSignIn,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("G  Google", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            OutlinedButton(
                onClick = onPhoneOtp,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Phone OTP", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFF6F7FA),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Fair payment promise: no subscription starts without your consent and a reminder 3 days before any renewal.",
                fontSize = 11.sp,
                color = Color(0xFF8A8FA3),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }

        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().height(1.dp),
            shape = RoundedCornerShape(0.dp),
            enabled = false
        )
    }
}

private fun isValidIdentifier(value: String): Boolean {
    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return emailPattern.matches(value) || value.length >= 4
}

@Composable
fun SplashSignInPreview() {
    SplashSignInScreen(
        isLoading = false,
        errorMessage = null,
        infoMessage = null,
        onSignIn = { _, _ -> },
        onCreateAccount = { _, _ -> },
        onGoogleSignIn = {},
        onPhoneOtp = {}
    )
}
