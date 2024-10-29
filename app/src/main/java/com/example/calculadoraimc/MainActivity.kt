package com.example.calculadoraimc

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.slider.Slider
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var PesoEditText: EditText
    lateinit var AlturaEditText: EditText
    lateinit var CalcularPesoBoton: Button
    lateinit var BotonClear: Button

    lateinit var pesoBajo: TextView
    lateinit var pesoNormal: TextView
    lateinit var sobrePeso: TextView
    lateinit var pesoObeso: TextView
    lateinit var pesoExtremo: TextView

    lateinit var stringAltura: TextView
    lateinit var stringPeso: TextView

    lateinit var seekBarAltura: Slider
    lateinit var seekBarPeso: Slider

    lateinit var btnOddAltura: Button
    lateinit var btnAddAltura: Button
    lateinit var btnOddPeso: Button
    lateinit var btnAddPeso: Button

// -------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)                           // referencia a la ubicación de los componentes

        initViews()
        cleanEtiquetas()
        initEditText()

        enableCalculateButton()
        enableBotonClear()

        AlturaEditText.addTextChangedListener {
            enableCalculateButton()
            enableBotonClear()

            if (AlturaEditText.text.toString() != "" && AlturaEditText.text.toString().toFloat() > 0.0)
               seekBarAltura.value = AlturaEditText.text.toString().toFloat()
        }

        PesoEditText.addTextChangedListener {
            enableCalculateButton()
            enableBotonClear()

            if (PesoEditText.text.toString() != "" && PesoEditText.text.toString().toFloat() > 0.0)
               seekBarPeso.value = PesoEditText.text.toString().toFloat()
        }

        AlturaEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                cambioColor(stringAltura)
                initColor(stringPeso)
            }
        }

        PesoEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                cambioColor(stringPeso)
                initColor(stringAltura)
            }
        }

        BotonClear.setOnClickListener {
            cleanEtiquetas()
            initEditText()
          //  AlturaEditText.requestFocus()
        }

        CalcularPesoBoton.setOnClickListener {
            val resultado = PesoEditText.text.toString().toFloat() /
                    (AlturaEditText.text.toString().toFloat() / 100.0).pow(2)

            val IMC = String.format("%.2f", resultado)

            cleanEtiquetas()

            when (resultado) {
                in 0.00..18.50 -> {
                    with (pesoBajo) {
                        text = IMC
                        setBackgroundColor(getColor(R.color.bajoPeso))
                    }
                }

                in 18.51..24.90 -> {
                    with (pesoNormal) {
                        text = IMC
                        setBackgroundColor(getColor(R.color.pesoNormal))
                    }
                }

                in 24.91..29.90 -> {
                    with (sobrePeso) {
                        text = IMC
                        setTextColor(getColor(R.color.black))
                        setBackgroundColor(getColor(R.color.sobrePeso))
                    }
                }

                in 30.00..34.90 -> {
                    with (pesoObeso) {
                        text = IMC
                        setTextColor(getColor(R.color.black))
                        setBackgroundColor(getColor(R.color.sobreObeso))
                    }
                }
                else -> {
                    with (pesoExtremo) {
                        text = IMC
                        setTextColor(getColor(R.color.black))
                        setBackgroundColor(getColor(R.color.sobreExtremo))
                    }
                }
            }
        }

// Slider ALTURA
        with(seekBarAltura) {
            addOnChangeListener { slider, value, fromUser ->
                var altura = value.toInt()
                with (AlturaEditText) {
                    setText(altura.toString())
                    requestFocus()                          // damos foco al textedit de la altura
                }
            }
        }

// Slider PESO
        with(seekBarPeso) {
            addOnChangeListener { slider, value, fromUser ->
                with (PesoEditText) {
                    setText(value.toString())
                    requestFocus()                          // damos foco al textedit del peso
                }
            }
        }

// botones de Altura
        btnOddAltura.setOnClickListener(){
            seekBarAltura.value --
        }

        btnAddAltura.setOnClickListener(){
            seekBarAltura.value ++
        }

// botones de Peso
        btnOddPeso.setOnClickListener(){
            seekBarPeso.value = (seekBarPeso.value - 0.5).toFloat()
        }

        btnAddPeso.setOnClickListener(){
            seekBarPeso.value =  (seekBarPeso.value + 0.5).toFloat()
        }
    }

    private fun initEntorno(texto: TextView, color: Int) {
        with (texto) {
            setText("")
            setBackgroundColor(color)
        }
    }

    fun enableCalculateButton() {
        CalcularPesoBoton.isEnabled = (PesoEditText.text.toString() != "") &&
                                      (AlturaEditText.text.toString() != "") &&
                                      (PesoEditText.text.toString().toFloat() > 0.0) &&
                                      (AlturaEditText.text.toString().toFloat() > 0)
    }

    fun enableBotonClear() {
        BotonClear.isEnabled = (PesoEditText.text.toString() != "") or
                               (AlturaEditText.text.toString() != "")
    }

    fun cambioColor(componente: TextView) {
        with (componente) {
            setTextColor(getColor(R.color.white))
            setBackgroundColor(getColor(R.color.red))
       }
    }

// --- FUNCIONES -----------------------------------------------------------------------------------

    fun cleanEtiquetas(){
        initEntorno(pesoBajo, getColor(R.color.fondo))
        initEntorno(pesoNormal, getColor(R.color.fondo))
        initEntorno(sobrePeso, getColor(R.color.fondo))
        initEntorno(pesoObeso, getColor(R.color.fondo))
        initEntorno(pesoExtremo, getColor(R.color.fondo))
    }

    fun initEditText() {
        AlturaEditText.setText("160")
        PesoEditText.setText("60.0")
    }

    fun initColor(componente: TextView) {
        with (componente) {
            setTextColor(getColor(R.color.black))
            setBackgroundColor(getColor(R.color.fondo))
        }
    }

// capturamos componentes
    fun initViews() {
        PesoEditText = findViewById(R.id.textoPeso)
        AlturaEditText = findViewById(R.id.textoAltura)
        CalcularPesoBoton = findViewById(R.id.botonCalcularIMC)
        BotonClear = findViewById(R.id.botonClear)

        pesoBajo = findViewById(R.id.pesoBajo)
        pesoNormal = findViewById(R.id.pesoNormal)
        sobrePeso = findViewById(R.id.sobrePeso)
        pesoObeso = findViewById(R.id.pesoObeso)
        pesoExtremo = findViewById(R.id.pesoExtremo)

        stringAltura = findViewById(R.id.stringAltura)
        stringPeso = findViewById(R.id.stringPeso)

        seekBarAltura = findViewById(R.id.seekBarAltura)
        seekBarPeso = findViewById(R.id.seekBarPeso)

        btnOddAltura = findViewById(R.id.btnOddAltura)
        btnAddAltura = findViewById(R.id.btnAddAltura)
        btnOddPeso = findViewById(R.id.btnOddPeso)
        btnAddPeso = findViewById(R.id.btnAddPeso)
    }
}