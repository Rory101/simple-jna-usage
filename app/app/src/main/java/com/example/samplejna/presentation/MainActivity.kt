/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.samplejna.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.samplejna.R
import com.example.samplejna.presentation.theme.SampleJNATheme
import com.sun.jna.Memory
import com.sun.jna.Pointer

private var callbackImpl: CallbackImpl = CallbackImpl()

class MainActivity : ComponentActivity() {
    private val lib_foo: LibInterface = LibInterface.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android", lib_foo)
        }
    }
}

@Composable
fun WearApp(greetingName: String, lib: LibInterface) {
    SampleJNATheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            Greeting(greetingName = greetingName, lib)
//            calculate(a = 5, b = 232, lib = lib)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            calculate(a = 5, b = 232, lib = lib)
        }
    }
}

@Composable
fun calculate(a: Int, b: Int, lib: LibInterface): Int {
    var count by remember { mutableStateOf(0) }
    var disp by remember { mutableStateOf(0) }
    val ans: Pointer = Memory(4)
    val ctx: Pointer = Memory(1)
    fun onClick()
    {
        lib.registerCallback(ctx, callbackImpl)
        lib.add(a + count, b, ans)
        count = ans.getInt(0)
        disp = callbackImpl.getAnswer()
    }
    Button(onClick = { onClick() }) {
        Text(text = "Calc ${a + count} + ${b}")
    }
    Text(text = "Answer = ${disp}")
    return ans.getInt(0)
}

@Composable
fun Greeting(greetingName: String, lib: LibInterface) {
    Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.hello_world, greetingName)
    )
//    var ans = calculate(a = 5, b = 230, lib = lib)
//    Text(text = "Answer = ${ans}")
}



@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    val lib_foo: LibInterface = LibInterface.INSTANCE
    WearApp("Preview Android", lib_foo)
}
    