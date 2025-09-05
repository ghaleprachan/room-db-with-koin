package app.prgghale.roomdb.ui.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.composables.UserProfileImg
import app.prgghale.roomdb.extesion.Height
import app.prgghale.roomdb.extesion.Width

@Composable
fun ProfileScreen() {
    LoadingContent()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoadingContent() {
    LazyColumn(content = {
        item {
            AboutUser()
        }
        stickyHeader {
            HeaderTitle(title = "Technologies I Worked With")
        }
        itemsIndexed(myTechStack) { _, item ->
            TechItem(name = item)
        }
        stickyHeader {
            HeaderTitle(title = "Teach That Interest's me")
        }
        itemsIndexed(myInterestTechStack) { _, item ->
            TechItem(name = item)
        }
    })
}

@Composable
private fun AboutUser() {
    Column {
        Row(Modifier.padding(vertical = 12.dp, horizontal = 24.dp)) {
            UserProfileImg(firstName = "Prachan", lastName = "Ghale")
            12.Width()
            Column {
                Text(text = "Prachan Ghale", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                8.Height()
                Text(
                    text = "Hello, I am Prachan Ghale. I'm Mobile Application Developer based in kathmandu. I have been developing mobile application for over a year now. I have worked on project which has potential to become very important in user's life which I'm really proud of.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        16.Height()
    }
}

@Composable
private fun HeaderTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun TechItem(name: String) {
    Text(
        text = name, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp),
        color = Color.Gray
    )
}

val myTechStack = listOf(
    "Android App Development",
    "Flutter (Android, iOS and Web)",
    "Rest APIs",
    "Firebase",
    "Jetpack Compose",
    "Kotlin",
    "Dart",
    "Java",
    "Google Map Services and APIs",
    "Room Database",
)

val myInterestTechStack = listOf(
    "Blockchain Technology",
    ".Net 6",
    "Node.js",
    "Kotlin/js",
    "KMM",
    "c#",
    "Solidity",
    "React",
    "Machine Learning and AI",
    "Jetpack compose for web",
    "Kotlin for Blockchain",
    "Kotlin for server side",
    "SQL",
    "Blazor",
    "Unity",
    "Kotlin for Desktop",
    "Blockchain Technology",
    ".Net 6",
    "Node.js",
    "Kotlin/js",
    "KMM",
    "c#",
    "Solidity",
    "React",
    "Machine Learning and AI",
    "Jetpack compose for web",
    "Kotlin for Blockchain",
    "Kotlin for server side",
    "SQL",
    "Blazor",
    "Unity",
    "Kotlin for Desktop",
)