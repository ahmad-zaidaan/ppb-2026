package com.example.ets_registrasi_perpustakaan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
//import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ets_registrasi_perpustakaan.ui.theme.EtsregistrasiperpustakaanTheme

val SurfaceWhite = Color(0xFFFFFFFF)
val SurfaceMuted = Color(0xFFF7F7F5)
val BorderSubtle = Color(0xFFEBEBEA)
val ContentPrimary = Color(0xFF2E2E2D)
val ContentSecondary = Color(0xFF787774)
val ContentTertiary = Color(0xFFA5A5A4)
val BrandBlue = Color(0xFF2383E2)
val BrandBlueSoft = Color(0xFFEBF5FB)
val DangerRed = Color(0xFFEB5757)
val BadgeBg = Color(0xFFF1F1EF)
val BadgeText = Color(0xFF5F5E5B)

data class Member(
    val id: Int,
    val fullName: String,
    val idNumber: String,
    val studyProgram: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val membershipType: String
)

val dummyMembers = listOf(
    Member(
        id = 1,
        fullName = "Andi Pratama",
        idNumber = "2301001",
        studyProgram = "Informatika",
        address = "Jl. Teknik Komputer No. 12, Surabaya",
        phoneNumber = "081234567890",
        email = "andi.pratama@mhs.ac.id",
        membershipType = "Mahasiswa"
    ),
    Member(
        id = 2,
        fullName = "Siti Aisyah",
        idNumber = "2301002",
        studyProgram = "Sistem Informasi",
        address = "Jl. Gebang Wetan No. 45, Surabaya",
        phoneNumber = "081298765432",
        email = "siti.aisyah@mhs.ac.id",
        membershipType = "Mahasiswa"
    ),
    Member(
        id = 3,
        fullName = "Budi Santoso",
        idNumber = "198501001",
        studyProgram = "Teknik Elektro",
        address = "Jl. Manyar Sabrangan No. 8, Surabaya",
        phoneNumber = "085712345678",
        email = "budi.santoso@staff.ac.id",
        membershipType = "Dosen"
    ),
    Member(
        id = 4,
        fullName = "Rina Lestari",
        idNumber = "2301003",
        studyProgram = "Teknik Komputer",
        address = "Jl. Keputih Perintis No. 20, Surabaya",
        phoneNumber = "087811223344",
        email = "rina.lestari@mhs.ac.id",
        membershipType = "Mahasiswa"
    )
)

enum class ScreenState {
    REGISTER,
    MEMBER_LIST
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EtsregistrasiperpustakaanTheme {
                LibraryRegApp()
            }
        }
    }
}

@Composable
fun LibraryRegApp() {
    var currentScreen by remember { mutableStateOf(ScreenState.REGISTER) }
    var selectedMemberForDetail by remember { mutableStateOf<Member?>(null) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var memberToDelete by remember { mutableStateOf<Member?>(null) }
    var showSuccessSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var isEditMode by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AppBottomNav(
                currentScreen = currentScreen,
                onSelect = { destination ->
                    if (destination == ScreenState.REGISTER) {
                        isEditMode = false
                    }
                    currentScreen = destination
                }
            )
        },
        snackbarHost = {
            if (showSuccessSnackbar) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { showSuccessSnackbar = false }) {
                            Text("Tutup", color = BrandBlue)
                        }
                    },
                    containerColor = ContentPrimary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(snackbarMessage, fontSize = 13.sp)
                }
            }
        },
        containerColor = SurfaceWhite
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SurfaceWhite)
        ) {
            when (currentScreen) {
                ScreenState.REGISTER -> {
                    RegistrationScreen(
                        isEditing = isEditMode,
                        onSubmit = {
                            snackbarMessage = if (isEditMode) "Perubahan data berhasil disimpan" else "Pendaftaran anggota baru berhasil"
                            showSuccessSnackbar = true
                            currentScreen = ScreenState.MEMBER_LIST
                        }
                    )
                }

                ScreenState.MEMBER_LIST -> {
                    MemberListScreen(
                        members = dummyMembers,
                        onDetailClick = { selectedMemberForDetail = it },
                        onEditClick = {
                            isEditMode = true
                            currentScreen = ScreenState.REGISTER
                        },
                        onDeleteClick = { member ->
                            memberToDelete = member
                            showDeleteConfirmDialog = true
                        }
                    )
                }
            }

            selectedMemberForDetail?.let { member ->
                MemberDetailDialog(
                    member = member,
                    onDismiss = { selectedMemberForDetail = null },
                    onEdit = {
                        selectedMemberForDetail = null
                        isEditMode = true
                        currentScreen = ScreenState.REGISTER
                    }
                )
            }

            if (showDeleteConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmDialog = false },
                    title = {
                        Text(
                            text = "Hapus Anggota",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = ContentPrimary
                        )
                    },
                    text = {
                        Text(
                            text = "Apakah Anda yakin ingin menghapus data \"${memberToDelete?.fullName ?: "Anggota"}\"? Tindakan ini tidak dapat dibatalkan.",
                            color = ContentSecondary,
                            fontSize = 14.sp
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDeleteConfirmDialog = false
                                snackbarMessage = "Anggota ${memberToDelete?.fullName ?: ""} berhasil dihapus"
                                showSuccessSnackbar = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Hapus", color = Color.White, fontSize = 13.sp)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmDialog = false }) {
                            Text("Batal", color = ContentSecondary, fontSize = 13.sp)
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    containerColor = Color.White
                )
            }
        }
    }
}

