package com.example.myapplication.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.DB.Person

@Composable
fun changeMyInfo(person: Person, cancle:()->Unit, onClick:()->Unit) {

    var nameStr by remember {
        mutableStateOf("${person.name}")
    }

    var ageStr by remember {
        mutableStateOf("${person.age}")
    }
    var heightStr by remember {
        mutableStateOf("${person.height}")
    }
    var weightStr by remember {
        mutableStateOf("${person.weight}")
    }

    Column (
        modifier = Modifier
            .padding(vertical = 10.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ){
        Box( modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = cancle)
            )
        }
        Text("내 정보 수정", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        LazyColumn {
            item {
                TextField(value = nameStr, onValueChange = {nameStr = it}, label = { Text("이름") })
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(value = ageStr, onValueChange = {ageStr = it}, label = { Text("나이") })
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(value = heightStr, onValueChange = {heightStr = it}, label = { Text("키") })
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(value = weightStr, onValueChange = {weightStr = it}, label = { Text("몸무게") })
            }
        }

        Button(onClick = {
            person.name = nameStr
            person.age = ageStr.toInt()
            person.height = heightStr.toDouble()
            person.weight = weightStr.toDouble()

            onClick()
        }) {
            Text("수정하기")
        }
    }
}