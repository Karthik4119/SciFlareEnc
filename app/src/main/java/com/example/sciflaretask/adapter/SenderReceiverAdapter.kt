package com.example.sciflaretask.adapter

import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sciflaretask.R
import com.example.sciflaretask.SciFlareApplication
import com.example.sciflaretask.model.MessageResp
import com.example.sciflaretask.util.Utils
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class SenderReceiverAdapter(private val context: Context, var senderMsgList: List<MessageResp>, var OnMenuClick: (Int) -> Unit) :
    RecyclerView.Adapter<SenderReceiverAdapter.ViewHolder>() {

    val decoded: ByteArray = Base64.decode( SciFlareApplication.publicPrefs.secretKey, Base64.DEFAULT)
    val secretKey: SecretKey = SecretKeySpec(decoded, 0, decoded.size, "DES")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val txtMesage: TextView = itemView.findViewById(R.id.txtMesage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_sender, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var msgItem=senderMsgList.get(position)
        val dateTimeVal=Utils.splitSpaceValue(msgItem.datetime.toString())

        holder.txtDate.text=dateTimeVal.get(0)
        holder.txtTime.text=dateTimeVal.get(1)

        if(msgItem.encMes!!.isEmpty()){
            holder.txtMesage.text=msgItem.encMes
        }else{
            val strResult = msgItem.encMes?.let { Utils.decryptAES(it, secretKey) }
            holder.txtMesage.text=strResult
        }





        holder.itemView.setOnClickListener{
            OnMenuClick.invoke(position)
        }




    }

    override fun getItemCount(): Int {
        return senderMsgList.size
    }
}