@Composable
fun AppBottomNav(
    currentScreen: ScreenState,
    onSelect: (ScreenState) -> Unit
) {
    Surface(
        color = SurfaceMuted,
        border = BorderStroke(0.8.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.EditNote,
                label = "Pendaftaran",
                selected = currentScreen == ScreenState.REGISTER,
                onClick = { onSelect(ScreenState.REGISTER) }
            )
            BottomNavItem(
                icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                label = "Daftar Anggota",
                selected = currentScreen == ScreenState.MEMBER_LIST,
                onClick = { onSelect(ScreenState.MEMBER_LIST) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) ContentPrimary else ContentTertiary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) ContentPrimary else ContentTertiary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    isEditing: Boolean,
    onSubmit: () -> Unit
) {
    var fullName by remember { mutableStateOf(if (isEditing) "Andi Pratama" else "") }
    var idNumber by remember { mutableStateOf(if (isEditing) "2301001" else "") }
    var studyProgram by remember { mutableStateOf(if (isEditing) "Informatika" else "") }
    var address by remember { mutableStateOf(if (isEditing) "Jl. Teknik Komputer No. 12, Surabaya" else "") }
    var phoneNumber by remember { mutableStateOf(if (isEditing) "081234567890" else "") }
    var email by remember { mutableStateOf(if (isEditing) "andi.pratama@mhs.ac.id" else "") }
    var membershipType by remember { mutableStateOf("Mahasiswa") }

    var studyProgramExpanded by remember { mutableStateOf(false) }
    var membershipTypeExpanded by remember { mutableStateOf(false) }

    val studyProgramOptions = listOf(
        "Informatika",
        "Sistem Informasi",
        "Teknik Komputer",
        "Teknik Elektro",
        "Teknik Mesin",
        "Teknik Sipil",
        "Manajemen Bisnis",
        "Lainnya"
    )

    val membershipOptions = listOf(
        "Mahasiswa",
        "Dosen",
        "Tenaga Kependidikan",
        "Umum"
    )

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = ContentPrimary,
        unfocusedTextColor = ContentPrimary,
        focusedContainerColor = SurfaceMuted,
        unfocusedContainerColor = SurfaceMuted,
        focusedBorderColor = BrandBlue,
        unfocusedBorderColor = BorderSubtle,
        focusedLabelColor = BrandBlue,
        unfocusedLabelColor = ContentSecondary
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE8D4C8),
                            Color(0xFFF7C8BB),
                            Color(0xFFD4E8F2),
                            Color(0xFFBCE1E5)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "📚",
                fontSize = 44.sp,
                modifier = Modifier.offset(y = (-24).dp)
            )

            Text(
                text = if (isEditing) "Perbarui Data Anggota" else "Pendaftaran Anggota",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = ContentPrimary,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.offset(y = (-14).dp)
            )

            Text(
                text = "Lengkapi formulir di bawah ini untuk mendaftarkan anggota baru perpustakaan.",
                fontSize = 13.sp,
                color = ContentSecondary,
                lineHeight = 18.sp,
                modifier = Modifier.offset(y = (-10).dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Nama Lengkap") },
                placeholder = { Text("Nama sesuai kartu identitas") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = idNumber,
                onValueChange = { input -> idNumber = input.filter { it.isDigit() } },
                label = { Text("NIM / NIP") },
                placeholder = { Text("Nomor induk unik") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = studyProgramExpanded,
                onExpandedChange = { studyProgramExpanded = !studyProgramExpanded }
            ) {
                OutlinedTextField(
                    value = studyProgram,
                    onValueChange = { studyProgram = it },
                    label = { Text("Program Studi / Unit") },
                    placeholder = { Text("Pilih Program Studi") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = studyProgramExpanded)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = studyProgramExpanded,
                    onDismissRequest = { studyProgramExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    studyProgramOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontSize = 13.sp, color = ContentPrimary) },
                            onClick = {
                                studyProgram = option
                                studyProgramExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("user@domain.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { input -> phoneNumber = input.filter { it.isDigit() } },
                label = { Text("Nomor Telepon") },
                placeholder = { Text("08xxxxxxxxxx") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Alamat") },
                placeholder = { Text("Alamat domisili saat ini") },
                minLines = 2,
                maxLines = 3,
                shape = RoundedCornerShape(10.dp),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = membershipTypeExpanded,
                onExpandedChange = { membershipTypeExpanded = !membershipTypeExpanded }
            ) {
                OutlinedTextField(
                    value = membershipType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jenis Keanggotaan") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = membershipTypeExpanded)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = membershipTypeExpanded,
                    onDismissRequest = { membershipTypeExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    membershipOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontSize = 13.sp, color = ContentPrimary) },
                            onClick = {
                                membershipType = option
                                membershipTypeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onSubmit,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = if (isEditing) "Simpan Perubahan" else "Daftarkan Anggota",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun MemberListScreen(
    members: List<Member>,
    onDetailClick: (Member) -> Unit,
    onEditClick: (Member) -> Unit,
    onDeleteClick: (Member) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val filteredList = if (query.isBlank()) {
        members
    } else {
        members.filter {
            it.fullName.contains(query, ignoreCase = true) ||
                    it.idNumber.contains(query, ignoreCase = true) ||
                    it.studyProgram.contains(query, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Surface(
            color = SurfaceMuted,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, BorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = ContentTertiary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Cari nama, NIM, atau prodi...",
                            color = ContentTertiary,
                            fontSize = 13.sp
                        )
                    }
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            color = ContentPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = ContentTertiary,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { query = "" }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                color = BadgeBg,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "Daftar Anggota (${filteredList.size})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BadgeText,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ditemukan anggota dengan kata kunci \"$query\"",
                    color = ContentSecondary,
                    fontSize = 13.sp
                )
            }
        } else {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn {
                    items(filteredList, key = { it.id }) { member ->
                        MemberRowItem(
                            member = member,
                            isLast = member == filteredList.last(),
                            onCardClick = { onDetailClick(member) },
                            onEditClick = { onEditClick(member) },
                            onDeleteClick = { onDeleteClick(member) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MemberRowItem(
    member: Member,
    isLast: Boolean,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick() }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                tint = ContentTertiary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = member.fullName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ContentPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = when (member.membershipType) {
                            "Dosen" -> Color(0xFFFBF3DB)
                            "Mahasiswa" -> Color(0xFFEDF3EC)
                            else -> BadgeBg
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = member.membershipType,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = when (member.membershipType) {
                                "Dosen" -> Color(0xFF8F6B00)
                                "Mahasiswa" -> Color(0xFF2B593F)
                                else -> BadgeText
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "NIM: ${member.idNumber}",
                        fontSize = 11.sp,
                        color = ContentSecondary
                    )
                }
            }

            Row {
                IconButton(onClick = onEditClick, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = ContentSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(onClick = onDeleteClick, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Hapus",
                        tint = DangerRed,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }

        if (!isLast) {
            HorizontalDivider(
                color = BorderSubtle,
                thickness = 0.8.dp,
                modifier = Modifier.padding(start = 46.dp)
            )
        }
    }
}

@Composable
fun MemberDetailDialog(
    member: Member,
    onDismiss: () -> Unit,
    onEdit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📄", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Kartu Anggota",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = ContentPrimary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    color = SurfaceMuted,
                    border = BorderStroke(1.dp, BorderSubtle),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = member.fullName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ContentPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "NO: ${member.idNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = ContentSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = BrandBlueSoft,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "${member.studyProgram} • ${member.membershipType}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BrandBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                PropertyRow(label = "Nomor Telepon", value = member.phoneNumber)
                PropertyRow(label = "Alamat Email", value = member.email)
                PropertyRow(label = "Alamat Domisili", value = member.address)
            }
        },
        confirmButton = {
            Button(
                onClick = onEdit,
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Edit Data", color = Color.White, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup", color = ContentSecondary, fontSize = 13.sp)
            }
        },
        shape = RoundedCornerShape(14.dp),
        containerColor = Color.White
    )
}

@Composable
fun PropertyRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = ContentTertiary,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            color = ContentPrimary
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LibraryRegAppPreview() {
    EtsregistrasiperpustakaanTheme {
        LibraryRegApp()
    }
}
