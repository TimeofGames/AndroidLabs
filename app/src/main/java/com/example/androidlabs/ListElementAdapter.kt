import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlabs.R
import com.example.androidlabs.UserClickListener

class ListElementAdapter : RecyclerView.Adapter<ListElementAdapter.UserViewHolder>() {

    companion object {
        const val MENU_UPDATE = 1
        const val MENU_DELETE = 2
    }

    private var users = listOf<User>()
    private var listener: UserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.tv_id).text = user.id.toString()
            itemView.findViewById<TextView>(R.id.tv_login).text = user.login
            itemView.findViewById<TextView>(R.id.tv_pass).text = user.pass
            itemView.findViewById<ImageButton>(R.id.ib_more).setOnClickListener {
                showPopupMenu(user, it)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(user)
            }
        }
        private fun showPopupMenu(user: User, view: View) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menu.add(0, MENU_UPDATE, Menu.NONE, "Изменить")
            popupMenu.menu.add(1, MENU_DELETE, Menu.NONE, "Удалить")
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    MENU_UPDATE -> {
                        listener?.onMenuUpdateClick(user)
                    }
                    MENU_DELETE -> {
                        listener?.onMenuDeleteClick(user)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }
    fun setListener(listener: UserClickListener) {
        this.listener = listener
    }
    fun setData(userList: List<User>) {
        users = userList
        notifyDataSetChanged()
    }

}
