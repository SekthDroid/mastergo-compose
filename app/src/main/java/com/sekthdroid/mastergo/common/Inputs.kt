package com.sekthdroid.mastergo.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.theme.LightGray

@Preview
@Composable
fun DefaultInputPreview() {
    DefaultInput(value = "", onValueChanged = {}, "Label")
}

@Composable
fun SearchInput(value: String, onValueChanged: (String) -> Unit, label: String = "") {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(LightGray)
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Image(
            imageVector = Icons.Filled.Search,
            contentDescription = "",
            modifier = Modifier.height(16.dp),
            colorFilter = ColorFilter.tint(color = Color(0xFF525464))
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        ) {
            if (value.isEmpty()) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFB0B0C3)
                )
            }
            it()
        }
    }
}

class CreditCardVisualTransformation(val mask: Char = '\u2022') : VisualTransformation {
    private val TAG = "VisualTransformation2"
    private val offsetMapping = CreditCardOffsetMapping()

    override fun filter(text: AnnotatedString): TransformedText {
        val result = if (text.length <= 4) {
            mask.toString().repeat(text.length)
        } else {
            serialize(text.toString())
        }
        return TransformedText(
            AnnotatedString(result),
            offsetMapping
        )
    }

    private fun serialize(text: String): String {
        val chunked = text.chunked(4)
        return with(chunked) {
            mapIndexed { index, s ->
                if (index == chunked.lastIndex) {
                    s
                } else {
                    mask.toString().repeat(s.length)
                }
            }.joinToString(" ")
        }
    }
}

class CreditCardOffsetMapping : OffsetMapping {
    private val TAG = "OffsetMapping2"

    override fun originalToTransformed(offset: Int): Int {
        // 1234567891234567 => 1234 5678 9123 4567
        if (offset <= 4) return offset
        if (offset <= 8) return offset + 1
        if (offset <= 12) return offset + 2
        if (offset <= 16) return offset + 3
        return 19
    }

    override fun transformedToOriginal(offset: Int): Int {
        // 1234 5678 9123 4567 => 1234567891234567
        if (offset <= 4) return offset
        if (offset <= 9) return offset - 1
        if (offset <= 14) return offset - 2
        if (offset <= 19) return offset - 3
        return 16
    }
}


@Composable
fun DefaultInput(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String = "",
    backgroundColor: Color = LightGray,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color = Color(0xFFB0B0C3)
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        decorationBox = {
            if (value.isEmpty()) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
            it()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor)
            .padding(horizontal = 16.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        textStyle = TextStyle.Default.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = textColor
        ),
        visualTransformation = visualTransformation
    )
}

@Composable
fun PasswordInput(
    value: String,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
    textColor: Color = Color(0xFFB0B0C3),
) {
    var isPasswordHidden by remember { mutableStateOf(true) }
    val passwordTransformation = remember(isPasswordHidden) {
        if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }
    BasicTextField(
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password, autoCorrect = false
        ),
        onValueChange = onValueChanged,
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolder,
                        fontWeight = FontWeight.Medium,
                        color = textColor,
                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                    ) {
                        it()
                    }
                }
                IconButton(
                    onClick = {
                        isPasswordHidden = !isPasswordHidden
                    }, modifier = Modifier.size(36.dp)
                ) {
                    Image(
                        painter = if (isPasswordHidden) {
                            painterResource(id = R.drawable.ic_baseline_visibility_off)
                        } else {
                            painterResource(id = R.drawable.ic_baseline_visibility)
                        },
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = textColor)
                    )
                }
            }
        },
        visualTransformation = passwordTransformation,
        textStyle = TextStyle.Default.copy(color = textColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(LightGray)
            .padding(horizontal = 16.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
    )

}