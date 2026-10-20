package ru.yandex.practicum.contacts.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.yandex.practicum.contacts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CommonBottomSheet(
    title: String,
    items: List<T>,
    selectedItems: Set<T>,
    onItemsSelected: (Set<T>) -> Unit,
    onDismiss: () -> Unit,
    itemContent: @Composable (T, Boolean) -> Unit
) {
    ModalBottomSheet( // создаёт модальное нижнее окно
        onDismissRequest = onDismiss, // передаёт функцию закрытия.
        dragHandle = { BottomSheetDefaults.DragHandle() } // добавляет небольшую полоску в верхней части окна. Она визуально показывает, что окно можно перетаскивать.
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // на всю ширину
                .padding(horizontal = 16.dp) // отступы слева и справа
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // на всю ширину
                    .padding(bottom = 16.dp), // отступ снизу
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text( // отображает текст.
                    text = title, // заголовок
                    style = MaterialTheme.typography.titleLarge // задаёт стиль текста из текущей темы приложения.
                )
                IconButton(onClick = onDismiss) { // создаёт кнопку с иконкой. при нажатии на кнопку вызывается переданная функция закрытия.
                    Icon(
                        imageVector = Icons.Default.Close, // задаёт изображение крестика.
                        contentDescription = stringResource(R.string.close) // предоставляет текстовое описание иконки для сервисов доступности.
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                items(items) { item -> // первый items — функция для добавления элементов в LazyColumn, а второй items — список, переданный параметром в CommonBottomSheet.
                    val isSelected = selectedItems.contains(item)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        onClick = {
                            val newSelection = selectedItems.toMutableSet() // Создаётся изменяемая копия множества выбранных элементов.
                            if (isSelected) {
                                newSelection.remove(item)
                            } else {
                                newSelection.add(item)
                            }
                            onItemsSelected(newSelection)
                        },
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    ) {
                        itemContent(item, isSelected)
                    }
                }
            }
        }
    }
} 