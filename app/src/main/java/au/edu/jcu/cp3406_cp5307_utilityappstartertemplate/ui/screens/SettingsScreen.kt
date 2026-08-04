package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Parents & Help ⚙️", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ParentSection(
                title = "Curriculum Overview",
                icon = Icons.Default.Info,
                content = "This app is strictly aligned with the MOE Singapore Primary Syllabus (2021-2025). " +
                        "It covers Mathematics, English Language (STELLAR), and Science (Inquiry-based) from P1 to P6."
            )

            ParentSection(
                title = "Privacy & Safety",
                icon = Icons.Default.Lock,
                content = "Your child's privacy is our priority. No data is collected, shared, or sent to any server. " +
                        "All XP, trophies, and progress are stored locally on this device only (GDPR-K & COPPA compliant design)."
            )

            ParentSection(
                title = "How to Support Learning",
                icon = Icons.Default.Person,
                content = "• Encourage your child to explain their 'Logic Balance' decisions to develop metacognition.\n" +
                        "• Use the 'Memory Puzzle' to reinforce pattern recognition in Science life cycles.\n" +
                        "• Review 'Flashcards' together to strengthen vocabulary recall."
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Technical Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            
            Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                    Text("App Version: 1.2.0-PRO", style = MaterialTheme.typography.bodySmall)
                    Text("Last Content Update: Oct 2025 (Math Revised)", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ParentSection(title: String, icon: ImageVector, content: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )
        }
    }
}
