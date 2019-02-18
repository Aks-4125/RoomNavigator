package test.com.roomnavigator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val host= NavHostFragment.create(R.navigation.main_navigator)
        supportFragmentManager.beginTransaction().replace(R.id.main_container,host).setPrimaryNavigationFragment(host).commit()

    }
    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.main_container).navigateUp()

}
