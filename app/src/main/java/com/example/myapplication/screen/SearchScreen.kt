package com.example.myapplication.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.firestore.ApiResponse
import com.example.myapplication.firestore.Item
import com.example.myapplication.viewmodel.SearchViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
//import org.apache.commons.math3.stat.descriptive.summary.Product
import kotlin.math.round
import kotlin.math.roundToInt
import com.example.myapplication.compare.ProductViewModel
import com.example.myapplication.compare.Product
import kotlinx.coroutines.launch


fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {
    val productViewModel: ProductViewModel = viewModel()  // ProductViewModel 인스턴스 가져오기
    val snackbarHostState = remember { SnackbarHostState() }  // Snackbar를 위한 ScaffoldState

    var searchText by remember { mutableStateOf("") }
    var searchClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        searchClicked = false
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color( 0xFF9DCD5A))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "식전식KU🍴",
                    color = Color(0xFFf37221),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Search Food") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFE3F2FD), // Background color
                            focusedBorderColor = Color(0xFF1E88E5), // Border color when focused
                            unfocusedBorderColor = Color(0xFFBDBDBD) // Border color when not focused
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (isInternetAvailable(context)) {
                                searchClicked = true
                                viewModel.searchFood(searchText)
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "인터넷 연결을 확인해주세요.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }),
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                    )

                    Button(
                        onClick = {
                            if (isInternetAvailable(context)) {
                                searchClicked = true
                                viewModel.searchFood(searchText)
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "인터넷 연결을 확인해주세요.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }

                if (searchClicked) {
                    Spacer(modifier = Modifier.height(30.dp))
                    val data by viewModel.data.collectAsState()
                    if (data != null) {
                        DataDisplay(
                            data = data!!,
                            searchText,
                            navController,
                            viewModel,
                            productViewModel,
                            snackbarHostState
                        )
                        PageIndicator(viewModel)
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No items available")
                    }
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Enter the product name")
                }
            }
        }
    }

}

