package com.example.mobileappproject


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobileappproject.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.btnSignOut?.setOnClickListener{
            if(auth.currentUser!= null)
            {
                auth.signOut()
                startActivity(Intent(this, GetStartedActivity::class.java))
                finish()
            }
        }

        // Setting up Compose UI for navigation and other UI elements
        setContent {
            val navController = rememberNavController()
            MainPage(
                navController = navController,
                onLogoutClick = { performLogout() })
        }
    }

    fun performLogout() {
        if (auth.currentUser != null) {
            auth.signOut()
            startActivity(Intent(this, GetStartedActivity::class.java))
            finish()
        }
    }


    // Composable function for MainPage UI
    @Composable
    fun MainPage(
        navigateToForum: () -> Unit = {},
        navController: NavController,
        onLogoutClick: () -> Unit
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val context = LocalContext.current


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 3) // Set the height of the image section
            ) {
                // Top Image with Rounded Shape
                Image(
                    painter = painterResource(id = R.drawable.mainpic), // Replace with your image resource
                    contentDescription = "Main Image",
                    modifier = Modifier
                        .fillMaxWidth() // Span full width
                        .height(screenHeight / 3) // Occupy 1/3 of screen height
                )



                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = (screenHeight / 14))
                        .padding(4.dp)
                ) {
                    // Emergency Phone Button
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .height(95.dp)
                            .clickable { /* TODO */ },
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()
                            .clickable {

                                val intent = Intent(context, ETelephone::class.java)
                                context.startActivity(intent)
                                Log.d("BoxClicked", "Emergency Phone clicked!")
                            }) {
                            // Background image
                            Image(
                                painter = painterResource(id = R.drawable.janecalls),
                                contentDescription = "Emergency Phone",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            // Text positioned at the bottom-left
                            Text(
                                text = "Emergency Phone",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp // Increase font size for better visibility
                                ),
                                color = MaterialTheme.colorScheme.onPrimary, // Ensure text is white
                                modifier = Modifier
                                    .align(Alignment.BottomStart) // Position text at the bottom-left
                                    .padding(8.dp) // Add padding for better placement
                            )
                        }
                    }

                    // Report Incident Button
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .height(95.dp)
                            .clickable { /* TODO */ },
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Background image
                            Image(
                                painter = painterResource(id = R.drawable.reports),
                                contentDescription = "Report Incident",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            // Text positioned at the bottom-left
                            Text(
                                text = "Report Incident",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp // Increase font size for better visibility
                                ),
                                color = MaterialTheme.colorScheme.onPrimary, // Ensure text is white
                                modifier = Modifier
                                    .align(Alignment.BottomStart) // Position text at the bottom-left
                                    .padding(8.dp) // Add padding for better placement
                            )
                        }
                    }
                }
            }



            Spacer(modifier = Modifier.height(80.dp))

            // "Other Features" Title
            Text(
                text = "Other Features",

                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.align(Alignment.Start)
                    .padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Cards for other features
            FeatureGrid(
                navigateToForum = {
                    // Handle navigation to forum here
                    println("Navigating to forum...")
                },
                onLogoutClick = {
                    // Handle the logout action here, for example, navigating to login screen
                    println("Logout clicked!")
                }
            )
        }
    }



    @Composable
    fun HorizontalFeatureCard(title: String, iconId: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
        Card(
            modifier = modifier
                .width(175.dp)
                .height(80.dp)
                .clickable(onClick = onClick),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = title,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }


    @Composable
    fun FeatureGrid(navigateToForum: () -> Unit, onLogoutClick: () -> Unit) {

        val context = LocalContext.current
        val customPurple = Color(0xFF9B4DCA)
        val customBlack = Color(0xFF000000)
        val shadowGray = Color(0xFFB0B0B0)

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp) // Add padding to avoid overlapping with the button
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    HorizontalFeatureCard(
                        title = "Call",
                        iconId = R.drawable.call,
                        onClick = {
                            val phoneNumber = "tel:+1234567890"  // Replace with the phone number you want to dial
                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber))
                            try {
                                context.startActivity(dialIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
                            }
                        })
                    HorizontalFeatureCard(title = "Share Location", iconId = R.drawable.location, onClick = { /* TODO */ })
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    HorizontalFeatureCard(title = "Directions", iconId = R.drawable.directions, onClick = { /* TODO */ })
                    HorizontalFeatureCard(title = "Account", iconId = R.drawable.account, onClick = { /* TODO */ })
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    HorizontalFeatureCard(title = "Forum", iconId = R.drawable.messaging, onClick = { /* TODO */ })
                }
            }

            // Circular Logout Button
            val circleSize = 64.dp
            FloatingActionButton(
                onClick = {
                    val auth = FirebaseAuth.getInstance()
                    if (auth.currentUser != null) {
                        auth.signOut()
                        val intent = Intent(context, GetStartedActivity::class.java)
                        context.startActivity(intent)
                        (context as? Activity)?.finish() // Finish the current activity
                    }
                },
                shape = CircleShape,
                containerColor = customPurple,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 18.dp)
                    .size(circleSize)
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Half-circle arch on top of the button
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = circleSize)

            ) {
                val lineHeight = 4f // Line thickness
                val padding = 16f // Space between the line and the circle
                val startY = padding + 9f  // Position of the line from the top of the circle
                val circleRadius = (circleSize / 2).toPx()

                val arcRadius = circleRadius * 1.2f

                val centerX = size.width / 2



                // Draw a path that wraps around the circle
                val path = Path().apply {
                    // Start at the left side of the screen
                    moveTo(0f, startY)

                    // Draw the line going around the circle
                    lineTo(centerX - arcRadius - padding, startY)  // Start at left with padding

                    // Arc around the circle
                    arcTo(
                        rect = Rect(
                            left = centerX - arcRadius,
                            top = startY - arcRadius,
                            right = centerX + arcRadius,
                            bottom = startY + arcRadius
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 180f,
                        forceMoveTo = false
                    )

                    // Continue drawing the line after the circle
                    lineTo(size.width, startY)
                }

                // Draw the path with the gradient effect
                drawPath(
                    color = shadowGray,
                    path = path,

                    style = Stroke(width = lineHeight)
                )
            }
        }
    }





    @Preview(showBackground = true)
    @Composable
    fun MainPagePreview() {

        val navController = rememberNavController()
        MainPage(navController = navController, onLogoutClick = { performLogout() })
    }
}

