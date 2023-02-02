package io.well.schoccer

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint
import io.well.schoccer.domain.Schedule
import io.well.schoccer.ui.theme.SchoccerTheme
import io.well.schoccer.viewmodel.MatchViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSchedules()
        setContent {
            SchoccerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    val schedules: List<Schedule>? by viewModel.schedulesStateFlow.collectAsState()
                    schedules?.let { matchGroup ->
                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(25.dp))
                            }
                            items(matchGroup) {
                                MatchGroup(it)
                            }
                            item {
                                Spacer(modifier = Modifier.height(25.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchGroup(
    schedule: Schedule,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(10.dp)
                .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 12.dp, vertical = 25.dp)
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            schedule.matches.forEach { match ->
                Text(
                    text = match,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
        Box(
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = schedule.time,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .offset(x = 30.dp)
                    .background(MaterialTheme.colors.primary)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun MatchGroupV2(
    schedule: Schedule,
    modifier: Modifier = Modifier
) {
    Box {
        Text(
            text = schedule.time,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .zIndex(1f)
                .offset(x = 30.dp)
                .background(MaterialTheme.colors.primary)
                .padding(horizontal = 20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .padding(10.dp)
                    .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 12.dp, vertical = 25.dp)
                    .then(modifier),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                schedule.matches.forEach { match ->
                    Text(
                        text = match,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 200,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    widthDp = 200,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun MatchGroupsPreview() {
    SchoccerTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            MatchGroup(
                schedule = Schedule(
                    "19h00",
                    listOf(
                        "A x B",
                        "C x D",
                        "E x F"
                    )
                )
            )
            MatchGroup(
                schedule = Schedule("19h00", listOf("A x B", "C x D", "E x F"))
            )
            MatchGroup(
                schedule = Schedule("19h00", listOf("A x B", "C x D", "E x F"))
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 200,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    widthDp = 200,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun MatchGroupsV2Preview() {
    SchoccerTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
        ) {
            MatchGroupV2(
                schedule = Schedule(
                    "19h00",
                    listOf(
                        "A x B",
                        "C x D",
                        "E x F"
                    )
                )
            )
            MatchGroupV2(
                schedule = Schedule("19h00", listOf("A x B", "C x D", "E x F"))
            )
            MatchGroupV2(
                schedule = Schedule("19h00", listOf("A x B", "C x D", "E x F"))
            )
        }
    }
}

