package com.example.pengeluaranku.view.component

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.pengeluaranku.BuildConfig
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


@Composable
fun LoadingDialog(onDismiss: () -> Unit, loadingWord: String) {
    Dialog(
        onDismissRequest = { }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier,
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "$loadingWord Data...",
                    Modifier
                        .padding(8.dp), textAlign = TextAlign.Center
                )

                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ResultDialog(onDismiss: () -> Unit, resultString: String, actionString: String) {
    Dialog(
        onDismissRequest = { }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier,
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "$actionString data $resultString...",
                    Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp
                )

                Button(
                    onClick = {
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                        .width(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF758BFD),
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Okay", letterSpacing = 1.sp)
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {
    Dialog(
        onDismissRequest = { }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(250.dp),
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Delete Data?",
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp
                )
                Row {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF758BFD),
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Back", letterSpacing = 1.sp)
                    }
                    Button(
                        onClick = onDelete,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF5A75),
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Yes", letterSpacing = 1.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDialog(onDismiss: () -> Unit) {

    //Using Camera
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    //ShowDialog
    var showDialog by remember { mutableStateOf(false) }

    //Photo Picker
    var photoPickerUri: Uri? by remember { mutableStateOf(null) }

    val launcherPhotoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoPickerUri = uri
            showDialog = true
        }
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = "Choose Photo Source",
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp, letterSpacing = 2.sp
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = {
                            launcherPhotoPicker.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF758BFD),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F),
                    ) {
                        Text(text = "Gallery", letterSpacing = 1.sp)
                    }

                    Button(
                        onClick = {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF758BFD),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Camera", letterSpacing = 1.sp)
                    }
                }
                Button(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF5A75),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Back", letterSpacing = 1.sp)
                }
            }
        }
    }

    if (showDialog) {
        ResultDialog(
            onDismiss = { showDialog = false },
            actionString = "Send",
            resultString = "Success"
        )
    }

}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

@Preview(showBackground = true)
@Composable
fun CustomDialogPrev() {
    PengeluarankuTheme {
        Column() {


//            var showCustomDialog by remember {
//                mutableStateOf(true)
//            }
//
//            if (showCustomDialog) {
//                CustomDialog {
//                    showCustomDialog = !showCustomDialog
//                }
//            }

//            ResultDialog(onDismiss = { }, actionString = "Delete", resultString = "Success")
            DeleteDialog(onDismiss = {}, onDelete = {})
//            LoadingDialog(onDismiss = {}, loadingWord = "Uploading")
        }
    }
}