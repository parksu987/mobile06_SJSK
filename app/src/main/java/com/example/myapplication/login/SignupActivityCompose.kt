package com.example.myapplication.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.LoginViewModel
@Composable
fun SignupActivityCompose(navController: NavController, viewModel: LoginViewModel) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var id by rememberSaveable { mutableStateOf("") }
    var pw by rememberSaveable { mutableStateOf("") }
    var pwCheck by rememberSaveable { mutableStateOf("") }
    val isIdDuplicated by viewModel.isIdDuplicated.collectAsState()
    val passwordsMatch = pw.isNotEmpty() && pw == pwCheck
    var idChecked by remember { mutableStateOf(false) }

    // 아이디가 변경될 때마다 중복 확인 상태를 리셋합니다.
    LaunchedEffect(id) {
        idChecked = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("계정 만들기", fontSize = 20.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            placeholder = { Text("홍길동") }
        )

        // ID 입력 필드와 중복 확인 버튼
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("아이디") },
                placeholder = { Text("ID") },
                modifier = Modifier.weight(3f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.checkIdDuplication(id)
                    idChecked = true
                },
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Text("중복 확인")
            }
        }

        if (idChecked) {
            if (isIdDuplicated) {
                Text("현재 아이디는 사용 중입니다.", color = Color.Red)
            } else {
                Text("현재 아이디는 사용 가능합니다.", color = Color.Green)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = pw,
            onValueChange = { pw = it },
            label = { Text("비밀번호") },
            placeholder = { Text("비밀번호") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = pwCheck,
            onValueChange = { pwCheck = it },
            label = { Text("비밀번호 확인") },
            placeholder = { Text("비밀번호 확인") }
        )

        // 비밀번호 확인 텍스트 필드에 값이 입력되었을 때만 비밀번호 일치 여부를 표시합니다.
        if (pwCheck.isNotEmpty()) {
            if (passwordsMatch) {
                Text("비밀번호가 일치합니다.", color = Color.Green)
            } else {
                Text("비밀번호가 일치하지 않습니다.", color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // 모든 필드가 채워져 있는지 확인
                if (name.isBlank() || id.isBlank() || pw.isBlank() || pwCheck.isBlank()) {
                    Toast.makeText(context, "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show()
                }
                // 비밀번호가 일치하는지 확인
                else if (pw != pwCheck) {
                    Toast.makeText(context, "입력하신 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                // ID 중복 확인이 완료되었는지 확인
                else if (isIdDuplicated && idChecked) {
                    Toast.makeText(context, "ID 중복 확인이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
                // 모든 조건이 충족되면 회원가입 처리
                else {
                    viewModel.signUp(name, id, pw) // 회원가입 처리 함수 호출
                    Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    navController.navigate("Login") // 로그인 화면으로 네비게이션
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color.LightGray)
        ) {
            Text("회원가입", fontSize = 18.sp)
        }
    }
}
