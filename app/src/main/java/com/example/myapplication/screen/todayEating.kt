package com.example.myapplication.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.ListenableWorker
import com.example.myapplication.DB.Food
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import com.example.myapplication.R
import com.example.myapplication.roomDB.Eating
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import java.time.LocalDate

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun todayEating(person: Person, eatingViewModel: EatingViewModel, searchViewModel: SearchViewModel, loginViewModel: LoginViewModel) {
    val spaceModifier = Modifier.height(25.dp)

    val kcal = person.kcal
    val carbohydrate = person.carbohydrate
    val protein = person.protein
    val fat = person.fat

    var searchClicked by remember { mutableStateOf(false)}
    LaunchedEffect(key1 = Unit) {
        searchClicked = false
    }

    var searchStr by remember {
        mutableStateOf("")
    }

    var remainKcal by remember {
        mutableStateOf(kcal)
    }
    var remainNutrient by remember {
        mutableStateOf(Nutrient(carbohydrate, protein, fat))
    }

    val remain = calculate(kcal, Nutrient(carbohydrate, protein, fat), eatingViewModel)

    remainKcal = remain.keys.first()
    remainNutrient = remain.values.first()

    var flag by remember {
        mutableStateOf(false)
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    var newEating: Eating? by remember {
        mutableStateOf(null)
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "오늘 섭취한 칼로리 기록", fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)
                Icon(
                    Icons.Default.SaveAs,
                    contentDescription = "save",
                    modifier = Modifier.clickable {
                        val today = LocalDate.now().toString()
                        var eatingList = eatingViewModel.todayEating.value

                        var nutrientMap = mutableMapOf<String, Nutrient>()

                        for(e in eatingList){
                            if(nutrientMap.containsKey(e.personId)){  //id값이 있는 경우, 기존 영양성분에 더하기
                                val nutrient = nutrientMap[e.personId]!!
                                nutrientMap[e.personId] = Nutrient(
                                    nutrient.carbohydrate + (e.carbohydrate * (e.gram/100)),
                                    nutrient.protein + (e.protein * (e.gram/100)),
                                    nutrient.fat + (e.fat * (e.gram/100))
                                )
                            } else {
                                nutrientMap.put(e.personId, Nutrient(
                                    e.carbohydrate * (e.gram/100),
                                    e.protein * (e.gram/100),
                                    e.fat * (e.gram/100)
                                ))
                            }
                        }

                        for(n in nutrientMap){
                            loginViewModel.updatePersonIntake(n.key, mapOf(today to n.value))
                        }

                        eatingViewModel.deleteAllEating()
                    }
                )
            }
            Spacer(modifier = spaceModifier)

            //searchBar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchStr,
                    onValueChange = { searchStr = it },
                    label = { Text("Search Food") },
                    modifier = Modifier
                        .weight(1f)
                        .height(IntrinsicSize.Min),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        searchClicked = true
                        searchViewModel.searchFood(searchStr)
                    },
                    modifier = Modifier.height(IntrinsicSize.Min)
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }


            Spacer(modifier = spaceModifier)
            if(searchStr != "" && searchClicked) {
                val data by searchViewModel.data.collectAsState()
                val dataBody = data?.body
                val searchList = data?.body?.items ?: emptyList()

                FoodList(dataBody, searchList, onClick = {item ->
                    flag = false
                    Log.d("todayEating", "searchList: ${searchList.toString()}")

                    for(it in eatingViewModel.todayEating.value) {
                        if(it.foodCode == item.FOOD_CD) {
                            val kcal = item.AMT_NUM1?.toDouble() ?: 0.0
                            val carbohydrate = item.AMT_NUM6?.toDouble() ?: 0.0
                            val protein = item.AMT_NUM3?.toDouble() ?: 0.0
                            val fat = item.AMT_NUM4?.toDouble() ?: 0.0

                            newEating = Eating(
                                item.FOOD_CD!!,
                                item.FOOD_NM_KR!!,
                                kcal,  //kcal
                                carbohydrate,
                                protein,
                                fat,
                                it.gram,  //gram
                                person.id
                            )
                            Log.d("todayEating", "update newEating: ${newEating.toString()}")
                            flag = true
                            break
                        }
                    }

                    if(!flag) {
                        val kcal = item.AMT_NUM1?.toDouble() ?: 0.0
                        val carbohydrate = item.AMT_NUM6?.toDouble() ?: 0.0
                        val protein = item.AMT_NUM3?.toDouble() ?: 0.0
                        val fat = item.AMT_NUM4?.toDouble() ?: 0.0

                        newEating = Eating(
                            item.FOOD_CD!!,
                            item.FOOD_NM_KR!!,
                            kcal,  //kcal
                            carbohydrate,
                            protein,
                            fat,
                            0.0,  //gram
                            person.id
                        )
                        Log.d("todayEating", "create newEating: ${newEating.toString()}")
                    }

                    openDialog = true
                })
                Spacer(modifier = Modifier.height(8.dp))
                PageIndicator(searchViewModel)
            }

            else {
                val eatingList by eatingViewModel.todayEating.collectAsState()
                var myEatingList = mutableListOf<Eating>()

                for(eating in eatingList) {
                    if(eating.personId == person.id) {
                        myEatingList.add(eating)
                    }
                }
                EatingList(list = myEatingList, onClick = {eatingViewModel.deleteEating(it)})
                for(eating in eatingList) {
                    Log.d("todayEating", "eating: $eating")
                }

            }

        }

        if(searchStr == "") {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(color = Color.Gray)
            ) {
                remainingKcal(remainKcal, remainNutrient.carbohydrate, remainNutrient.protein, remainNutrient.fat)
            }
        }

