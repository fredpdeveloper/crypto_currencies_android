package com.example.cryptos.adapter

import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptos.network.model.Article

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("cursorPosition")
    fun setCursorPosition(editText: EditText, text: String) {
        editText.setSelection(text.length)
    }

    @JvmStatic
    @BindingAdapter("listData")
    fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
        val adapter = recyclerView.adapter as NewsListAdapter
        adapter.submitList(data)
    }
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "error"])
    fun setImageUrl(view: ImageView, url: String, error: Drawable) {
        view.load(url) {
            view.setImageDrawable(error)
        }
    }

}