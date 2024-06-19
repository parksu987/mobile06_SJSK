package com.example.myapplication.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.firestore.Body
import com.example.myapplication.firestore.Item
import com.example.myapplication.firestore.Items
import com.example.myapplication.roomDB.Eating

@Composable
fun FoodList(dataBody: Body?, list: List<Items>, onClick:(Item)->Unit) {
//    LazyColumn {
//        items(list) {item ->
//            FoodUI(item, onClick)
//        }
//    }

    if (list.isNotEmpty() == true) {  // 데이터가 있을 때
        LazyColumn {
            item{
                HeaderBar(purposeText = "추가")
            }

            list.forEach { items ->
                items.item?.forEachIndexed { index, item ->
                    item {
                        FoodUI(dataBody = dataBody, item = item, index = index) {
                            onClick(item)
                        }
                    }
                }
            }
        }
    } else {
        Text("No items available")
    }
}


@Composable
fun FoodUI(dataBody: Body?, item: Item, index:Int, onClick:(item: Item)->Unit) {
//    Row(
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//            .clickable {onClick(item)}
//            .background(color= Color.LightGray)
//            .border(BorderStroke(3.dp, Color.Red))
//        ,
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(item.FOOD_NM_KR!!, fontSize=25.sp)
//        Text(item.AMT_NUM1.toString(), fontSize=25.sp)
//    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${dataBody?.pageNo?.toInt()?.minus(1)?.times(10)?.plus(index + 1)}",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "${item.FOOD_NM_KR?.split("_")?.get(1)}",
            modifier = Modifier
                .weight(4f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "${item.AMT_NUM1}",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = { onClick(item) },
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "",
            )
        }
    }
}


@Composable
fun EatingList(list: List<Eating>) {
    LazyColumn {
        items(list) {eating ->
            EatingUI(eating)
        }
    }
}


@Composable
fun EatingUI(eating: Eating) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .border(BorderStroke(3.dp, Color.Red))
        ,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(eating.foodName, fontSize=25.sp)
        Text(eating.kcal.toString(), fontSize=25.sp)
    }
}