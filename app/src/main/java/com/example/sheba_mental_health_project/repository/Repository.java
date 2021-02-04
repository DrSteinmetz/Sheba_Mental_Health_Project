package com.example.sheba_mental_health_project.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.ChatMessage;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.Patient;
import com.example.sheba_mental_health_project.model.Question;
import com.example.sheba_mental_health_project.model.Therapist;
import com.example.sheba_mental_health_project.model.enums.AppointmentStateEnum;
import com.example.sheba_mental_health_project.model.enums.BodyPartEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Repository {

    @SuppressLint("StaticFieldLeak")
    private static Repository repository;

    private final FirebaseFirestore mCloudDB = FirebaseFirestore.getInstance();

    private final Context mContext;

    private Appointment mCurrentAppointment;

    private ListenerRegistration mPatientAppointmentsListener;
    private ListenerRegistration mTherapistAppointmentsListener;
    private ListenerRegistration mGetAllPainPointsListener;

    private final String PATIENTS = "patients";
    private final String THERAPISTS = "therapists";
    private final String APPOINTMENTS = "appointments";
    private final String QUESTIONS = "questions";
    private final String CHAT = "chat";
    private final String QUESTIONS_ENGLISH = "questions_english";
    private final String QUESTIONS_HEBREW = "questions_hebrew";

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

    /*<------ Get All Pain Points ------>*/
    public interface RepositoryGetAllPainPointsInterface {
        void onGetAllPainPointsSucceed(Map<String, List<PainPoint>> painPointsMap);

        void onGetAllPainPointsFailed(String error);
    }

    private RepositoryGetAllPainPointsInterface mRepositoryGetAllPainPointsListener;

    public void setGetAllPainPointsInterface(RepositoryGetAllPainPointsInterface repositoryGetAllPainPointsListener){
        this.mRepositoryGetAllPainPointsListener = repositoryGetAllPainPointsListener;
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

    /*<------ Set Pain Points ------>*/
    public interface RepositorySetPainPointsInterface {
        void onSetPainPointsSucceed(PainPoint painPoint);

        void onSetPainPointsFailed(String error);
    }

    private RepositorySetPainPointsInterface mRepositorySetPainPointsListener;

    public void setSetPainPointsInterface(RepositorySetPainPointsInterface repositorySetPainPointsInterface){
        this.mRepositorySetPainPointsListener = repositorySetPainPointsInterface;
    }

    /*<------ Get Questions of Page ------>*/
    public interface RepositoryGetQuestionsOfPageInterface {
        void onGetQuestionsOfPageSucceed(List<Question> questions);

        void onGetQuestionsOfPageFailed(String error);
    }

    private RepositoryGetQuestionsOfPageInterface mRepositoryGetQuestionsOfPageListener;

    public void setGetQuestionsOfPageInterface(RepositoryGetQuestionsOfPageInterface repositoryGetQuestionsOfPageInterface){
        this.mRepositoryGetQuestionsOfPageListener = repositoryGetQuestionsOfPageInterface;
    }

    /*<------ Update Answers of Appointment ------>*/
    public interface RepositoryUpdateAnswersOfAppointmentInterface {
        void onUpdateAnswersOfAppointmentSucceed(Appointment appointment);

        void onUpdateAnswersOfAppointmentFailed(String error);
    }

    private RepositoryUpdateAnswersOfAppointmentInterface mRepositoryUpdateAnswersOfAppointmentListener;

    public void setUpdateAnswersOfAppointmentInterface(RepositoryUpdateAnswersOfAppointmentInterface repositoryUpdateAnswersOfAppointmentInterface){
        this.mRepositoryUpdateAnswersOfAppointmentListener = repositoryUpdateAnswersOfAppointmentInterface;
    }

    /*<------ Upload Chat Message ------>*/
    public interface RepositoryUploadChatMessageInterface {
        void onUploadChatMessageFailed(String error);
    }

    private RepositoryUploadChatMessageInterface mRepositoryUploadChatMessageListener;

    public void setUploadChatMessageInterface(RepositoryUploadChatMessageInterface repositoryUploadChatMessageInterface){
        this.mRepositoryUploadChatMessageListener = repositoryUploadChatMessageInterface;
    }

    /**<------ Singleton ------>*/
    public static Repository getInstance(final Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    private Repository(final Context context) {
        this.mContext = context;
//        addQuestions();
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

        /*mCloudDB.collection(APPOINTMENTS)
                .document(id)
                .*/
    }

    public void getAppointmentsOfSpecificTherapist() {
        final String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        final List<AppointmentStateEnum> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentStateEnum.PreMeeting);
        stateQuery.add(AppointmentStateEnum.Ongoing);

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

        final List<AppointmentStateEnum> stateQuery = new ArrayList<>();
        stateQuery.add(AppointmentStateEnum.PreMeeting);
        stateQuery.add(AppointmentStateEnum.Ongoing);

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

    public void setPainPoints(final BodyPartEnum bodyPart, final PainPoint painPoint) {
        final Map<String, List<PainPoint>> map =  mCurrentAppointment.getPainPointsOfBodyPartMap();
        List<PainPoint> painPoints = mCurrentAppointment.getPainPointsOfBodyPartMap().get(bodyPart.name());
        if (painPoints == null) {
            painPoints = new ArrayList<>();
            painPoints.add(painPoint);
            map.put(bodyPart.name(), painPoints);
        } else {
            final int index = painPoints.indexOf(painPoint);
            if (index != -1) {
                painPoints.set(index, painPoint);
            } else {
                painPoints.add(painPoint);
            }
        }

        mCloudDB.collection(APPOINTMENTS)
                .document(mCurrentAppointment.getId())
                .update("painPointsOfBodyPartMap", map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (mRepositorySetPainPointsListener != null) {
                                mRepositorySetPainPointsListener.onSetPainPointsSucceed(painPoint);
                            }
                        } else {
                            final String error = Objects.requireNonNull(task.getException())
                                    .getMessage();
                            Log.w(TAG, "onComplete: ", task.getException());
                            if (mRepositorySetPainPointsListener != null) {
                                mRepositorySetPainPointsListener.onSetPainPointsFailed(error);
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

    public List<PainPoint> getSpecificPainPointsList(final BodyPartEnum bodyPartEnum) {
        List<PainPoint> painPoints = mCurrentAppointment.getPainPointsOfBodyPartMap().get(bodyPartEnum.name());
        if (painPoints == null) {
            painPoints = new ArrayList<>();
        }
        return painPoints;
    }

    public void getAllPainPoints() {
        mGetAllPainPointsListener = mCloudDB.collection(APPOINTMENTS)
                .document(mCurrentAppointment.getId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            final Appointment appointment = value.toObject(Appointment.class);
                            if (mRepositoryGetAllPainPointsListener != null) {
                                mRepositoryGetAllPainPointsListener.onGetAllPainPointsSucceed(appointment.getPainPointsOfBodyPartMap());
//                                setCurrentAppointment(appointment);
                                mCurrentAppointment.setPainPointsOfBodyPartMap(appointment.getPainPointsOfBodyPartMap());
                            }
                        } else {
                            Log.w(TAG, "onEvent: ", error);
                            if (mRepositoryGetAllPainPointsListener != null) {
                                mRepositoryGetAllPainPointsListener.onGetAllPainPointsFailed(error.getMessage());
                            }
                        }
                    }
                });
    }

    public void removeTherapistAppointmentsListener() {
        mTherapistAppointmentsListener.remove();
    }

    public void removePatientAppointmentsListener() {
        mPatientAppointmentsListener.remove();
    }

    public void removeGetAllPainPointsListener() {
        mGetAllPainPointsListener.remove();
    }

    public void getQuestions(final ViewModelEnum page) {
        final String language = Locale.getDefault().getLanguage(); // 'he' 'en'

        final List<Question> questions = new ArrayList<>();

        mCloudDB.collection(QUESTIONS)
                .document(language)
                .collection("ENGLISH_QUESTIONS")
                .whereEqualTo("page", page.name())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                questions.add(document.toObject(Question.class));
                            }
                            if (mRepositoryGetQuestionsOfPageListener != null) {
                                mRepositoryGetQuestionsOfPageListener.onGetQuestionsOfPageSucceed(questions);
                            }
                        } else {
                            Log.w(TAG, "onComplete: ", task.getException());
                            if (mRepositoryGetQuestionsOfPageListener != null) {
                                mRepositoryGetQuestionsOfPageListener.onGetQuestionsOfPageFailed(Objects.
                                        requireNonNull(task.getException()).getMessage());
                            }
                        }
                    }
                });
    }

    public void updateAnswersOfAppointment() {
        Log.d(TAG, "updateAnswersOfAppointment: " + mCurrentAppointment.getAnswers());
        mCloudDB.collection(APPOINTMENTS)
                .document(mCurrentAppointment.getId())
                .update("answers", mCurrentAppointment.getAnswers())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (mRepositoryUpdateAnswersOfAppointmentListener != null) {
                                mRepositoryUpdateAnswersOfAppointmentListener.onUpdateAnswersOfAppointmentSucceed(mCurrentAppointment);
                            }
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                            if (mRepositoryUpdateAnswersOfAppointmentListener != null) {
                                mRepositoryUpdateAnswersOfAppointmentListener.onUpdateAnswersOfAppointmentFailed(Objects.
                                        requireNonNull(task.getException()).getMessage());
                            }
                        }
                    }
                });
    }

    public Query getChatQuery() {
        return mCloudDB.collection(APPOINTMENTS)
                .document(mCurrentAppointment.getId())
                .collection(CHAT)
                .orderBy("time");
    }

    public void uploadChatMessageToCloud(final ChatMessage chatMessage) {
        mCloudDB.collection(APPOINTMENTS)
                .document(mCurrentAppointment.getId())
                .collection(CHAT)
                .document(chatMessage.getTime().toString())
                .set(chatMessage)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                        if (mRepositoryUploadChatMessageListener != null) {
                            mRepositoryUploadChatMessageListener.onUploadChatMessageFailed(e.getMessage());
                        }
                    }
                });
    }

    public void addQuestions() {
        final List<Question> questions = new ArrayList<>();

        questions.add(new Question("3", "I Want a Long Meeting", ViewModelEnum.Treaty));
        questions.add(new Question("4", "I Want a Short Meeting", ViewModelEnum.Treaty));
        questions.add(new Question("5", "I want another person to present with me during the meeting", ViewModelEnum.Treaty));

        questions.add(new Question("6", "document 17", ViewModelEnum.Bureaucracy));
        questions.add(new Question("7", "Appointment summery", ViewModelEnum.Bureaucracy));
        questions.add(new Question("8", "Prescriptions", ViewModelEnum.Bureaucracy));

        questions.add(new Question("9", "I feel suicide", ViewModelEnum.SanityCheck));
        questions.add(new Question("10", "I feel aggressive", ViewModelEnum.SanityCheck));
        questions.add(new Question("11", "I hear voices", ViewModelEnum.SanityCheck));
        questions.add(new Question("12", "I feel strong physical pain", ViewModelEnum.SanityCheck));

        questions.add(new Question("1", "I had an accident recently", ViewModelEnum.Statement));
        questions.add(new Question("2", "I have been hospitalized recently", ViewModelEnum.Statement));

        for (Question question : questions) {
            mCloudDB.collection(QUESTIONS)
                    .document("en")
                    .collection("ENGLISH_QUESTIONS")
                    .document(question.getId())
                    .set(question);
        }
    }
}