@Composable
fun DataDisplay(data: ApiResponse, searchText: String, navController: NavHostController, viewModel: SearchViewModel, productViewModel: ProductViewModel, snackbarHostState: SnackbarHostState){

    if (data.body?.items?.isNotEmpty() == true) {  // 데이터가 있을 때
        LazyColumn {
            item {
                Text(
                    "\"$searchText\"에 대한 검색 결과입니다.",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                HeaderBar(purposeText = "비교함")
            }

            data.body?.items?.forEach { items ->
                items.item?.forEachIndexed { index, item ->
                    item {
                        var iconTint by remember { mutableStateOf(Color(0xFF326A29)) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectItem(item)
                                    navController.navigate("detail")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${data.body?.pageNo?.toInt()?.minus(1)?.times(10)?.plus(index + 1)}",
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
                            val coroutineScope = rememberCoroutineScope() // rememberCoroutineScope를 컴포저블 함수 내에 위치시킴
                            IconButton(
                                onClick = {
                                    val product = Product(
                                        name = item.FOOD_NM_KR ?: "", //식품명
                                        energy = item.AMT_NUM1?: "", //에너지(kcal)
                                        carbohydrates = item.AMT_NUM6?: "", //탄수화물(g)
                                        sugars = item.AMT_NUM7?: "", //총당류(g)
                                        fat = item.AMT_NUM4?: "", //지방(g)
                                        protein = item.AMT_NUM3?: "", //단백질(g)
                                        sodium = item.AMT_NUM13?: "", //나트륨(mg)
                                        saturatedFat = item.AMT_NUM23?: "", //총 포화 지방산(g)
                                        cholesterol = item.AMT_NUM22?: "", //콜레스테롤(mg)
                                    )
                                    productViewModel.addProduct(product)
                                    iconTint = Color.Gray

                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "비교함에 잘 담겼습니다.",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .weight(2f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                                    contentDescription = "비교하기",
                                    tint = iconTint
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text("Enter the product name")
    }
}


@Composable
fun PageIndicator(viewModel: SearchViewModel) {
    val totalPages = viewModel.getTotalPages()
    val data by viewModel.data.collectAsState()
    val currentPage = data?.body?.pageNo?.toInt() ?: 1

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (page in 1..totalPages) {
            Text(
                text = page.toString(),
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { viewModel.changePage(page) },
                fontWeight = if (page == currentPage) FontWeight.Bold else FontWeight.Normal,
                textDecoration = if (page == currentPage) TextDecoration.Underline else TextDecoration.None
            )
        }
    }
}


@Composable
fun HeaderBar(purposeText:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "번호",
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp, end = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "식품명",
            color = Color.White,
            modifier = Modifier
                .weight(4f)
                .padding(start = 4.dp, end = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "에너지(kcal)",
            color = Color.White,
            modifier = Modifier
                .weight(2f)
                .padding(start = 4.dp, end = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = purposeText,
            color = Color.White,
            modifier = Modifier
                .weight(2f)
                .padding(start = 4.dp),
            textAlign = TextAlign.Center

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: SearchViewModel, navController: NavHostController) {
    val selectedItem by viewModel.selectedItem.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TopAppBar(
                title = { Text(text = "상세 정보") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )

            Text(
                "\"${selectedItem?.FOOD_NM_KR}\"의 영양성분",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            selectedItem?.let { NutritionTable(item = it) }

            Spacer(modifier = Modifier.height(8.dp))

            Text("부가 정보", modifier = Modifier.fillMaxWidth(), fontSize = 20.sp, textAlign = TextAlign.Center)

            selectedItem?.let { PlusNutritionTable(item = it) }
        }
    }
}

@Composable
fun NutritionTable(item: Item) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(8.dp)
        ) {
            Text(text = "영양성분", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "100g 당 함량", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "1일영양섭취기준(%)", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        NutritionRow("에너지", item.AMT_NUM1, item.AMT_NUM1?.toDouble()?.div(2000)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("탄수화물", item.AMT_NUM6, item.AMT_NUM6?.toDouble()?.div(324)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("총당류", item.AMT_NUM7, item.AMT_NUM7?.toDouble()?.div(100)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("지방", item.AMT_NUM4, item.AMT_NUM4?.toDouble()?.div(54)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("단백질", item.AMT_NUM3, item.AMT_NUM3?.toDouble()?.div(55)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("나트륨", item.AMT_NUM13, item.AMT_NUM13?.toDouble()?.div(2000)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("총 포화 지방산", item.AMT_NUM23, item.AMT_NUM23?.toDouble()?.div(15)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
        NutritionRow("콜레스테롤", item.AMT_NUM22, item.AMT_NUM22?.toDouble()?.div(300)?.times(100)?.let { round(it*100)/100 }.toString() + "%")
    }
}

@Composable
fun NutritionRow(nutrient: String, amount: String?, dailyValue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
//            .border(1.dp, Color.Black)
            .background(Color.White)
    ) {
        Text(text = nutrient, modifier = Modifier
            .weight(1f)
            .padding(8.dp))
        Text(text = amount ?: "0.00g", modifier = Modifier
            .weight(1f)
            .padding(8.dp))
        Text(text = dailyValue, modifier = Modifier
            .weight(1f)
            .padding(8.dp))
    }
}


@Composable
fun PlusNutritionTable(item: Item) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .background(Color.White)
    ) {
        PlusNutritionRow("식품명", item.FOOD_NM_KR)
        PlusNutritionRow("제조사명", item.MAKER_NM)
        PlusNutritionRow("식품 중량", item.Z10500)
        PlusNutritionRow("1회 섭취참고량", item.NUTRI_AMOUNT_SERVING)
        PlusNutritionRow("품목제조보고번호", item.ITEM_REPORT_NO)
        PlusNutritionRow("식품기원", item.FOOD_OR_NM)
        PlusNutritionRow("대표식품명", item.FOOD_REF_NM)
        PlusNutritionRow("식품소분류명", item.FOOD_CAT3_NM)
        PlusNutritionRow("데이터기준일자", item.UPDATE_YMD)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFE0E0E0))
//                .padding(8.dp)
//        ) {
//
//        }


    }
}

@Composable
fun PlusNutritionRow(info: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
//            .border(1.dp, Color.Black)
            .background(Color.White)
    ) {
        Text(text = info, modifier = Modifier
            .weight(1f)
            .padding(8.dp))
        Text(text = value ?: "데이터 미등록", modifier = Modifier
            .weight(1f)
            .padding(8.dp))
    }
}