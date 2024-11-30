package com.example.mobileappproject

import android.app.Activity
import android.content.Intent
import android.net.Uri

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mobileappproject.ui.theme.MobileAppProjectTheme
import com.example.mobileappproject.Contact



class ETelephone : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the XML layout as the content view
        setContentView(R.layout.activity_telephone)

        // Find the ComposeView by ID and set the composable content inside it
        val composeView: ComposeView = findViewById(R.id.compose_view)
        composeView.setContent {
            EmergencyTelephonePage(navController = rememberNavController())
        }
    }




    @Composable
    fun EmergencyTelephonePage(navController: NavHostController) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        var expanded = remember { mutableStateOf(false) }
        val context = LocalContext.current

        // State for the Name field (optional)
        val name = remember { mutableStateOf("") }

        // State for the Phone Number field
        val phoneNumber = remember { mutableStateOf("") }

        // State for the list of favorite contacts
        val favoriteContacts = remember { mutableStateListOf<Contact>() }




        val onSaveContact = {
            if (name.value.isNotEmpty() && phoneNumber.value.isNotEmpty()) {
                // Add the new contact to the favorites list
                favoriteContacts.add(Contact(name.value, phoneNumber.value))
                // Clear the text fields after saving
                name.value = ""
                phoneNumber.value = ""
            } else {
                Toast.makeText(context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }


        // Main Content in a Scrollable Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 4)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.janecalls), // Background image
                    contentDescription = "Header Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                )

                // Overlay for gradient or tint
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.primary)
                            )
                        )
                )

                // Header Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Action Row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = {

                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.back), // Replace 'back' with your image's resource name
                                contentDescription = "Back",
                                modifier = Modifier.size(24.dp) // Set an appropriate size for the back button
                            )
                        }

                    }

                    // Title and Profile Section
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Emergency Telephone",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }
                }
            }

            // Emergency Buttons
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val phoneNumber = "tel:+999"  // Replace with the phone number you want to dial
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
                        try {
                            context.startActivity(dialIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1f) // Apply weight for equal width
                        .height(48.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.badge), // Your image resource
                            contentDescription = "Police Icon",
                            modifier = Modifier.size(22.dp) // Adjust image size
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Space between image and text
                        Text(text = "Police",
                            fontSize = 18.sp)
                    }
                }

                Button(
                    onClick = {
                        val phoneNumber = "tel:0206992000 "  // Replace with the phone number you want to dial
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
                        try {
                            context.startActivity(dialIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1f) // Apply weight for equal width
                        .height(48.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus), // Your image resource
                            contentDescription = "Ambulance Icon",
                            modifier = Modifier.size(22.dp) // Adjust image size
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Space between image and text
                        Text(text = "Ambulance",

                            fontSize = 18.sp )
                    }
                }
            }

            // Add Emergency Phone Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "+ Add emergency phone",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold, // Makes the text bold
                        fontSize = 18.sp
                    ),

                    )
                Spacer(modifier = Modifier.height(8.dp))




                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier

                        .padding(start = 80.dp)
                ) {
                    Button(
                        onClick = { favoriteContacts.add(Contact(name.value, phoneNumber.value)) },
                        modifier = Modifier
                            .height(48.dp)
                            .width(150.dp)
                            .padding(end = 7.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Make the background transparent
                            contentColor = Color.Black  // Set text color to black
                        ),

                        ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Replace with your own image in the drawable folder
                            Icon(
                                painter = painterResource(id = R.drawable.save),  // Replace with your custom image
                                contentDescription = "Save",
                                modifier = Modifier.size(16.dp)  // Adjust size of the icon
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                            Text("Save")
                        }
                    }
                    Button(
                        onClick = {
                            // Open the phone's dialer with the entered phone number
                            val phoneUri = Uri.parse("tel:${phoneNumber.value}")
                            val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)

                            try {
                                context.startActivity(dialIntent)
                            } catch (e: Exception) {
                                // Show a Toast message if the dialer fails to open
                                Toast.makeText(context, "Error: Unable to open dialer", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp) // Set height for the button
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Make the background transparent
                            contentColor = Color.Black  // Set text color to black
                        ),

                        ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Replace with your own image in the drawable folder
                            Icon(
                                painter = painterResource(id = R.drawable.callsave),  // Replace with your custom image
                                contentDescription = "Telephone",
                                modifier = Modifier.size(12.dp)
                                // Adjust size of the icon
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                            Text(
                                text = "Telephone",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp  // Change the font size as needed
                                )
                            )
                        }
                    }
                }
            }

            // Favorites Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Align icon and text vertically
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Icon before the "Favourites" text
                    Icon(
                        painter = painterResource(id = R.drawable.favourite), // Replace with your own icon
                        contentDescription = "Favorites Icon",
                        modifier = Modifier.size(20.dp) // Set the icon size
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between the icon and the text
                    Text(
                        text = "Favourites",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Sample Contact Items
                Column(modifier = Modifier.fillMaxWidth()) {
                    favoriteContacts.forEach { contact ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),  // Padding around each card

                            shape = RoundedCornerShape(8.dp) // Rounded corners for the card
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)  // Padding inside the card
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = contact.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp)) // Space between name and phone number
                                    Text(
                                        text = contact.phoneNumber,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        val phoneUri = Uri.parse("tel:${phoneNumber.value}")
                                        val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)

                                        try {
                                            context.startActivity(dialIntent)
                                        } catch (e: Exception) {
                                            // Show a Toast message if the dialer fails to open
                                            Toast.makeText(context, "Error: Unable to open dialer", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.padding(start = 8.dp) // Space between phone number and icon
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.callsave), // Use your own call icon image
                                        contentDescription = "Call Icon",
                                        modifier = Modifier.size(24.dp) // Icon size
                                    )
                                }
                            }
                        }
                    }

                    // Favorite Contact 2
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),  // Padding around each card

                        shape = RoundedCornerShape(8.dp),

                        ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Michelle",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp)) // Space between name and phone number
                                Text(
                                    text = "+254XXXXXXX",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                            IconButton(
                                onClick = { val phoneUri = Uri.parse("tel:${phoneNumber.value}")
                                    val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)

                                    try {
                                        context.startActivity(dialIntent)
                                    } catch (e: Exception) {
                                        // Show a Toast message if the dialer fails to open
                                        Toast.makeText(context, "Error: Unable to open dialer", Toast.LENGTH_SHORT).show()
                                    } },
                                modifier = Modifier.padding(start = 8.dp) // Space between phone number and icon
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.callsave), // Use your own call icon image
                                    contentDescription = "Call Icon",
                                    modifier = Modifier.size(24.dp) // Icon size
                                )
                            }
                        }
                    }
                }

            }
        }
    }





    @Composable
    fun FavoriteContact(name: String, phoneNumber: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = phoneNumber, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { /* Call action */ }) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    @Composable
    @Preview(showBackground = true, showSystemUi = true)
    fun EmergencyTelephonePagePreview() {

        val navController = rememberNavController()

        MaterialTheme { // Apply your app's theme
            EmergencyTelephonePage(navController)
        }
    }

}