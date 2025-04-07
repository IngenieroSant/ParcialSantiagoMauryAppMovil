package com.example.parcialsantiagomaury;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etCorreo, etPassword;
    CheckBox cbTerminos;
    Button btnIngresar, btnRegistrar;
    TextView tvRecordar;
    boolean mostrarPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        cbTerminos = findViewById(R.id.cbTerminos);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvRecordar = findViewById(R.id.tvRecordar);

        btnIngresar.setEnabled(false);


        TextWatcher watcher = new SimpleTextWatcher(() -> validarCampos());
        etCorreo.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        cbTerminos.setOnClickListener(v -> validarCampos());


        tvRecordar.setOnClickListener(v ->
                Toast.makeText(this, "Recordar Contraseña", Toast.LENGTH_SHORT).show()
        );


        btnRegistrar.setOnClickListener(v ->
                Toast.makeText(this, "Proceso de Registro", Toast.LENGTH_SHORT).show()
        );


        btnIngresar.setOnClickListener(v ->
                Toast.makeText(this, "Ingresando al sistema", Toast.LENGTH_SHORT).show()
        );


        etPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableEnd = etPassword.getCompoundDrawables()[DRAWABLE_END];
                if (drawableEnd != null && event.getRawX() >= (etPassword.getRight() - drawableEnd.getBounds().width())) {
                    mostrarPassword = !mostrarPassword;

                    if (mostrarPassword) {
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                    } else {
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
                    }

                    etPassword.setSelection(etPassword.getText().length());

                    v.performClick();
                    return true;
                }
            }
            return false;
        });
    }

    private void validarCampos() {
        String correo = etCorreo.getText().toString();
        String pass = etPassword.getText().toString();
        boolean correoValido = Patterns.EMAIL_ADDRESS.matcher(correo).matches();
        boolean passValido = pass.length() >= 8;
        boolean aceptaTerminos = cbTerminos.isChecked();
        btnIngresar.setEnabled(correoValido && passValido && aceptaTerminos);
    }


    private static class SimpleTextWatcher implements TextWatcher {

        private final Runnable onTextChangedCallback;

        public SimpleTextWatcher(Runnable onTextChangedCallback) {
            this.onTextChangedCallback = onTextChangedCallback;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChangedCallback.run();
        }

        @Override
        public void afterTextChanged(Editable s) {
        
        }
    }
}
