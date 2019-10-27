package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContactsProvider.intentMail
import admire.by.mobi.a7winds.a7winds.ContactsProvider.intentTel
import admire.by.mobi.a7winds.a7winds.ContentData.persons_data
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class PersonBlock(val fragment: PageFragment, val index: Int, val is_with_tile: Boolean = false) {

    fun create(): View {
        val data = persons_data.getJSONObject(index)

        val view = fragment.layoutInflater.inflate(R.layout.person_block, null)

        val image = view.findViewById(R.id.person_image) as ImageView

        view.setOnClickListener {
            fragment.pageData.personFragment.index = index
            fragment.setActive(fragment.pageData.personFragment, image)
        }
        view.findViewById<TextView>(R.id.person_name).text = data.getString("name")
        view.findViewById<TextView>(R.id.person_who).text = data.getString("who")

        view.findViewById<ImageButton>(R.id.person_tel).setOnClickListener {
            intentTel(data.getString("tel"), fragment)
        }
        view.findViewById<ImageButton>(R.id.person_email).setOnClickListener {
            intentMail(data.getString("email"), fragment)
        }

        ContentData.getImage(
            data.getString("avatar"),
            image,
            "person_" + data.getInt("id").toString()
        )

        if(is_with_tile) {
            view.background = fragment.resources.getDrawable(R.drawable.yellow_tile)
            val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(32, 8, 32, 8)
            view.layoutParams = lp

            view.findViewById<TextView>(R.id.person_name).setTextColor(Color.rgb(0, 0, 0))
            view.findViewById<TextView>(R.id.person_who).setTextColor(Color.rgb(0, 0, 0))

            view.findViewById<ImageButton>(R.id.person_tel).setImageResource(R.drawable.ic_call_black_24dp)
            view.findViewById<ImageButton>(R.id.person_email).setImageResource(R.drawable.ic_email_black_24dp)
        }

        return view
    }
}