package gastonsanguinetti.tvshows.ext

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import com.squareup.picasso.Picasso
import gastonsanguinetti.tvshows.R


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.animatedShow() {
    show()
    alpha = 0f
    animate()
            .alpha(1f)
            .setDuration(200)
            .start()
}

fun ImageView.loadImage(imageUrl: String) =
        doOnPreDraw { Picasso.with(this.context).load(imageUrl).into(this) }

fun AppCompatActivity.showConnectionErrorView(onRetry: () -> Unit) =
        Snackbar.make(findViewById(android.R.id.content),
                getString(R.string.connection_error_msg), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry_button_label)) { onRetry() }
                .show()