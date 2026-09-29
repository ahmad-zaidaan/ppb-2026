package com.example.kuis1_membuatuideklaratif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kuis1_membuatuideklaratif.ui.theme.Kuis1MembuatUIDeklaratifTheme
import kotlinx.coroutines.delay

data class Student(
    val id: Int,
    val name: String,
    val nrp: String,
    val major: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kuis1MembuatUIDeklaratifTheme {
                var isSplashActive by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(2000L)
                    isSplashActive = false
                }

                Crossfade(
                    targetState = isSplashActive,
                    animationSpec = tween(durationMillis = 400),
                    label = "SplashTransition"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen()
                    } else {
                        StudentManagerScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Logo",
                tint = Color(0xFF64748B),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Student Manager",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kelola data mahasiswa\ndengan mudah",
                fontSize = 15.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentManagerScreen() {
    val students = remember {
        mutableStateListOf(
            Student(1, "Budi Santoso", "2301001", "Informatika"),
            Student(2, "Siti Aminah", "2301002", "Sistem Informasi"),
            Student(3, "Andi Wijaya", "2301003", "Teknik Komputer")
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingStudent by remember { mutableStateOf<Student?>(null) }
    var deletingStudent by remember { mutableStateOf<Student?>(null) }

    val filteredStudents = students.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.nrp.contains(searchQuery, ignoreCase = true) ||
                it.major.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Student Manager",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                shape = CircleShape,
                containerColor = Color(0xFF1877F2),
                contentColor = Color.White,
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Student",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        containerColor = Color(0xFFF8FAFC)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "Cari mahasiswa...",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF64748B)
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFCBD5E1),
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah mahasiswa: ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "${filteredStudents.size}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(filteredStudents, key = { it.id }) { student ->
                    StudentItemCard(
                        student = student,
                        onEdit = { editingStudent = student },
                        onDelete = { deletingStudent = student }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        StudentFormDialog(
            title = "Tambah Mahasiswa",
            initialName = "",
            initialNrp = "",
            initialMajor = "",
            onDismiss = { showAddDialog = false },
            onConfirm = { name, nrp, major ->
                val newId = (students.maxOfOrNull { it.id } ?: 0) + 1
                students.add(Student(newId, name, nrp, major))
                showAddDialog = false
            }
        )
    }

    editingStudent?.let { student ->
        StudentFormDialog(
            title = "Edit Mahasiswa",
            initialName = student.name,
            initialNrp = student.nrp,
            initialMajor = student.major,
            onDismiss = { editingStudent = null },
            onConfirm = { name, nrp, major ->
                val index = students.indexOfFirst { it.id == student.id }
                if (index != -1) {
                    students[index] = student.copy(name = name, nrp = nrp, major = major)
                }
                editingStudent = null
            }
        )
    }

    deletingStudent?.let { student ->
        AlertDialog(
            onDismissRequest = { deletingStudent = null },
            title = {
                Text(text = "Hapus Mahasiswa", fontWeight = FontWeight.Bold)
            },
            text = {
                Text("Apakah Anda yakin ingin menghapus data ${student.name}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        students.removeAll { it.id == student.id }
                        deletingStudent = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                ) {
                    Text("Hapus", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingStudent = null }) {
                    Text("Batal", color = Color.Black)
                }
            }
        )
    }
}

@Composable
fun StudentItemCard(
    student: Student,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEEF2F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "NRP: ${student.nrp}",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
                Text(
                    text = student.major,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }

            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF334155),
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFDC2626),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun StudentFormDialog(
    title: String,
    initialName: String,
    initialNrp: String,
    initialMajor: String,
    onDismiss: () -> Unit,
    onConfirm: (name: String, nrp: String, major: String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var nrp by remember { mutableStateOf(initialNrp) }
    var major by remember { mutableStateOf(initialMajor) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Mahasiswa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nrp,
                    onValueChange = { input ->
                        val filteredInput = input.filter { it.isDigit() }
                        nrp = filteredInput
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("NRP") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = major,
                    onValueChange = { major = it },
                    label = { Text("Program Studi / Jurusan") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && nrp.isNotBlank()) {
                        onConfirm(name.trim(), nrp.trim(), major.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
            ) {
                Text("Simpan", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = Color.Black)
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    Kuis1MembuatUIDeklaratifTheme {
        SplashScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentManagerPreview() {
    Kuis1MembuatUIDeklaratifTheme {
        StudentManagerScreen()
    }
}