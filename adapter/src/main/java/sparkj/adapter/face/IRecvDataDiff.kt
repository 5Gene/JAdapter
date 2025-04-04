package sparkj.adapter.face

import androidx.annotation.Keep

/**
 * @another 江祖赟
 * @date 2017/7/5.
 */
@Keep
interface IRecvDataDiff<D : IRecvDataDiff<D>> : IRecvData {
    /**
     * 增删 判断的主要依据
     *
     * 检查id之类
     * @param newData
     * @return
     */
    fun areItemsTheSame(newData: D): Boolean = false

    /**
     * areItemsTheSame为true才判断 areContentsTheSame
     * @param newData
     * @return
     */
    fun areContentsTheSame(newData: D): Boolean = true

    fun getChangePayload(newData: D): Any? = null
}
