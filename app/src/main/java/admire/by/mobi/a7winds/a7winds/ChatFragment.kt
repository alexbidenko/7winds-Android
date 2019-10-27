package admire.by.mobi.a7winds.a7winds

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout

class ChatFragment : Fragment() {

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val sendedText = view.findViewById<EditText>(R.id.chat_sended_message)

        /*val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,
            Server.url.replace("http://", "ws://") + "/7winds-chat-websocket")
        stompClient.connect()

        stompClient.topic("/topic/messages").subscribe { stompMessage ->
            val messageView = MessageView(context!!)
            messageView.message = stompMessage.payload
            messageView.messageAlign = MessageView.MESSAGE_ALIGN_LEFT
            view.findViewById<LinearLayout>(R.id.chat_messages_layout).addView(
                messageView
            )
        }*/

        /*val client = SpringBootWebSocketClient()
        client.id = "sub-001"
        val handler = client.subscribe("/topic/messages")
        handler.addListener { message ->
            val messageView = MessageView(mainActivity!!)
            messageView.message = message.toString()
            messageView.messageAlign = MessageView.MESSAGE_ALIGN_LEFT
            view.findViewById<LinearLayout>(R.id.chat_messages_layout).addView(
                messageView
            )
        }
        client.connect(Server.url.replace("http://", "ws://") + "/7winds-chat-websocket/websocket")*/

        view.findViewById<ImageButton>(R.id.chat_send_message).setOnClickListener {
            val messageView = MessageView(context!!)
            messageView.message = sendedText.text.toString()
            view.findViewById<LinearLayout>(R.id.chat_messages_layout).addView(
                MessageView(context!!)
            )
            //client.webSocket.send("""{"id_form":0,"id_to":0,"message":"${sendedText.text}"}""")
            /*stompClient.send("/app/message",
                """{"id_form":0,"id_to":0,"message":"${sendedText.text}"}""").subscribe()*/

            sendedText.text.clear()
        }

        return view
    }
}
