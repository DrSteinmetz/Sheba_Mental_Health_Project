package com.example.sheba_mental_health_project.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.Therapist;
import com.example.sheba_mental_health_project.model.enums.AppointmentState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    @SuppressLint("StaticFieldLeak")
    private static Repository repository;

    private final FirebaseFirestore mCloudDB = FirebaseFirestore.getInstance();

    private final Context mContext;

    private Appointment mCurrentAppointment;

    private ListenerRegistration mPatientAppointmentsListener;
    private ListenerRegistration mTherapistAppointmentsListener;

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

    /*<------ Get Appointment Of Specific Therapist ------>*/
    public interface RepositoryGetAppointmentOfSpecificTherapistInterface {
        void onGetAppointmentOfSpecificTherapistSucceed(List<Appointment> appointments);

        void onGetAppointmentOfSpecificTherapistFailed(String error);
    }

    private RepositoryGetAppointmentOfSpecificTherapistInterface mRepositoryGetAppointmentOfSpecificTherapistListener;

    public void setGetAppointmentOfSpecificTherapist(RepositoryGetAppointmentOfSpecificTherapistInterface repositoryGetAppointmentOfSpecificTherapistListener){
        this.mRepositoryGetAppointmentOfSpecificTherapistListener = repositoryGetAppointmentOfSpecificTherapistListener;
    }

    /*<------ Get Appointment Of Specific Patient ------>*/
    public interface RepositoryGetAppointmentOfSpecificPatientInterface {
        void onGetAppointmentOfSpecificPatientSucceed(List<Appointment> appointments);

        void onGetAppointmentOfSpecificPatientFailed(String error);
    }

    private RepositoryGetAppointmentOfSpecificPatientInterface mRepositoryGetAppointmentOfSpecificPatientListener;

    public void setGetAppointmentOfSpecificPatient(RepositoryGetAppointmentOfSpecificPatientInterface repositoryGetAppointmentOfSpecificPatientListener){
        this.mRepositoryGetAppointmentOfSpecificPatientListener = repositoryGetAppointmentOfSpecificPatientListener;
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
        this.mContext=context;
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
        final String id = mCloudDB.collection(APPOINTMENTS).document().getId();
        appointment.setId(id);
        appointment.setTherapist((Therapist) AuthRepository.getInstance(mContext).getUser());

        mCloudDB.collection(APPOINTMENTS)
                .document(id)
                .set(appointment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: " + id);
                            if (mRepositoryAddAppointmentListener != null) {
                                mRepositoryAddAppointmentListener.onAddAppointmentSucceed(id);
                            }
                        } else {
                            final String error = Objects
                                    .requireNonNull(task.getException()).getMessage();
                            Log.w(TAG, "onComplete: ", task.getException());
                            if (mRepositoryAddAppointmentListener != null) {
                                mRepositoryAddAppointmentListener.onAddAppointmentFailed(error);
                            }
                        }
                    }
                });
    }

    public void getAppointmentsOfSpecificTherapist() {
        final String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        final List<AppointmentState> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentState.PreMeeting);
        stateQuery.add(AppointmentState.Ongoing);

        mTherapistAppointmentsListener = mCloudDB.collection(APPOINTMENTS)
                .whereEqualTo(FieldPath.of("therapist", "id"), id)
                .whereIn("state", stateQuery)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {
                            final List<Appointment> appointments = new ArrayList<>();
                            for (DocumentSnapshot document : value.getDocuments()) {
                                appointments.add(document.toObject(Appointment.class));
                            }
                            Log.d(TAG, "onEvent: " + appointments.size());
                            if (mRepositoryGetAppointmentOfSpecificTherapistListener != null) {
                                mRepositoryGetAppointmentOfSpecificTherapistListener
                                        .onGetAppointmentOfSpecificTherapistSucceed(appointments);
                            }
                        } else {
                            String errorMessage;
                            if (error != null) {
                                errorMessage = error.getMessage();
                            } else {
                                errorMessage = "FIREBASE ERROR IS NULL";
                            }
                            Log.w(TAG, "onEvent: ", error);
                            if (mRepositoryGetAppointmentOfSpecificTherapistListener != null) {
                                mRepositoryGetAppointmentOfSpecificTherapistListener
                                        .onGetAppointmentOfSpecificTherapistFailed(errorMessage);
                            }
                        }
                    }
                });
    }

    public void getAppointmentsOfSpecificPatient() {
        final String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        final List<AppointmentState> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentState.PreMeeting);
        stateQuery.add(AppointmentState.Ongoing);

        mPatientAppointmentsListener = mCloudDB.collection(APPOINTMENTS)
                .whereEqualTo(FieldPath.of("patient", "id"), id)
                .whereIn("state", stateQuery)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {
                            final List<Appointment> appointments = new ArrayList<>();
                            for (DocumentSnapshot document : value.getDocuments()) {
                                appointments.add(document.toObject(Appointment.class));
                            }
                            Log.d(TAG, "onEvent: appointments size: " + appointments.size());
                            if (mRepositoryGetAppointmentOfSpecificPatientListener != null) {
                                mRepositoryGetAppointmentOfSpecificPatientListener
                                        .onGetAppointmentOfSpecificPatientSucceed(appointments);
                            }
                        } else {
                            String errorMessage;
                            if (error != null) {
                                errorMessage = error.getMessage();
                            } else {
                                errorMessage = "FIREBASE ERROR IS NULL";
                            }
                            Log.w(TAG, "onEvent: ", error);
                            if (mRepositoryGetAppointmentOfSpecificPatientListener != null) {
                                mRepositoryGetAppointmentOfSpecificPatientListener
                                        .onGetAppointmentOfSpecificPatientFailed(errorMessage);
                            }
                        }
                    }
                });
    }

    public Appointment getCurrentAppointment() {
        return mCurrentAppointment;
    }

    public void setCurrentAppointment(Appointment mCurrentAppointment) {
        this.mCurrentAppointment = mCurrentAppointment;
    }

    public void removeTherapistAppointmentsListener() {
        mTherapistAppointmentsListener.remove();
    }

    public void removePatientAppointmentsListener() {
        mPatientAppointmentsListener.remove();
    }
}
