package com.example.physioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.physioapp.data.Exercise
import com.example.physioapp.data.plan
import com.example.physioapp.ui.theme.PhysioAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhysioAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    PhysioApp()
                }

            }
        }
    }
}

/* Composable to display the App and generate content */
@Composable
fun PhysioApp() {
    Scaffold (
        topBar = {
            PhysioTopAppBar()
        }
    ) { it->
        LazyColumn(contentPadding = it){
            items(plan) {
                ExerciseItem(
                    exercise = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

/* Display the top bar */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysioTopAppBar( modifier: Modifier = Modifier )
{
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = stringResource(R.string.actual_app_name),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        },
        modifier = modifier
    )
}
/* Display a list item to display an exercise */
@Composable
fun ExerciseItem (
    exercise: Exercise,
    modifier: Modifier = Modifier
)
{
    Card(modifier = modifier)
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            ExerciseInformation(exercise.name, exercise.hold, exercise.set, exercise.reps, exercise.state)
        }
    }
}

/* Fill out the list with exercise information */
@Composable
fun ExerciseInformation(
    @StringRes eName: Int,
    eHold: Int,
    eSet: Int,
    eReps: Int,
    eState: Boolean,
    modifier: Modifier = Modifier
)
{
    Column(modifier = modifier) {
        Text(
            text = stringResource(eName),
            fontWeight = FontWeight.Bold

        )
        Text(
            text = stringResource(R.string.exercise_msg, eSet, eReps, eHold),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhysioAppPreview() {
    PhysioAppTheme {
        PhysioApp()
    }
}