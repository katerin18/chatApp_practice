package com.example.chat.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.UserAdapter
import com.example.chat.model.User
import kotlinx.android.synthetic.main.fragment_chats.*

class ChatsFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<UserAdapter.ViewHolder>? = null

    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var userlist = ArrayList<User>()
        userlist.add(User("Squirrel", "https://wampi.ru/image/RjQ3yNw"))
        userlist.add(User("Adam", "https://wampi.ru/image/RjQ3yNw"))

        super.onViewCreated(view, savedInstanceState)
        usersRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(requireContext(), userlist)
        }
    }
}