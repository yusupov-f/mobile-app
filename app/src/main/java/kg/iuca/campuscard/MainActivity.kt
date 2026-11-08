package kg.iuca.campuscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.iuca.campuscard.ui.theme.CampusCardLabTheme
import kotlin.random.Random // 1. Импорт для случайного выбора цвета

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusCardLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WordleGridScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WordleGridScreen(modifier: Modifier = Modifier) {
    val Name = listOf("Yusupov", "Farhad", "IT-124")
    val text = "Hello, World!".filter { it.isLetter() }
    val firstRow = text.take(5) // Hello
    val secondRow = text.takeLast(5) // World
    val thirdRow = listOf(firstRow, secondRow)
    val LastRow = listOf("Something", "like", "that", "LexusRX300")

    // Объединяем все слова в один общий список
    val allWords = Name + thirdRow + LastRow

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // Скролл для большого количества слов
            .background(Color.Magenta)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 2. Используем forEachIndexed, чтобы знать индекс текущей строки (rowIndex)
        allWords.forEachIndexed { rowIndex, word ->

            // Проверяем: четная ли это строка по порядку на экране (индексы 1, 3, 5...)
            val isEvenRow = rowIndex % 2 != 0

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                word.forEach { letter ->

                    // 3. Логика выбора цвета для каждой плашки
                    val boxColor = remember(rowIndex, letter) {
                        if (isEvenRow) {
                            // В четных строках случайным образом выпадает Желтый или Серый
                            if (Random.nextBoolean()) Color.Yellow else Color.Gray
                        } else {
                            // В нечетных строках — стандартный Зеленый
                            Color.Green
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(color = boxColor) // Применяем динамический цвет
                            .weight(1f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = letter.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordleGridPreview() {
    CampusCardLabTheme {
        WordleGridScreen()
    }
}