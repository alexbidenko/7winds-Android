package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.persons_data
import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import admire.by.mobi.a7winds.a7winds.ContentData.setIcon
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView



class PersonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var index = 0

    lateinit var name: TextView
    lateinit var who: TextView
    lateinit var image: ImageView
    lateinit var layout: LinearLayout
    lateinit var button_tel: ImageButton
    lateinit var button_mail: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)

        postponeEnterTransition()

        image = view.findViewById(R.id.page_person_image)

        ViewCompat.setTransitionName(image, "shared_image")

        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    sharedElements[names[0]] = image
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.person_back).setOnClickListener {
            (parentFragment as PageFragment).backActivity()
        }

        name = view.findViewById(R.id.page_person_name)
        who = view.findViewById(R.id.page_person_who)
        layout = view.findViewById(R.id.page_person_layout)
        button_tel = view.findViewById(R.id.page_person_tel)
        button_mail = view.findViewById(R.id.page_person_email)

        onDraw()
    }

    private fun onDraw() {
        val data = persons_data.getJSONObject(index)

        name.text =
            data.getString("name")
        who.text =
            data.getString("who")
        image.setImageResource(R.drawable.logo)
        layout.removeAllViews()
        for(i in 0 until projects_data.length()) {
            val cash_data = projects_data.getJSONObject(i).getJSONArray("workers")
            for(j in 0 until cash_data.length()) {
                if(cash_data.getInt(j) == data.getInt("id"))
                    layout.addView(ProjectBlock(parentFragment as PageFragment, i, true).create())
            }
        }

        for(i in 0 until data.getJSONArray("profile").length()) {
            when (i) {
                0 -> {
                    setIcon(view!!.findViewById(R.id.page_person_icon_1),
                        data.getJSONArray("profile").getString(i))
                }
                1 -> {
                    setIcon(view!!.findViewById(R.id.page_person_icon_2),
                        data.getJSONArray("profile").getString(i))
                }
                2 -> {
                    setIcon(view!!.findViewById(R.id.page_person_icon_3),
                        data.getJSONArray("profile").getString(i))
                }
                3 -> {
                    setIcon(view!!.findViewById(R.id.page_person_icon_4),
                        data.getJSONArray("profile").getString(i))
                }
            }
        }

        button_tel.setOnClickListener {
            ContactsProvider.intentTel(data.getString("tel"), this)
        }
        button_mail.setOnClickListener {
            ContactsProvider.intentMail(data.getString("email"), this)
        }

        ContentData.getImage(
            persons_data.getJSONObject(index).getString("avatar"),
            image,
            "person_" + data.getInt("id").toString()
        )

        startPostponedEnterTransition()
    }
}
