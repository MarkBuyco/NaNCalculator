package com.example.nancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Declaring Variables
    private lateinit var showPopupInstruct: ImageButton
    private lateinit var showResultPopup: Button
    private lateinit var topResult: ImageView
    private lateinit var botResult: ImageView
    private lateinit var shoeResult: ImageView
    private lateinit var top1: ImageButton
    private lateinit var top2: ImageButton
    private lateinit var top3: ImageButton
    private lateinit var bot1: ImageButton
    private lateinit var bot2: ImageButton
    private lateinit var bot3: ImageButton
    private lateinit var shoe1: ImageButton
    private lateinit var shoe2: ImageButton
    private lateinit var shoe3: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adding references
        top1 = findViewById(R.id.top1)
        top2 = findViewById(R.id.top2)
        top3 = findViewById(R.id.top3)

        bot1 = findViewById(R.id.bot1)
        bot2 = findViewById(R.id.bot2)
        bot3 = findViewById(R.id.bot3)

        shoe1 = findViewById(R.id.shoe1)
        shoe2 = findViewById(R.id.shoe2)
        shoe3 = findViewById(R.id.shoe3)

        showPopupInstruct = findViewById(R.id.instructButton)
        showPopupInstruct.setOnClickListener {
            showPopup()
        }

        showResultPopup = findViewById(R.id.resultButton)
        showResultPopup.setOnClickListener {
            showResults()
        }

        // Toggle the selected state of each image button

        top1.setOnClickListener {
            top1.isSelected = !top1.isSelected
            animation(top1)
            stopAnimation(top2)
            stopAnimation(top3)
        }


        top2.setOnClickListener {
            top2.isSelected = !top2.isSelected
            animation(top2)
            stopAnimation(top1)
            stopAnimation(top3)
        }

        top3.setOnClickListener {
            top3.isSelected = !top3.isSelected
            animation(top3)
            stopAnimation(top1)
            stopAnimation(top2)
        }

        bot1.setOnClickListener {
            bot1.isSelected = !bot1.isSelected
            animation(bot1)
            stopAnimation(bot2)
            stopAnimation(bot3)
        }

        bot2.setOnClickListener {
            bot2.isSelected = !bot2.isSelected
            animation(bot2)
            stopAnimation(bot1)
            stopAnimation(bot3)
        }

        bot3.setOnClickListener {
            bot3.isSelected = !bot3.isSelected
            animation(bot3)
            stopAnimation(bot1)
            stopAnimation(bot2)
        }

        shoe1.setOnClickListener {
            shoe1.isSelected = !shoe1.isSelected
            animation(shoe1)
            stopAnimation(shoe2)
            stopAnimation(shoe3)
        }

        shoe2.setOnClickListener {
            shoe2.isSelected = !shoe2.isSelected
            animation(shoe2)
            stopAnimation(shoe1)
            stopAnimation(shoe3)
        }

        shoe3.setOnClickListener {
            shoe3.isSelected = !shoe3.isSelected
            animation(shoe3)
            stopAnimation(shoe1)
            stopAnimation(shoe2)
        }
    }

    // Function for the instruction button
    private fun showPopup() {

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_info, null)

        val width = 1000
        val height = 1800

        val instructWindow = PopupWindow(popupView, width, height, true)
        instructWindow.showAtLocation (popupView, Gravity.BOTTOM, 0, 100)

        val closeButton = popupView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            instructWindow.dismiss()
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        popupView.startAnimation(animation)
    }

    // Function for the result button
    private fun showResults() {
        // Check if the user has selected one top, one bottom, and one shoe
        val selectedTop = getSelectedImageButton("top")
        val selectedBot = getSelectedImageButton("bot")
        val selectedShoe = getSelectedImageButton("shoe")

        if (selectedTop == null || selectedBot == null || selectedShoe == null) {
            // Inform the user if any of the selections are missing
            val missingItems = mutableListOf<String>()
            if (selectedTop == null) missingItems.add("top")
            if (selectedBot == null) missingItems.add("bottom")
            if (selectedShoe == null) missingItems.add("shoe")

            // Construct the toast message based on missing items
            val message = when (missingItems.size) {
                0 -> "Please select one top, one bottom, and one shoe."
                1 -> "Please select a ${missingItems[0]}."
                2 -> "Please select a ${missingItems[0]} and a ${missingItems[1]}."
                3 -> "Please select one top, one bottom, and one shoe."
                else -> ""
            }

            // Display the toast message
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                return
            }
        }

        // All selections are made, display the results
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_result, null)

        val width = 1000
        val height = 1800

        val resultWindow = PopupWindow(popupView, width, height, true)
        resultWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 100)

        topResult = popupView.findViewById(R.id.topImage)
        botResult = popupView.findViewById(R.id.botImage)
        shoeResult = popupView.findViewById(R.id.shoeImage)

        //The thanks button, will run dismiss and clear selections
        val thanksButton = popupView.findViewById<Button>(R.id.thanksButton)
        thanksButton.setOnClickListener {
            resultWindow.dismiss()
            clearSelection()
            stopAnimation(top1)
            stopAnimation(top2)
            stopAnimation(top3)
            stopAnimation(bot1)
            stopAnimation(bot2)
            stopAnimation(bot3)
            stopAnimation(shoe1)
            stopAnimation(shoe2)
            stopAnimation(shoe3)
        }

        // Set the selected top, bottom, and shoe images
        topResult.setImageResource(getImageResource(selectedTop))
        botResult.setImageResource(getImageResource(selectedBot))
        shoeResult.setImageResource(getImageResource(selectedShoe))

        // Find the existing TextView by its ID
        val styleResultTextView = popupView.findViewById<TextView>(R.id.styleResult)

        // Clear any existing text in the TextView
        styleResultTextView.text = ""

        // Custom text result for each combination
        val combinationTextMap = mapOf(
            Triple(top1.isSelected, bot1.isSelected, shoe1.isSelected) to "Streetwear",
            Triple(top1.isSelected, bot2.isSelected, shoe1.isSelected) to "Simple Urban",
            Triple(top1.isSelected, bot3.isSelected, shoe1.isSelected) to "Summer Street",
            Triple(top2.isSelected, bot1.isSelected, shoe1.isSelected) to "Polo Cargo Fusion",
            Triple(top2.isSelected, bot2.isSelected, shoe1.isSelected) to "Urban Monochrome",
            Triple(top2.isSelected, bot3.isSelected, shoe1.isSelected) to "Polo Sporty Summer",
            Triple(top3.isSelected, bot1.isSelected, shoe1.isSelected) to "Hoodie Cargo Fusion",
            Triple(top3.isSelected, bot2.isSelected, shoe1.isSelected) to "Hoodie Urban Monochrome",
            Triple(top3.isSelected, bot3.isSelected, shoe1.isSelected) to "Hoodie Sporty Summer",
            Triple(top1.isSelected, bot1.isSelected, shoe2.isSelected) to "Cargo Casual Edge",
            Triple(top1.isSelected, bot2.isSelected, shoe2.isSelected) to "Urban Black & White Ensemble",
            Triple(top1.isSelected, bot3.isSelected, shoe2.isSelected) to "Casual Streetwear Comfort",
            Triple(top2.isSelected, bot1.isSelected, shoe2.isSelected) to "Polo Street Casual",
            Triple(top2.isSelected, bot2.isSelected, shoe2.isSelected) to "All Black Fit",
            Triple(top2.isSelected, bot3.isSelected, shoe2.isSelected) to "Polo Casual Sportswear",
            Triple(top3.isSelected, bot1.isSelected, shoe2.isSelected) to " Hoodie Street Casual",
            Triple(top3.isSelected, bot2.isSelected, shoe2.isSelected) to "Hoodie Urban",
            Triple(top3.isSelected, bot3.isSelected, shoe2.isSelected) to "Hoodie Casual Sportswear",
            Triple(top1.isSelected, bot1.isSelected, shoe3.isSelected) to "Cargo Street Style",
            Triple(top1.isSelected, bot2.isSelected, shoe3.isSelected) to "Urban Summer Swagger",
            Triple(top1.isSelected, bot3.isSelected, shoe3.isSelected) to "Modern Monochrome Urban",
            Triple(top2.isSelected, bot1.isSelected, shoe3.isSelected) to "Polo Cargo Urban",
            Triple(top2.isSelected, bot2.isSelected, shoe3.isSelected) to "Polo Modern Monochrome",
            Triple(top2.isSelected, bot3.isSelected, shoe3.isSelected) to "Polo Casual Summer Swagger",
            Triple(top3.isSelected, bot1.isSelected, shoe3.isSelected) to "Hoodie Cargo Urban",
            Triple(top3.isSelected, bot2.isSelected, shoe3.isSelected) to "Hoodie Modern Monochrome",
            Triple(top3.isSelected, bot3.isSelected, shoe3.isSelected) to "Hoodie Casual Summer",


        )

        // Find text for specific buttons clicked
        for ((combination, text) in combinationTextMap) {
            val (selectedTop, selectedBot, selectedShoe) = combination
            if (selectedTop && selectedBot && selectedShoe) {
                styleResultTextView.text = text
                break
            }
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        popupView.startAnimation(animation)
    }


    // Get the selected ImageButton for a given category (top, bot, shoe)
    private fun getSelectedImageButton(category: String): ImageButton? {
        return when (category) {
            "top" -> if (top1.isSelected) top1 else if (top2.isSelected) top2 else if (top3.isSelected) top3 else null
            "bot" -> if (bot1.isSelected) bot1 else if (bot2.isSelected) bot2 else if (bot3.isSelected) bot3 else null
            "shoe" -> if (shoe1.isSelected) shoe1 else if (shoe2.isSelected) shoe2 else if (shoe3.isSelected) shoe3 else null
            else -> null
        }
    }

    //Get the image resource for a selected ImageButton
    private fun getImageResource(imageButton: ImageButton?): Int {
        return when (imageButton) {
            top1 -> R.drawable.whiteshirt
            top2 -> R.drawable.blackpolo
            top3 -> R.drawable.hoodie
            bot1 -> R.drawable.cargo
            bot2 -> R.drawable.blackpants
            bot3 -> R.drawable.shorts
            shoe1 -> R.drawable.jordans
            shoe2 -> R.drawable.blacksneakers
            shoe3 -> R.drawable.whiteshoe
            else -> R.drawable.whiteshirt
        }
    }

    // Function to clear selection of buttons
    private fun clearSelection() {
        top1.isSelected = false
        top2.isSelected = false
        top3.isSelected = false
        bot1.isSelected = false
        bot2.isSelected = false
        bot3.isSelected = false
        shoe1.isSelected = false
        shoe2.isSelected = false
        shoe3.isSelected = false

    }

    // Function to animate button click
    private fun animation(view: View) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        view.startAnimation(animation)
    }

    // Function to stop the animation
    private fun stopAnimation(view: View) {
        view.clearAnimation()
    }
}

