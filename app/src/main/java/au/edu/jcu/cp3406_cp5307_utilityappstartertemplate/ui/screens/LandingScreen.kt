package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    onNavigateToActivity: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isWide = configuration.screenWidthDp > 600

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { 
        delay(200)
        visible = true 
    }

    val infiniteTransition = rememberInfiniteTransition(label = "rocket")
    val rocketOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rocketOffset"
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colorScheme.background, Color(0xFFE0F7FA))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Row(
                        modifier = Modifier.offset(y = rocketOffset.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "🎓 Primary Learning Hub",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black
                        ) 
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(gradient).padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(if (isWide) 64.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn() + scaleIn()
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (isWide) 160.dp else 120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⭐", style = if (isWide) MaterialTheme.typography.displayLarge else MaterialTheme.typography.displayMedium)
                    }
                }

                Text(
                    text = "Ready to be a Learning Hero?",
                    style = if (isWide) MaterialTheme.typography.displayMedium else MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )

                if (isWide) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            LandingCard(
                                title = "Play & Learn",
                                description = "Explore all subjects! 🎮",
                                icon = Icons.Default.PlayArrow,
                                containerColor = Color(0xFF81C784),
                                contentColor = Color.White,
                                onClick = onNavigateToActivity
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            LandingCard(
                                title = "My Trophies",
                                description = "See wins! 🏆",
                                icon = Icons.Default.Star,
                                containerColor = Color(0xFF4FC3F7),
                                contentColor = Color.White,
                                onClick = onNavigateToStats
                            )
                        }
                    }
                } else {
                    LandingCard(
                        title = "Play & Learn",
                        description = "Explore all subjects! 🎮",
                        icon = Icons.Default.PlayArrow,
                        containerColor = Color(0xFF81C784),
                        contentColor = Color.White,
                        onClick = onNavigateToActivity
                    )

                    LandingCard(
                        title = "My Trophies",
                        description = "See how awesome you are! 🏆",
                        icon = Icons.Default.Star,
                        containerColor = Color(0xFF4FC3F7),
                        contentColor = Color.White,
                        onClick = onNavigateToStats
                    )
                }

                LandingCard(
                    title = "Parents & Help",
                    description = "Settings and info ⚙️",
                    icon = Icons.Default.Settings,
                    containerColor = Color(0xFFFFB74D),
                    contentColor = Color.White,
                    onClick = onNavigateToSettings
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                val quote = remember {
                    listOf(
                        "Mistakes are proof that you are trying! 🌟",
                        "The only way to learn math is to do math. 🧮",
                        "You're getting smarter every second! 🧠",
                        "Numbers are the language of the universe. 🌌"
                    ).random()
                }
                
                Text(
                    text = quote,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "🔒 Safe & Private: Your data stays on this device.",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun LandingCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor, contentColor = contentColor),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = contentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp))
                }
            }
            Column {
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        LandingScreen({}, {}, {})
    }
}
