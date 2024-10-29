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
    lateinit var resultadoIMC: TextView

    lateinit var pesoBajo: TextView
    lateinit var pesoNormal: TextView
    lateinit var sobrePeso: TextView
    lateinit var pesoObeso: TextView
    lateinit var pesoExtremo: TextView

    lateinit var stringAltura: TextView
    lateinit var stringPeso: TextView

    lateinit var seekBarAltura: Slider
    lateinit var seekBarPeso: Slider

// -------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)                           // referencia a la ubicación de los componentes

        initViews()

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
            AlturaEditText.setText("")
            PesoEditText.setText("")
            resultadoIMC.setText("0.00")

            initEntorno(pesoBajo, getColor(R.color.white))
            initEntorno(pesoNormal, getColor(R.color.white))
            initEntorno(sobrePeso, getColor(R.color.white))
            initEntorno(pesoObeso, getColor(R.color.white))
            initEntorno(pesoExtremo, getColor(R.color.white))
            initEditText()
        }

        CalcularPesoBoton.setOnClickListener {
            val resultado = PesoEditText.text.toString().toFloat() /
                    (AlturaEditText.text.toString().toFloat() / 100.0).pow(2)

            resultadoIMC.text = String.format("%.2f", resultado)

            when (resultado) {
                in 0.00..18.50 -> {
                    pesoBajo.text = resultadoIMC.text
                    pesoBajo.setBackgroundColor(getColor(R.color.bajoPeso))
                }

                in 18.51..24.90 -> {
                    pesoNormal.text = resultadoIMC.text
                    pesoNormal.setBackgroundColor(getColor(R.color.pesoNormal))
                }

                in 24.91..29.90 -> {
                    sobrePeso.text = resultadoIMC.text
                    sobrePeso.setBackgroundColor(getColor(R.color.sobrePeso))
                }

                in 30.00..34.90 -> {
                    pesoObeso.text = resultadoIMC.text
                    pesoObeso.setBackgroundColor(getColor(R.color.sobreObeso))
                }

                else -> {
                    pesoExtremo.text = resultadoIMC.text
                    pesoExtremo.setBackgroundColor(getColor(R.color.sobreExtremo))
                }
            }
        }

// Slider ALTURA
        with(seekBarAltura) {
            addOnChangeListener { slider, value, fromUser ->
                AlturaEditText.setText(value.toString())
            }
        }

// Slider PESO
        with(seekBarPeso) {
            addOnChangeListener { slider, value, fromUser ->
                PesoEditText.setText(value.toString())
            }
        }
    }

    private fun initEntorno(texto: TextView, color: Int) {
        texto.setText("")
        texto.setBackgroundColor(color)
    }

    private fun initEditText() {
        AlturaEditText.setText("160")
        PesoEditText.setText("60")
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
        componente.setBackgroundColor(getColor(R.color.yellow))
    }

    fun initColor(componente: TextView) {
        componente.setBackgroundColor(getColor(R.color.fondo))
    }

// capturamos componente
    fun initViews() {
        PesoEditText = findViewById(R.id.textoPeso)
        AlturaEditText = findViewById(R.id.textoAltura)
        CalcularPesoBoton = findViewById(R.id.botonCalcularIMC)
        BotonClear = findViewById(R.id.botonClear)
        resultadoIMC = findViewById<TextView>(R.id.resultadoIMC)

        pesoBajo = findViewById(R.id.pesoBajo)
        pesoNormal = findViewById(R.id.pesoNormal)                          // capturamos componente
        sobrePeso = findViewById(R.id.sobrePeso)                           // capturamos componente
        pesoObeso = findViewById(R.id.pesoObeso)                           // capturamos componente
        pesoExtremo = findViewById(R.id.pesoExtremo)                         // capturamos componente

        stringAltura = findViewById(R.id.stringAltura)                        // capturamos componente
        stringPeso = findViewById(R.id.stringPeso)                          // capturamos componente

        seekBarAltura = findViewById(R.id.seekBarAltura)           // capturamos componente
        seekBarPeso = findViewById(R.id.seekBarPeso)               // capturamos componente
    }
}