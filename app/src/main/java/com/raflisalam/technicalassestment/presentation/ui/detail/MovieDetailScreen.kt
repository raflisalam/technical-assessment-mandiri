package com.raflisalam.technicalassestment.presentation.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.raflisalam.technicalassestment.BuildConfig
import com.raflisalam.technicalassestment.core.utils.ApiResult
import com.raflisalam.technicalassestment.core.utils.AppConstant.YOUTUBE_THUMBNAIL_BASE_URL
import com.raflisalam.technicalassestment.core.utils.AppConstant.YOUTUBE_TRAILER_BASE_URL
import com.raflisalam.technicalassestment.domain.model.Genre
import com.raflisalam.technicalassestment.domain.model.MovieDetail
import com.raflisalam.technicalassestment.domain.model.Review
import com.raflisalam.technicalassestment.domain.model.Trailer
import com.raflisalam.technicalassestment.presentation.ui.common.ErrorView

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()
    val trailersState by viewModel.trailersState.collectAsStateWithLifecycle()
    val reviewsState by viewModel.reviewsState.collectAsStateWithLifecycle()
    val isLoadingMoreReviews by viewModel.isLoadingMoreReviews.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.loadAllData(movieId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = movieDetailState) {
            is ApiResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ApiResult.Success -> {
                MovieDetailContent(
                    movieDetail = state.data,
                    trailersState = trailersState,
                    reviewsState = reviewsState,
                    isLoadingMoreReviews = isLoadingMoreReviews,
                    hasNextReviewsPage = viewModel.hasNextReviewsPage,
                    onLoadMoreReviews = { viewModel.loadNextReviewsPage(movieId) },
                    onRetryTrailers = { viewModel.loadTrailers(movieId) },
                    onRetryReviews = { viewModel.loadReviews(movieId) },
                    onBackClick = onBackClick
                )
            }
            is ApiResult.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.loadAllData(movieId) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ApiResult.Default -> Unit
        }
    }
}

