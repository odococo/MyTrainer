package com.example.mytrainer.database.remote

import android.util.Log
import com.example.mytrainer.component.Component
import com.google.firebase.firestore.FirebaseFirestore

object Firestore {

    val TAG: String = "Firestore"
    // warning se metto db come variabile di classe --> memory leak

    fun create(
        table: String,
        data: Component,
        callback: (ok: Boolean, info: Any) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        if (data.id.isEmpty()) {
            db.collection(table)
                .add(data.toMap())
                // se va a buon fine, il documento avra' un id univoco
                .addOnSuccessListener { document ->
                    Log.d(TAG, "Aggiunto documento con id: ${document.id}")
                    data.id = document.id
                    callback(true, data)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Errore in inserimento", exception)
                    callback(false, exception)
                }
        } else {
            val id = data.id
            db.collection(table).document(id)
                .set(data.toMap())
                .addOnSuccessListener {
                    Log.d(TAG, "Aggiunto documento con id: $id")
                    callback(true, data)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Errore in inserimento", exception)
                    callback(false, exception)
                }
        }

    }

    fun update(
        table: String,
        data: Component,
        callback: (ok: Boolean, info: Any) -> Unit
    ) {
        val id = data.id
        val db = FirebaseFirestore.getInstance()
        db.collection(table).document(id)
            .update(data.toMap())
            .addOnSuccessListener {
                Log.d(TAG, "Aggiornato documento con successo")
                callback(true, data)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Errore in aggiornamento", exception)
                callback(false, exception)
            }
    }

    // reified server per poter utilizzare la sua classe in esecuzione (la JVM cancella - type erasure)
    // per usare reified bisogna utilizzare inline (non crea molteplici istanze delle funzioni)
    // con inline pero' non posso utilizzare callback senza specificare che non deve avere return
    inline fun <reified T : Component> find(
        table: String,
        field: String,
        value: Any?,
        crossinline callback: (List<T>) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(table)
            .whereEqualTo(field, value)
            .get()
            .addOnSuccessListener { document ->
                val list = document.documents.map { dc ->
                    (dc.toObject(T::class.java) as T).withId(dc.id) as T
                }
                callback(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Errore con il database", exception)
            }
    }

    inline fun <reified T : Component> getAll(
        table: String,
        crossinline callback: (List<T>) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(table)
            .get()
            .addOnSuccessListener { document ->
                val list = document.documents.map { dc ->
                    val obj = dc.toObject(T::class.java) as T
                    obj.id = dc.id
                    obj
                }
                Log.d(TAG, "getAll $table con ${list.size}")
                callback(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Errore con il database", exception)
            }
    }

    inline fun <reified T : Component> get(
        table: String,
        doc: String,
        default: T = T::class.java.newInstance(),
        crossinline callback: (T) -> Unit = {}
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(table).document(doc)
            .get()
            .addOnSuccessListener { document ->
                val obj: T = if (document.data != null) {
                    val obj = document.toObject(T::class.java) as T
                    obj.id = document.id
                    obj
                } else {
                    default
                }
                obj.id = doc
                callback(obj)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Errore con il database", exception)
            }
    }
}