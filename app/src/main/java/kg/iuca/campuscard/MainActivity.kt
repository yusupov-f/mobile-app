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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kg.iuca.campuscard.ui.theme.CampusCardLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusCardLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CampusCardScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Exercise 5: extracted composable
@Composable
fun ProfileHeader(
    name: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Column {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(text = status, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Exercise 5: extracted composable
// Exercise 3: verticalAlignment = Alignment.Top (попробуйте CenterVertically)
// Exercise 4: title = Modifier.weight(1f)
@Composable
fun ScheduleRow(
    time: String,
    title: String,
    room: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top // Exercise 3: попробуйте CenterVertically
    ) {
        Text(
            text = time,
            // Exercise 3: время крупнее названия курса
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.width(64.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f) // Exercise 4
        )
        Text(
            text = room,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CampusCardScreen(modifier: Modifier = Modifier) {
    // Exercise 2: переключатель для нижней панели
    var useSpaceBetween by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,   // Exercise 6: попробуйте SpaceBetween
        horizontalAlignment = Alignment.Start
    ) {
        // Заголовок профиля (вложенный Row + Column)
        ProfileHeader(
            name = "Farhad",
            status = "In class · Building B"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Exercise 1: этот блок можно центрировать через
        // horizontalAlignment = Alignment.CenterHorizontally у Column
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "Campus Card",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = "Farhad")
            Text(text = "Student ID: 26.10.26")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Today", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

// Список пар — три строки
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ScheduleRow(
                time = "9:30",
                title = "Machine Learning",
                room = "Cyber Lab"
            )
            ScheduleRow(
                time = "10:50",
                title = "Mobile App Development",
                room = "B305"
            )
            // Exercise 4: длинный заголовок — проверьте, что аудитория не уезжает
            ScheduleRow(
                time = "13:10",
                title = "Algorithms and Data Structures for IT 124",
                room = "B001"
            )
        }

        // Прижимаем нижнюю панель к низу экрана
        Spacer(modifier = Modifier.weight(1f))

        // Exercise 2: SpaceBetween vs Center
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (useSpaceBetween) {
                Arrangement.SpaceBetween
            } else {
                Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            }
        ) {
            Button(onClick = { useSpaceBetween = !useSpaceBetween }) {
                Text("Map")
            }
            OutlinedButton(onClick = { useSpaceBetween = !useSpaceBetween }) {
                Text("Show QR")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CampusCardPreview() {
    CampusCardLabTheme {
        CampusCardScreen()
    }
}