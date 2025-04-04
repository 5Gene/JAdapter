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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sparkj.adapter.JVBrecvAdapter
import sparkj.adapter.R
import sparkj.adapter.face.OnViewClickListener
import sparkj.adapter.holder.JViewHolder
import sparkj.adapter.vb.JViewBean

class MediaItem(val uri: Uri? = null) : JViewBean() {

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
                view.height, view.height / 5f
            )
        }
    }

    override fun bindLayout() = R.layout.item_adapter_media

    override fun onBindViewHolder(
        holder: JViewHolder,
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

    override fun areItemsTheSame(newData: JViewBean): Boolean {
        return uri == (newData as MediaItem).uri
    }
}

class MediaSelectViewModel : ViewModel() {

    var maxCount = 5
    val mediasData = MutableLiveData<List<MediaItem>>(listOf(MediaItem()))

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
}

//https://android-docs.cn/training/data-storage/shared/photopicker?hl=zh-cn
class MediaSelectLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs), OnViewClickListener<MediaItem> {

    var mediaMaxCount = 5
    private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private var jadapter: JVBrecvAdapter<MediaItem>
    private val mediaSelectViewModel:MediaSelectViewModel


    init {
        clipChildren = false
        val activity = context as AppCompatActivity
        mediaSelectViewModel = ViewModelProvider(activity)[MediaSelectViewModel::class.java]
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        jadapter = JVBrecvAdapter<MediaItem>(mutableListOf(), this)
        mediaSelectViewModel.mediasData.observe(activity) {
            jadapter.refreshAllData(it)
        }
        adapter = jadapter
        pickMedia = activity.registerForActivityResult(PickMultipleVisualMedia(mediaMaxCount)) { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Selected URI: $uris")
                mediaSelectViewModel.addAll(uris.map { MediaItem(it) })
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    override fun onItemClicked(view: View?, itemData: MediaItem) {
        if (itemData.uri == null) {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
        } else {
            mediaSelectViewModel.remove(itemData)
        }
    }
}