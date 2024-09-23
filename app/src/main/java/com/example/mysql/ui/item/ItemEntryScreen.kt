package com.example.mysql.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysql.R
import com.example.mysql.ui.AppViewModelProvider
import com.example.mysql.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ItemEntryDestination: NavigationDestination {
    override val route = "item_entry"
    override val titleRes = R.string.item_entry_title
}

@Composable
fun ItemEntryScreen(
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ItemEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row (
            Modifier
                .fillMaxWidth()
                .absoluteOffset(x = 10.dp, y = 50.dp),
            Arrangement.Start
        ) {
            if (canNavigateBack) {
                IconButton(
                    onClick = { navigateBack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }
        }

        Row (
            Modifier
                .fillMaxWidth()
                .offset(y = 10.dp),
            Arrangement.Center
        ) {
            Text(
                text = stringResource(ItemEntryDestination.titleRes),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val coroutineScope = rememberCoroutineScope()

        ItemEntryBody (
            itemUiState = viewModel.itemUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            }
        )
    }
}

@Composable
fun ItemEntryBody(
    itemUiState: ItemUiState,
    onItemValueChange: (ItemDetails) -> Unit,
    onSaveClick: () -> Unit,

    modifier: Modifier = Modifier
){

    val focusManager = LocalFocusManager.current

    Column (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 20.dp)
            .background(Color.White)
            .clickable {focusManager.clearFocus()},
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {


        ItemInputForm(
            itemDetails = itemUiState.itemDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth(0.5f),
            enabled = itemUiState.isEntryValid
        ) {
            Text(
                text = "salvar",
                fontSize = 20.sp
            )
        }
    }

}

@Composable
fun ItemInputForm(
    itemDetails: ItemDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ItemDetails) -> Unit = {},
    enabled: Boolean = true
){

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        OutlinedTextField(
            value = itemDetails.nome,
            onValueChange = {onValueChange(itemDetails.copy(nome = it))},
            enabled = enabled,
            label = { Text(text = "Nome") },
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        OutlinedTextField(
            value = itemDetails.preco,
            onValueChange = {onValueChange(itemDetails.copy(preco = it))},
            enabled = enabled,
            label = { Text(text = "Preço") },
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        OutlinedTextField(
            value = itemDetails.quantidade.toString(),
            onValueChange = {
                val newQuantidade = it.toIntOrNull()
                if (newQuantidade != null) {
                    onValueChange(itemDetails.copy(quantidade = newQuantidade))
                }
            },
            enabled = enabled,
            label = { Text(text = "Quantidade") }
        )
    }

}