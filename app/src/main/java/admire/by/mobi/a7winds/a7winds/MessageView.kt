package admire.by.mobi.a7winds.a7winds

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MessageView(context: Context) : LinearLayout(context) {

    val text: TextView = TextView(context)

    var message: String = ""
        set(value) {
            field = value
            text.text = value
        }

    var messageAlign: Int = MESSAGE_ALIGN_RIGHT
        set(value) {
            field = value
            if(value == MESSAGE_ALIGN_RIGHT) {
                gravity = Gravity.END
            } else if(value == MESSAGE_ALIGN_LEFT) {
                gravity = Gravity.START
            }
        }

    init {
        text.setBackgroundColor(Color.rgb(255, 255, 0))

        gravity = Gravity.END

        setPadding(16, 16, 16, 16)

        addView(text)
    }

    companion object {
        const val MESSAGE_ALIGN_LEFT = 1
        const val MESSAGE_ALIGN_RIGHT = 2
    }
}