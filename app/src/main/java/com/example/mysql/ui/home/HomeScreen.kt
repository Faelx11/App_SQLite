package com.example.mysql.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysql.R
import com.example.mysql.data.Item
import com.example.mysql.ui.AppViewModelProvider
import com.example.mysql.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(20.dp))

            Row (
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ){
                Text(
                    text = "Mercadinho",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            HomeBody(
                itemList = homeUiState.ItemList,
                onItemClick = navigateToItemDetails,
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            )
        }

        FloatingActionButton(
            onClick = navigateToItemEntry,
            containerColor = Color.Black,
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .padding(20.dp)
                .size (60.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                modifier = Modifier
                    .size(40.dp)
            )

        }
    }
}

@Composable
private fun HomeBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier
){

    if(itemList.isEmpty()){

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.Center
        ){
            Text(
                text = "Nenhum item encontrado",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

    else{
        ItemList(
            itemList = itemList,
            onItemClick = { onItemClick(it.id) },
            modifier = modifier
        )
    }
}

@Composable
private fun ItemList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        items(items = itemList, key = {it.id}){
            item ->
            Item(
                item = item,
                onItemClick = { onItemClick(item) },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun Item(
    item: Item,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
)  {
    Card (
        modifier = Modifier
            .padding(20.dp)
            .clickable {
                onItemClick(item)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column (
            modifier = Modifier
                .background(Color.Gray)
        ) {

            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Text(
                    text = item.nome,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(2.dp).weight(1f)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = item.preco,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(2.dp).weight(1f)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    HomeBody(
        itemList = listOf(
            Item(1, "Banana", "2,00", 2),
            Item(2, "Maça", "3,00", 3)
        ),
        onItemClick = {},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview(){
    HomeBody(listOf(), onItemClick = {}, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    Item(
        item = Item(1, "Banana", "2,00", 2),
        onItemClick = {}
    )
}