//        Column (modifier = Modifier
//            .padding(10.dp)
//            .align(Alignment.BottomStart)
//            .fillMaxWidth()
//            .background(color = Color.LightGray)
//        ) {
////            selectedFood?.let {
////                Text(
////                    text = "선택된 음식: ${it.foodName}",
////                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
////                )
////                Spacer(modifier = spaceModifier)
////                Text(
////                    text = "선택된 음식의 칼로리: ${it.kcal}",
////                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
////                )
////            }
//            remainingKcal(remainKcal, remainNutrient.carbohydrate, remainNutrient.protein, remainNutrient.fat)
//        }
        if(openDialog) {
            GramsInputDialog(
                onDismiss = { openDialog = false },
                onConfirm = { grams ->
                    if (newEating != null) {
                        newEating!!.gram = newEating!!.gram!!.plus(grams.toDouble())

                        Log.d("todayEating", "flag: ${flag}")
                        if(!flag) {
                            eatingViewModel.saveEating(newEating!!)
                            Log.d("todayEating", "saveEating")
                        }
                        else {
                            eatingViewModel.updateEating(newEating!!)

                            Log.d("todayEating", "updateEating")
                        }
                        searchStr = ""
                        openDialog = false

                    }
                    else {
                        Log.d("todayEating", "newEating is null")
                    }
                }

            )
        }
    }
}


@Composable
fun remainingKcal(kcal: Int, carbohydrate: Double, protein: Double, fat: Double) {

    Row (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column {
            Text(text = "남은 권장 칼로리", fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "${kcal}kcal", fontSize = 35.sp)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text(text = "탄수화물", fontSize = 17.sp)
                Text(text ="${carbohydrate}g", fontSize = 17.sp)
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                Text(text = "단백질", fontSize = 17.sp)
                Text(text = "${protein}g", fontSize = 17.sp)
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                Text(text = "지방", fontSize = 17.sp)
                Text(text = "${fat}g", fontSize = 17.sp)
            }
        }
    }
}

fun calculate(kcal: Int, nutrient: Nutrient, eatingViewModel: EatingViewModel): Map<Int, Nutrient> {
    var remainKcal = kcal
    var remainCarbohydrate = nutrient.carbohydrate
    var remainProtein = nutrient.protein
    var remainFat = nutrient.fat

    for(eating in eatingViewModel.todayEating.value) {
        remainKcal -= (eating.kcal * (eating.gram/100)).toInt()
        remainCarbohydrate -= (eating.carbohydrate * (eating.gram/100))
        remainProtein -= (eating.protein * (eating.gram/100))
        remainFat -= (eating.fat * (eating.gram/100))
    }

    return mapOf(
        remainKcal to Nutrient(remainCarbohydrate, remainProtein, remainFat)
    )
}

@Composable
fun GramsInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var gramsInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("몇 그램을 드셨나요?") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = gramsInput,
                    onValueChange = { gramsInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val grams = gramsInput.toIntOrNull() ?: 0
                    onConfirm(grams)
                }
            ) {
                Text("확인")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("취소")
            }
        }
    )
}
