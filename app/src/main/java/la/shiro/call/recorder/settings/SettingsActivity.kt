package la.shiro.call.recorder.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import la.shiro.call.recorder.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        setTitle(R.string.app_name_full)
    }
}
