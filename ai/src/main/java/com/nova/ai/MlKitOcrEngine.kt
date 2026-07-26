package com.nova.ai

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * On-Device Text Recognition OCR Engine powered by Google ML Kit.
 */
class MlKitOcrEngine {

    private val recognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    suspend fun processImageUri(context: Context, imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            try {
                val image = InputImage.fromFilePath(context, imageUri)
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        continuation.resume(visionText.text.ifBlank { "No text detected in image." })
                    }
                    .addOnFailureListener { e ->
                        continuation.resume("OCR Error: ${e.localizedMessage}")
                    }
            } catch (e: Exception) {
                continuation.resume("Failed to process image: ${e.localizedMessage}")
            }
        }
    }
}
