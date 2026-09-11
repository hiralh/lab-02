package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepo = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepo.cities,
                        modifier = Modifier.padding(paddingValues = innerPadding),
                        onAddCity = {cityRepo.addCity(it)},  // Adds the user-entered city to the list in CityRepository
                        onDeleteCity = {cityRepo.deleteCity(it)}
                    )
                }
            }
        }
    }
}
class CityRepository{
    // Keep this mutable app data private because we do not want other classes to change it directly
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow",
        "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka",
        "New Delhi"
    )

    // Get a read-only list for the UI to display
    val cities: List<String>
        get() = _cities

    fun addCity(city:String){
        _cities.add(city)
    }

    fun deleteCity(city:String){
        _cities.remove(city)
    }
}

@Composable
fun CityListScreen(
    // CityListScreen receives cities: List<String> from MainActivity as the list of city names.
    cities: List<String>,
    // modifier: Modifier = Modifier allows layout information such as padding to be passed to the screen.
    modifier: Modifier = Modifier,
    onAddCity: (String) -> Unit,  // (String) -> Unit needs a function as a parameter. The function takes on String and returns Unit which means it performs an action but doesn't return anything.
    onDeleteCity: (String) -> Unit
){
    var newCityName by remember {mutableStateOf("")}
    //var selectedCityName by remember {mutableStateOf("")}

    Column(modifier = modifier.fillMaxSize()){
        Row(modifier = Modifier.padding(8.dp)){
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()){
                        onAddCity(newCityName)
                        newCityName=""
                    }
                }
            ){
                Text("Add City")
            }
            Button(
                onClick = {
                    if (newCityName.isNotBlank()){
                        onDeleteCity(newCityName)
                        newCityName=""
                    }
                }
            ){
                Text("Delete City")
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // items(cities) loops through the city list and creates one UI row for each city.
            items(cities){ city ->
                CityRow(city = city)
            }
        }
    }
}

@Composable
fun CityRow(city: String){ // Pass selectedCityName to CityRow to delete
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
    )
}