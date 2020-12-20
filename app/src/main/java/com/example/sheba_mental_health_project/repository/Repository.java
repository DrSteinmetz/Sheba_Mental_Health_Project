package com.example.sheba_mental_health_project.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository repository;

    private final FirebaseFirestore mCloudDB = FirebaseFirestore.getInstance();

    private final String PATIENTS = "patients";
    private final String THERAPISTS = "therapists";
    private final String APPOINTMENTS = "appointments";

    private final String TAG = "Repository";

    /**<------ Interfaces ------>*/
    /*<------ Get All Patients ------>*/
    public interface RepositoryGetAllPatientsInterface {
        void onGetAllPatientsSucceed(List<Patient> patients);

        void onGetAllPatientsFailed(String error);
    }

    private RepositoryGetAllPatientsInterface mRepositoryGetAllPatientsListener;

    public void setGetAllPatientsInterface(RepositoryGetAllPatientsInterface repositoryGetAllPatientsListener){
        this.mRepositoryGetAllPatientsListener = repositoryGetAllPatientsListener;
    }

    /*<------ Add Appointment ------>*/
    public interface RepositoryAddAppointmentInterface {
        void onAddAppointmentSucceed(String appointmentId);

        void onAddAppointmentFailed(String error);
    }

    private RepositoryAddAppointmentInterface mRepositoryAddAppointmentListener;

    public void setAddAppointmentInterface(RepositoryAddAppointmentInterface repositoryAddAppointmentListener){
        this.mRepositoryAddAppointmentListener = repositoryAddAppointmentListener;
    }

    /**<------ Singleton ------>*/
    public static Repository getInstance(final Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    private Repository(final Context context) {
    }

    public void getAllPatients() {
        final List<Patient> patients = new ArrayList<>();

        mCloudDB.collection(PATIENTS).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                patients.add(document.toObject(Patient.class));
                            }
                            if (mRepositoryGetAllPatientsListener != null) {
                                mRepositoryGetAllPatientsListener.onGetAllPatientsSucceed(patients);
                            }
                        } else {
                            final String error = task.getException().getMessage();
                            Log.wtf(TAG, "onComplete: ", task.getException());
                            if (mRepositoryGetAllPatientsListener != null) {
                                mRepositoryGetAllPatientsListener.onGetAllPatientsFailed(error);
                            }
                        }
                    }
                });
    }

    public void addAppointment(final Appointment appointment) {
        mCloudDB.collection(APPOINTMENTS)
                .add(appointment)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            final String id = task.getResult().get().getResult().getId();
                            Log.d(TAG, "onComplete: " + id);
                            if (mRepositoryAddAppointmentListener != null) {
                                mRepositoryAddAppointmentListener.onAddAppointmentSucceed(id);
                            }
                        } else {
                            final String error = task.getException().getMessage();
                            Log.w(TAG, "onComplete: ", task.getException());
                            if (mRepositoryAddAppointmentListener != null) {
                                mRepositoryAddAppointmentListener.onAddAppointmentFailed(error);
                            }
                        }
                    }
                });
    }

    public void getAppointmentsOfSpecificTherapist() {
        final List<Appointment>appointments = new ArrayList<>();
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mCloudDB.collection(APPOINTMENTS)
                .whereEqualTo("therapist/id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                final Appointment appointment = document.toObject(Appointment.class);
                                appointments.add(appointment);
                                Log.d(TAG, "onComplete: " + appointment);
                            }
                        } else {
                            final String error = task.getException().getMessage();
                            Log.w(TAG, "onComplete: ", task.getException());
                        }
                    }
                });
    }
}
