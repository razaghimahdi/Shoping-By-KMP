package presentation.ui.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import business.domain.main.Comment
import business.domain.main.Product
import presentation.component.CircleImage
import presentation.component.DEFAULT__BUTTON_SIZE
import presentation.component.DefaultButton
import presentation.component.DefaultScreenUI
import presentation.component.ExpandingText
import presentation.component.Spacer_16dp
import presentation.component.Spacer_32dp
import presentation.component.Spacer_4dp
import presentation.component.Spacer_8dp
import presentation.component.noRippleClickable
import presentation.component.rememberCustomImagePainter
import presentation.theme.BackgroundContent
import presentation.theme.orange_400
import presentation.ui.main.detail.view_model.DetailEvent
import presentation.ui.main.detail.view_model.DetailState
import presentation.ui.main.home.view_model.HomeEvent
import kotlin.reflect.KFunction1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(popup: () -> Unit, state: DetailState, events: (DetailEvent) -> Unit) {


    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(DetailEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(DetailEvent.OnRetryNetwork) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 75.dp)
            ) {

                Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {

                    Image(
                        painter = rememberCustomImagePainter(state.selectedImage),
                        null, modifier = Modifier.fillMaxSize()
                    )

                    Box(modifier = Modifier.padding(16.dp).align(Alignment.TopStart)) {
                        Card(
                            modifier = Modifier.size(55.dp).padding(4.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.cardElevation(8.dp),
                            onClick = {
                                popup()
                            }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.ArrowBack, null)
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(16.dp).align(Alignment.TopEnd)) {
                        Card(
                            modifier = Modifier.size(55.dp).padding(4.dp),
                            shape = CircleShape,
                            elevation = CardDefaults.cardElevation(8.dp),
                            onClick = {
                                events(DetailEvent.Like(state.product.id))
                            }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    if (state.product.isLike) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    null
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(state.product.gallery) {
                                    ImageSliderBox(it) {
                                        events(DetailEvent.OnUpdateSelectedImage(it))
                                    }
                                }
                            }
                        }
                    }


                }

                Spacer_32dp()

                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            state.product.category.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Filled.Star, null, tint = orange_400)
                            Text(
                                state.product.rate.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }


                    Spacer_16dp()


                    Text(state.product.title, style = MaterialTheme.typography.headlineLarge)

                    Spacer_16dp()

                    Text("Product Details", style = MaterialTheme.typography.titleLarge)

                    Spacer_8dp()

                    ExpandingText(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.product.description,
                        style = MaterialTheme.typography.bodyMedium,
                    ) {}


                    Spacer_16dp()

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = BackgroundContent
                    )

                    Spacer_16dp()

                    Text(
                        text = "Read some comments",
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer_8dp()

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(state.product.comments) {
                            CommentBox(comment = it)
                        }
                    }


                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
                BuyButtonBox(
                    state.product
                ) {
                    events(DetailEvent.AddBasket(state.product.id))
                }
            }
        }
    }
}

@Composable
fun BuyButtonBox(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Total Price", style = MaterialTheme.typography.titleMedium)
                Text(product.getPrice(), style = MaterialTheme.typography.titleLarge)
            }

            DefaultButton(
                modifier = Modifier.fillMaxWidth(.7f).height(DEFAULT__BUTTON_SIZE),
                text = "Add to Cart"
            ) {
                onClick()
            }
        }
    }
}

@Composable
fun CommentBox(comment: Comment) {
    Box(modifier = Modifier.padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.width(300.dp).height(160.dp),
            elevation = CardDefaults.cardElevation(8.dp), shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircleImage(
                            image = comment.user.image,
                            modifier = Modifier.size(55.dp)
                        )
                        Spacer_4dp()
                        Text(comment.user.fetchName(), style = MaterialTheme.typography.titleSmall)
                    }
                    Text("2 days ago", style = MaterialTheme.typography.titleSmall)
                }
                Spacer_8dp()
                Column {
                    Text(
                        comment.comment,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer_4dp()

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Star, null, tint = orange_400)
                        Text(comment.rate.toString(), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun ImageSliderBox(it: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(65.dp).clip(MaterialTheme.shapes.small).padding(4.dp)
            .noRippleClickable { onClick() }) {
        Image(
            rememberCustomImagePainter(it),
            null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
