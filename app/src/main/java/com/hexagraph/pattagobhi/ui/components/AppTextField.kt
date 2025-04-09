package com.hexagraph.pattagobhi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexagraph.pattagobhi.R


@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    outerText: String = "Login with email",
    placeholderText: String = "Enter your email",
    icon: ImageVector? = Icons.Default.Email,
    isError: Boolean = false,
    errorText: String = "Invalid Email Format",
    isPassword: Boolean = false,
    isEnabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    keyboardActions: KeyboardActions = KeyboardActions(),

) {
    // Use fillMaxWidth for responsiveness rather than a fixed width.
    Box(
        modifier = modifier.fillMaxWidth(0.9f)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = outerText,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            LoginTextField(
                modifier = Modifier.fillMaxWidth(),
                placeholderText = placeholderText,
                value = value,
                icon = icon,
                isError = isError,
                errorText = errorText,
                keyboardOptions = keyboardOptions,
                isPassword = isPassword,

                onValueChange = onValueChange,
                isEnabled = isEnabled,
                maxLines = maxLines,
                keyboardActions = keyboardActions
            )
        }
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier,
    placeholderText: String,
    icon: ImageVector? = Icons.Default.Email,
    value: String,
    isError: Boolean,
    isPassword: Boolean,
    keyboardOptions: KeyboardOptions,
    errorText: String,
    onValueChange: (String) -> Unit,
    isEnabled: Boolean,
    maxLines: Int,
    keyboardActions: KeyboardActions
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        maxLines = maxLines,
        value = value,
        enabled = isEnabled,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        supportingText = { if (isError) Text(text = errorText, color = MaterialTheme.colorScheme.error) },
        leadingIcon = {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(if (showPassword) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },

        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    )
}



@Composable
fun ChatInputTextField(
    value: String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter name here",
    onValueChange: (String) -> Unit
) {

    var valueIn by remember {
        mutableStateOf(value)
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp))
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = valueIn, onValueChange = {
                onValueChange(it)
                valueIn = it
            }, enabled = isActive,
            modifier = Modifier,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA0A0A0),
                    ),
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledTextColor = Color.Black
            )
        )
    }
}



@Composable
fun BotTextField(modifier: Modifier,value: String, onValueChange: (String) -> Unit, onDone: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {onValueChange(it)
        },
        placeholder = {
            Text(
                text = "Type a message",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(400),
                    color =  Color(0x80EEF6F8),
                    textDecoration = TextDecoration.None
                )
            )

        },
        keyboardOptions =
            KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone()  }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFEEF6F8),
            unfocusedIndicatorColor =  Color(0x80EEF6F8),
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledTextColor = Color.Black
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight(400),
            color =  Color(0xFFEEF6F8),
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}