@Composable
private fun MovieDetailContent(
    movieDetail: MovieDetail,
    trailersState: ApiResult<List<Trailer>>,
    reviewsState: ApiResult<List<Review>>,
    isLoadingMoreReviews: Boolean,
    hasNextReviewsPage: Boolean,
    onLoadMoreReviews: () -> Unit,
    onRetryTrailers: () -> Unit,
    onRetryReviews: () -> Unit,
    onBackClick: () -> Unit
) {
    val reviews = (reviewsState as? ApiResult.Success)?.data ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            BackdropHeader(
                backdropPath = movieDetail.backdropPath,
                onBackClick = onBackClick
            )
        }
        item {
            MovieInfoRow(movieDetail = movieDetail)
        }
        item {
            GenreChipsRow(genres = movieDetail.genres)
        }
        item {
            OverviewSection(overview = movieDetail.overview)
        }
        item {
            SpokenLanguagesSection(languages = movieDetail.spokenLanguages)
        }
        item {
            TrailersSection(
                trailersState = trailersState,
                onRetry = onRetryTrailers
            )
        }
        item {
            ReviewsSectionHeader(reviewsState = reviewsState)
        }
        when (reviewsState) {
            is ApiResult.Loading -> item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(modifier = Modifier.size(32.dp)) }
            }
            is ApiResult.Error -> item {
                TextButton(
                    onClick = onRetryReviews,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) { Text("Retry") }
            }
            is ApiResult.Success -> {
                if (reviews.isEmpty()) {
                    item {
                        Text(
                            text = "No reviews yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                } else {
                    items(reviews, key = { it.id }) { review ->
                        ReviewCard(review = review)
                    }
                    if (hasNextReviewsPage) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isLoadingMoreReviews) {
                                    CircularProgressIndicator(modifier = Modifier.size(28.dp))
                                } else {
                                    TextButton(onClick = onLoadMoreReviews) {
                                        Text("Load more reviews")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is ApiResult.Default -> Unit
        }
    }
}

@Composable
private fun BackdropHeader(
    backdropPath: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = BuildConfig.TMDB_IMAGE_BASE_URL + "w780" + backdropPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun MovieInfoRow(movieDetail: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier
                .width(110.dp)
                .height(165.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = BuildConfig.TMDB_IMAGE_BASE_URL + "w342" + movieDetail.posterPath,
                contentDescription = movieDetail.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = movieDetail.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (movieDetail.tagline.isNotBlank()) {
                Text(
                    text = "\"${movieDetail.tagline}\"",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(2.dp))
            DetailInfoRow(
                icon = Icons.Default.Star,
                text = String.format("%.1f", movieDetail.voteAverage) + " (${movieDetail.voteCount})",
                tint = Color(0xFFFFD700)
            )
            if (movieDetail.releaseDate.isNotBlank()) {
                DetailInfoRow(icon = Icons.Default.CalendarToday, text = movieDetail.releaseDate)
            }
            if (movieDetail.runtime > 0) {
                val h = movieDetail.runtime / 60
                val m = movieDetail.runtime % 60
                val runtimeStr = if (h > 0) "${h}h ${m}m" else "${m}m"
                DetailInfoRow(icon = Icons.Default.AccessTime, text = runtimeStr)
            }
            if (movieDetail.status.isNotBlank()) {
                DetailInfoRow(icon = Icons.Default.Info, text = movieDetail.status)
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: ImageVector,
    text: String,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = tint
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GenreChipsRow(genres: List<Genre>) {
    if (genres.isEmpty()) return
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres, key = { it.id }) { genre ->
            AssistChip(
                onClick = {},
                label = { Text(genre.name, style = MaterialTheme.typography.labelMedium) }
            )
        }
    }
}

@Composable
private fun OverviewSection(overview: String) {
    if (overview.isBlank()) return
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        SectionTitle("Overview")
        Spacer(Modifier.height(8.dp))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
        )
    }
}

@Composable
private fun SpokenLanguagesSection(languages: List<String>) {
    if (languages.isEmpty()) return
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)) {
        SectionTitle("Languages")
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(languages) { lang ->
                AssistChip(
                    onClick = {},
                    label = { Text(lang, style = MaterialTheme.typography.labelMedium) }
                )
            }
        }
    }
}

@Composable
private fun TrailersSection(
    trailersState: ApiResult<List<Trailer>>,
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        SectionTitle(
            text = "Trailers",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(10.dp))
        when (trailersState) {
            is ApiResult.Loading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator(modifier = Modifier.size(28.dp)) }

            is ApiResult.Success -> {
                if (trailersState.data.isEmpty()) {
                    Text(
                        text = "No trailers available.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(trailersState.data, key = { it.id }) { trailer ->
                            TrailerCard(trailer = trailer)
                        }
                    }
                }
            }

            is ApiResult.Error -> TextButton(
                onClick = onRetry,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) { Text("Retry trailers") }

            is ApiResult.Default -> Unit
        }
    }
}

@Composable
private fun TrailerCard(trailer: Trailer) {
    val context = LocalContext.current
    val thumbnailUrl = "$YOUTUBE_THUMBNAIL_BASE_URL${trailer.key}/hqdefault.jpg"

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$YOUTUBE_TRAILER_BASE_URL${trailer.key}")
                )
                context.startActivity(intent)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = trailer.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
            )
            Text(
                text = trailer.name,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun ReviewsSectionHeader(reviewsState: ApiResult<List<Review>>) {
    val count = (reviewsState as? ApiResult.Success)?.data?.size
    val title = if (count != null) "Reviews ($count)" else "Reviews"
    SectionTitle(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun ReviewCard(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (review.author.avatarPath.isNotBlank()) {
                AsyncImage(
                    model = review.author.avatarPath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = review.author.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = review.author.name.ifBlank { review.author.username },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = review.createdAt.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                )
            }
            if (review.author.rating > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = String.format("%.1f", review.author.rating),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis,
            modifier = Modifier.clickable { expanded = !expanded }
        )
        if (!expanded && review.content.length > 200) {
            Text(
                text = "Read more",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(top = 2.dp)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}
