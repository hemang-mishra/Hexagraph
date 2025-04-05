package com.hexagraph.pattagobhi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.jeziellago.compose.markdowntext.MarkdownText

@Preview(showBackground = true)
@Composable
fun See(){
    MarkdownText(
        """
        # Welcome to Compose Markdown
        
        This is **bold**, this is _italic_, and here's a [link](https://openai.com).

        - List Item 1
        - List Item 2

        > A quote from someone wise.

        ```kotlin
        // Code block example:
        fun sayHello() {
            println("Hello, World!")
        }
        ```
    """.trimIndent(),
        modifier = Modifier.fillMaxWidth()
    )
}