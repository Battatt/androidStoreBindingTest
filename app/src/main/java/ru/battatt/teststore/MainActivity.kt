package ru.battatt.teststore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import ru.battatt.teststore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)
        setContentView(binding.root)

        var order = "В заказ входят: "
        var deliveryType = -1
        var sheets = false
        var tips = 0

        binding.ET.addTextChangedListener (
            object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null && s.isDigitsOnly()) tips = s.toString().toInt()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
        )

        binding.BT.setOnClickListener{
            if (binding.CB1.isChecked) order += "${binding.CB1.text}; "
            if (binding.CB2.isChecked) order += "${binding.CB2.text}; "
            if (binding.CB3.isChecked) order += "${binding.CB3.text}; "
            if (binding.CB4.isChecked) order += "${binding.CB4.text}; "
            if (binding.CB5.isChecked) order += "${binding.CB5.text}; "
            if (binding.CB6.isChecked) order += "${binding.CB6.text}; "

            deliveryType= (if ((binding.RG[0] as RadioButton).isChecked) 0
                            else if ((binding.RG[2] as RadioButton).isChecked) 1
                            else 2)

            sheets = binding.S.isChecked

            if (!((binding.CB1.isChecked || binding.CB2.isChecked || binding.CB3.isChecked ||
                        binding.CB4.isChecked || binding.CB5.isChecked || binding.CB6.isChecked)
                        && (deliveryType != -1))
            ) {
                Snackbar.make(binding.root, "Ошибка при оформлении заказа", Snackbar.LENGTH_SHORT).show()
            }
            else {
                order += when (deliveryType) {
                    0 -> "Доставка: самовывоз. "
                    1 -> "Доставка: курьер. "
                    2 -> "Доставка: дрон. "
                    else -> "Доставка: телепортация"
                } + (if (sheets) "С чеком. " else "Без чека. ") + (if (tips > 0) "Чаевые : $tips" else "Без чаевых")
                Snackbar.make(binding.root, "Заказ сформирован. $order", Snackbar.LENGTH_SHORT).show()
            }





        }


    }
}