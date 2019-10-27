package admire.by.mobi.a7winds.a7winds

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment

object ContactsProvider {
    fun intentTel (tel: String, fragment: Fragment) {
        val number = "tel:" + tel
        fragment.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(number)))
    }

    fun intentMail(mail: String, fragment: Fragment) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL,
            Array(1){ mail })
        fragment.startActivity(Intent.createChooser(intent,
            "Отправить письмо"))
    }
}