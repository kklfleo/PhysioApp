package com.example.physioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    /* Create an insteance of plan */
    var exercises by remember { mutableStateOf(plan) }
    /* Enables the reset button */
    val exercisesComplete = exercises.all { it.state }

    /* Layout of the App */
    Scaffold(
        topBar = {
            PhysioTopAppBar()
        }
    )
    /* Generate contents of the App based of the plan of exercises */
    { it ->
        LazyColumn(contentPadding = it) {
            itemsIndexed(exercises, key = { _, exercise -> exercise.name }) { index, exercise ->
                ExerciseItem(
                    exercise = exercise,
                    /* Indicate the Exercise is completed for complete button */
                    onDoneClick = {
                        val newExercises = exercises.toMutableList()
                        newExercises[index] = exercise.copy(state = true)
                        exercises = newExercises
                    },
                    /* Check what sets are completed for each exercise */
                    onSetClick = { setIndex ->
                        val newExercises = exercises.toMutableList()
                        val updatedSetsDone = exercise.setsDone.toMutableList()
                        updatedSetsDone[setIndex] = !updatedSetsDone[setIndex]
                        newExercises[index] = exercise.copy(setsDone = updatedSetsDone)
                        exercises = newExercises
                    },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
            /* Generate the complete button, and re-enable the previous buttons */
            item {
                Button(
                    onClick = {
                        exercises = exercises.map {
                            it.copy(state = false, setsDone = List(it.set) { false })
                        }
                    },
                    enabled = exercisesComplete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(stringResource(R.string.reset_msg))
                }
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
            ) {
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
    onDoneClick: () -> Unit,
    onSetClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val setsComplete = exercise.setsDone.all { it }

    /* Encapsulate all information into a Card */
    Card(modifier = modifier)
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ExerciseInformation(exercise.name, exercise.hold, exercise.set, exercise.reps)

                /* Generate the Done button, disable when exercise is completed */
                Button(
                    onClick = onDoneClick,
                    enabled = setsComplete && !exercise.state,
                    modifier = Modifier.height(dimensionResource(R.dimen.image_size))


                ) {
                    Text(stringResource(R.string.done_msg))
                }
            }

            /* Generate buttons for number of sets which require completion, disable when completed */
            if (exercise.set > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.Start
                ) {
                    repeat(exercise.set) { setIndex ->
                        val isSetDone = exercise.setsDone[setIndex]
                        Button(
                            onClick = { onSetClick(setIndex) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSetDone) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                        ) {
                            Text("${setIndex + 1}")
                        }
                    }
                }
            }
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
    modifier: Modifier = Modifier
) {
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

/* Display a preview of the App */
@Preview(showBackground = true)
@Composable
fun PhysioAppPreview() {
    PhysioAppTheme {
        PhysioApp()
    }
}
