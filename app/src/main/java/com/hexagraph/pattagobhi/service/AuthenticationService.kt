package com.hexagraph.pattagobhi.service

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class AuthenticationService {
    fun authenticateWithEmail(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onTask: (Task<AuthResult>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { onTask(it)
            }
    }

    fun createAccountWithEmail(
        auth: FirebaseAuth,
        email: String,
        password: String,
        name: String,
        onTask: (Task<AuthResult>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { onTask(it)
            }
    }

    fun sendEmailVerificationLink(
        auth: FirebaseAuth,
        email: String,
        onTask: (Task<Void>) -> Unit
    ) {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { onTask(it)
            }
    }

    fun updateUserProfile(auth: FirebaseAuth, name: String, onTask: (Task<Void>) -> Unit) {
        val profileUpdates = userProfileChangeRequest{
            displayName = name
        }
        auth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener {onTask(it)
            }
    }

    fun sendPasswordResetEmail(
        auth: FirebaseAuth,
        email: String,
        onTask: (Task<Void>) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { onTask(it)
            }

    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    fun reauthenticateAfterEmailVerification(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onTask: (Task<Void>) -> Unit
    ) {
        val user = auth.currentUser
        if(user == null){
            Log.e(TAG,"No user is logged in to authenticate")
            return
        }
        val credential = EmailAuthProvider
            .getCredential(email,password)

        user.reauthenticate(credential)
            .addOnCompleteListener {
                onTask(it)
            }
    }

    fun googleSignIn(
        auth: FirebaseAuth,
        authCredential: AuthCredential,
        onTask: (Task<AuthResult>) -> Unit
    ) {
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener { onTask(it)
            }
    }

}
