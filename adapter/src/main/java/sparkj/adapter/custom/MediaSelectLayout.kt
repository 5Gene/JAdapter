package sparkj.adapter.custom

import android.content.Context
import android.graphics.Outline
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sparkj.adapter.R
import sparkj.adapter.ViewBeanAdapter
import sparkj.adapter.face.OnViewClickListener
import sparkj.adapter.holder.ViewBeanHolder
import sparkj.adapter.vb.ViewBean

class MediaItem(val uri: Uri? = null) : ViewBean() {

    override fun hashCode(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return uri == (other as? MediaItem)?.uri
    }

    private val outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0, 0, view.width,
                view.height, view.height / 6f
            )
        }
    }

    override fun bindLayout() = R.layout.item_adapter_media

    override fun onBindViewHolder(
        holder: ViewBeanHolder,
        position: Int,
        payloads: List<Any?>?,
        viewClickListener: OnViewClickListener<*>?
    ) {
        if (uri == null) {
            holder.goneViews(R.id.adapter_media_del)
                .setImageResource(
                    R.id.adapter_media_src,
                    R.drawable.icon_media_add
                )
        } else {
            holder.visibleViews(R.id.adapter_media_del);
            val imageView = holder.getView<ImageView>(R.id.adapter_media_src)
            imageView.clipToOutline = true
            imageView.outlineProvider = outlineProvider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val thumbnail = imageView.context.contentResolver.loadThumbnail(uri, Size(520, 520), null)
                imageView.setImageBitmap(thumbnail)
            }
        }
    }

    override fun areItemsTheSame(newData: ViewBean): Boolean {
        return uri == (newData as MediaItem).uri
    }
}


class MediaSelectViewModel : ViewModel() {

    var maxCount = 5
    val mediasData = MutableLiveData<List<MediaItem>>(listOf(MediaItem()))
    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    fun registerForActivityResult(
        activityResultRegister: (
            ActivityResultContract<PickVisualMediaRequest, List<@JvmSuppressWildcards Uri>>, ActivityResultCallback<List<@JvmSuppressWildcards Uri>>
        ) -> ActivityResultLauncher<PickVisualMediaRequest>
    ) {
        pickMedia?.unregister()
        pickMedia = activityResultRegister(PickMultipleVisualMedia(maxCount)) { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Selected Uris: $uris")
                addAll(uris.map { MediaItem(it) })
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    fun launch() {
        pickMedia!!.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
    }

    fun remove(mediaItem: MediaItem) {
        val mediaDatas = mediasData.value!!.toMutableList()
        mediaDatas.remove(mediaItem)
        if (mediaDatas.last().uri != null) {
            mediaDatas.add(MediaItem())
        }
        mediasData.postValue(mediaDatas)
    }

    fun addAll(medias: List<MediaItem>) {
        val mediaDatas = mediasData.value!!.toMutableList()
        val addMediaItem = mediaDatas.removeAt(mediaDatas.size - 1)
        val newMedias = mediaDatas.toMutableSet()
        newMedias.addAll(medias)
        if (newMedias.size < maxCount) {
            newMedias.add(addMediaItem)
            mediasData.postValue(newMedias.toList())
        } else {
            val medias = newMedias.toMutableList()
            mediasData.postValue(medias.subList(0, maxCount))
        }
    }

    override fun onCleared() {
        super.onCleared()
        pickMedia?.unregister()
    }
}

//https://android-docs.cn/training/data-storage/shared/photopicker?hl=zh-cn
class MediaSelectLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs), OnViewClickListener<MediaItem> {

    private var jadapter: ViewBeanAdapter<MediaItem>
    private val mediaSelectViewModel: MediaSelectViewModel


    init {
        clipChildren = false
        val activity = context as AppCompatActivity
        mediaSelectViewModel = ViewModelProvider(activity)[MediaSelectViewModel::class.java]
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        jadapter = ViewBeanAdapter<MediaItem>(mutableListOf(), this)
        mediaSelectViewModel.mediasData.observe(activity) {
            jadapter.refreshAllData(it)
        }
        adapter = jadapter
    }

    override fun onItemClicked(view: View?, itemData: MediaItem) {
        if (itemData.uri == null) {
            mediaSelectViewModel.launch()
        } else {
            mediaSelectViewModel.remove(itemData)
        }
    }
}