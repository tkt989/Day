package info.tkt989.day

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import org.joda.time.DateTime

fun EditText.addTextChangedListener(callback: (String) -> Unit) {
   addTextChangedListener(object: TextWatcher {
      override fun afterTextChanged(editable: Editable?) { }
      override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) { }

      override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
          callback(text.toString())
      }
   })
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: ((T?) -> Unit)) {
    this.observe(owner, android.arch.lifecycle.Observer<T>(observer))
}

