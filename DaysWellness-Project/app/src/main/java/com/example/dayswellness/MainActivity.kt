package com.example.dayswellness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dayswellness.ui.theme.DaysWellnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaysWellnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProgrammingChallengeApp()
                }
            }
        }
    }
}

data class DayChallenge(
    val day: Int,
    val title: String,
    val description: String,
    val detailedInfo: String,
    val imageRes: Int // Usaremos R.drawable.day_1, day_2, etc.
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammingChallengeApp() {
    val challenges = getDailyChallenges()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "7 Días de Programación",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(challenges) { challenge ->
                ChallengeCard(challenge = challenge)
            }
        }
    }
}

@Composable
fun ChallengeCard(challenge: DayChallenge) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Día del mes
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = "Día ${challenge.day}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Título del desafío
            Text(
                text = challenge.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Imagen (placeholder con un color)
            // En la app real, usarías: painterResource(id = challenge.imageRes)
            Image(
                painter = painterResource(id = challenge.imageRes),
                contentDescription = "Imagen del día ${challenge.day}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción breve
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Botón expandible
            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (expanded) "Ver menos" else "Ver más detalles",
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Contraer" else "Expandir"
                )
            }

            // Contenido expandible con animación
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = challenge.detailedInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

fun getDailyChallenges(): List<DayChallenge> {
    return listOf(
        DayChallenge(1, "Planifica tu semana", "Organiza metas claras.", "Define tareas prioritarias para avanzar efectivamente.", R.drawable.day_1),
        DayChallenge(2, "Ejercicio físico", "Mueve el cuerpo diario.", "Mejora energía, salud y estado mental positivo.", R.drawable.day_2),
        DayChallenge(3, "BAprende algo nuevo", "Expande tus conocimientos.", "Dedica tiempo a lectura o cursos formativos.", R.drawable.day_3),
        DayChallenge(4, "Tiempo para meditar", "Calma tu mente.", "Reduce estrés y aumenta concentración y bienestar.", R.drawable.day_4),
        DayChallenge(5, "Conecta con otros", "Fortalece relaciones sociales.", "Habla con familia o amigos, comparte experiencias.", R.drawable.day_5),
        DayChallenge(6, "Descanso activo", "Relájate y muévete.", "Alterna entre descanso y actividades recreativas ligeras.", R.drawable.day_6),
        DayChallenge(7, "Autoevaluación semanal", "Revisa tus logros.", "Reflexiona y ajusta planes para mejor productividad.", R.drawable.day_7),
    )
}

@Preview(showBackground = true)
@Composable
fun ChallengeCardPreview() {
    DaysWellnessTheme {
        ChallengeCard(
            challenge = DayChallenge(
                1,
                "Variables y Tipos de Datos",
                "Aprende los fundamentos de programación",
                "Domina los tipos primitivos y referencias. Practica declarando variables de diferentes tipos.",
                0
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DaysWellnessTheme {
        ProgrammingChallengeApp()
    }
}