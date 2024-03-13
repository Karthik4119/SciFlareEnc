package com.example.sciflaretask.view

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sciflaretask.R
import com.example.sciflaretask.SciFlareApplication
import com.example.sciflaretask.adapter.SenderReceiverAdapter
import com.example.sciflaretask.model.MessageReq
import com.example.sciflaretask.model.MessageResp
import com.example.sciflaretask.util.Utils
import com.example.sciflaretask.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@AndroidEntryPoint
class SenderActivity:AppCompatActivity() {
    private var strResult: String=""
    private var rvSender:RecyclerView?=null
    private var edtMesage:EditText?=null
    private var receiver:TextView?=null
    private var imgSend:ImageView?=null
    private var msgList: ArrayList<MessageResp>? = null
    val viewModel: MyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        initView()
    }

    private fun initView() {
        rvSender=findViewById(R.id.rvSender)
        edtMesage=findViewById(R.id.edtMesage)
        imgSend=findViewById(R.id.imgSend)
        receiver=findViewById(R.id.receiver)

        receiver?.setOnClickListener {
            startActivity(Intent(this,ReceiverActivity::class.java))
        }

       /* val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val secretKey = keyGenerator.generateKey()*/
        val secretKey=Utils.generateKey()

        imgSend?.setOnClickListener {
           var message=edtMesage?.text.toString()


            val decoded: ByteArray = Base64.decode( SciFlareApplication.publicPrefs.secretKey, Base64.DEFAULT)
            val secrqtKey: SecretKey = SecretKeySpec(decoded, 0, decoded.size, "AES")






            if (message.isNotEmpty()) {
                try {
                    strResult = Utils.encryptAES(message, secrqtKey)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val sdf = SimpleDateFormat("MMMM dd,yyyy;HH:mm", Locale.US)
           //     sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                val currentDateandTime: String = sdf.format(Date())
                msgList = ArrayList()
                viewModel.createMessage(MessageReq(strResult,currentDateandTime))
            }

        }

        viewModel.readAllMsg()


        viewModel.sendMesage.observe(this , Observer {
            it.body()?.let {
                edtMesage?.text?.clear()
                viewModel.readAllMsg()
            }

        })

        viewModel.readAllMsgList.observe(this , Observer {
            it.body()?.let { it1 ->
                msgList?.clear()
                msgList= it.body() as ArrayList<MessageResp>?
            }
            rvSender?.layoutManager = LinearLayoutManager(this)
            rvSender?.adapter = SenderReceiverAdapter(this,msgList!!){ pos->

                shareMsg(pos)
            }
        })

    }

    private fun shareMsg(pos: Int) {
        val msg=msgList?.get(pos)?.encMes
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,msg )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}