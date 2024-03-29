package com.eduhub.company.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.eduhub.company.model.AnswerPOJO;
import com.eduhub.company.model.ChatPOJO;
import com.eduhub.company.model.MessagePOJO;
import com.eduhub.company.model.QuestionPOJO;
import com.eduhub.company.model.StudentPOJO;
import com.eduhub.company.model.TeacherPOJO;
import com.eduhub.company.model.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseManagement {
    private DatabaseReference databaseReference;
    private ArrayList<MessagePOJO> arrayList;
    private ArrayList<ChatPOJO> arrayListChat;
    private Context context;
    String Rname ;
    String RpicUrl;
    ChatPOJO contact;
    private String TAG = "1234";
    String uploadId = new String();
    DatabaseReference mDatabaseReference;
//    final static int PICK_PDF_CODE = 2342;
    StorageReference mStorageReference;
    //DatabaseReference mDatabaseReference;
    public DatabaseManagement(Context context){
        this.context = context;
    }
    public void createTeacher(String teacherId, TeacherPOJO teacherPOJO){//teacherId is Id given by FireBaseAuth for the authenticated user
        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher");
        databaseReference.child(teacherId).child("profileInfo").setValue(teacherPOJO);//TeacherInfo added to DB of teacher
    }
    public void createStudent(String teacherId, StudentPOJO studentPOJO){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("student");
        final String studentId = databaseReference.push().getKey();
        studentPOJO.setPassword(studentPOJO.getNumber());
        studentPOJO.setId(studentId);

        databaseReference.child(studentId).child("profileInfo").setValue(studentPOJO);
        databaseReference.child(studentId).child("teachers").push().setValue(teacherId);//teacherId added to the DB oof Student
        databaseReference.child(studentId).child("parent").push().setValue(createParent(studentPOJO));//parentId added to the database of student
        databaseReference=FirebaseDatabase.getInstance().getReference("teacher").child(teacherId);
        databaseReference.child("studentsUnderMe").push().setValue(studentId);//studentId added to the database of teacher
    }
    private String createParent(StudentPOJO studentPOJO){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("parent");
        final String parentId = databaseReference.push().getKey();
        databaseReference.child(parentId).child("ward").push().setValue(studentPOJO.getId());//studentId added to the database of the parent
        databaseReference.child(parentId).child("username").setValue(studentPOJO.getGuardianContact());
        databaseReference.child(parentId).child("password").setValue(studentPOJO.getGuardianContact());
        return parentId;
    }

    public void sendMessage(final String receiverId, final String senderId, final String msg){
        Rname = new String();
        RpicUrl = new String();

        final String chatId = returnChatId(senderId,receiverId);
        final MessagePOJO messagePOJO = new MessagePOJO(msg, returnTime(),returnDate(),senderId);
        final SharedPreferences sharedPreferences = context.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        messagePOJO.setImageurl(sharedPreferences.getString("profilePic", null));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("conversation").child("P2P");
        databaseReference.child(chatId).child("messages").push().setValue(messagePOJO)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                contact = new ChatPOJO();
                contact.setLastMessage(msg);
                contact.setReceiverName(sharedPreferences.getString("name",null));
                contact.setReceiverPicUrl(sharedPreferences.getString("profilePic", null));
                contact.setRecieverId(sharedPreferences.getString("id", null));
                contact.setUnseen(true);

                FirebaseDatabase.getInstance().getReference().child("student")
                        .child(receiverId).child("contacts").child(senderId).setValue(contact);

                contact.setRecieverId(receiverId);
                contact.setUnseen(false);

                FirebaseDatabase.getInstance().getReference().child("student").child(receiverId).child("profileInfo")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                                Rname = studentPOJO.getName();
                                RpicUrl = studentPOJO.getProfilePicURL();
                                Log.d("321", "onDataChange: "+ Rname + RpicUrl);
                                contact.setReceiverName(Rname);
                                contact.setReceiverPicUrl(RpicUrl);
                                FirebaseDatabase.getInstance().getReference().child("student").child(senderId).child("contacts").child(receiverId).setValue(contact);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                Log.d("321", "onDataChange: "+ Rname + RpicUrl);
            }
        });
    }
    public ArrayList<MessagePOJO> getMessages(String receiverId, String senderId){
        arrayList = new ArrayList<>();
        String chatId = returnChatId(senderId,receiverId);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("conversation").child("P2P")
                    .child(chatId).child("messages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()){
                    MessagePOJO messagePOJO = dataSnapshot.getValue(MessagePOJO.class);
                    arrayList.add(messagePOJO);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "321"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return arrayList;
    }
    public ArrayList<ChatPOJO> getChats(final String senderId){
        arrayListChat = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(senderId).child("contacts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnaps : dataSnapshot.getChildren()){
                    String receiverId = dataSnaps.getValue(String.class);
                    final String chatId = returnChatId(senderId,receiverId);
                    final ChatPOJO chatsPOJO = new ChatPOJO();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("conversation").child("P2P").child(chatId);
                    reference.child("lastMessage").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MessagePOJO messagePOJO =dataSnapshot.getValue(MessagePOJO.class);
                            chatsPOJO.setLastMessage(messagePOJO.getMessage());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                    reference.child("unseen").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean unseen = dataSnapshot.getValue(Boolean.class);
                            chatsPOJO.setUnseen(unseen);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    chatsPOJO.setRecieverId(receiverId);
                    reference = FirebaseDatabase.getInstance().getReference("student").child(receiverId);
                    reference.child("profileInfo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                            String picUrl = studentPOJO.getProfilePicURL();
                            String name = studentPOJO.getName();
                            chatsPOJO.setReceiverName(name);
                            chatsPOJO.setReceiverPicUrl(picUrl);
                            arrayListChat.add(chatsPOJO);
                            Log.d(TAG, "Array List Of Chats Users are: "+ chatsPOJO.getReceiverName());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return arrayListChat;
    }
    public ArrayList<ChatPOJO> getAllChats(final String senderId){
        final ArrayList<ChatPOJO> arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("student").child(senderId).child("teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot teacherSnapshot : dataSnapshot.getChildren()){
                    String teacherId = teacherSnapshot.getValue(String.class);
                    Log.d(TAG, "teacherId: "+teacherId);
                    FirebaseDatabase.getInstance().getReference().child("teacher").child(teacherId).child("studentsUnderMe").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for ( DataSnapshot studentSnapshot : dataSnapshot.getChildren()){
                                        final String recieverId = studentSnapshot.getValue(String.class);
                                        Log.d(TAG, "studentId: "+recieverId);

                                        //
                                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("student").child(recieverId).child("profileInfo");
                                        databaseReference1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                                                ChatPOJO chatsPOJO = new ChatPOJO();
                                                chatsPOJO.setRecieverId(recieverId);
                                                chatsPOJO.setReceiverPicUrl(studentPOJO.getProfilePicURL());
                                                chatsPOJO.setReceiverName(studentPOJO.getName());
                                                chatsPOJO.setLastMessage("Start a chat, be closer");//LOL
                                                chatsPOJO.setUnseen(false);

                                                if (!recieverId.equals(senderId))
                                                    arrayList.add(chatsPOJO);
                                                Log.d(TAG, "onReturnChatPOJO: "+studentPOJO.getName()+"   "+chatsPOJO.getReceiverName());
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                        //
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            }
                    );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        return arrayList;
    }
    private ChatPOJO returnChatPOJO(final String recieverId){
        final ChatPOJO chatsPOJO = new ChatPOJO();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(recieverId).child("profileInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentPOJO studentPOJO = dataSnapshot.getValue(StudentPOJO.class);
                chatsPOJO.setRecieverId(recieverId);
                chatsPOJO.setReceiverPicUrl(studentPOJO.getProfilePicURL());
                chatsPOJO.setReceiverName(studentPOJO.getName());
                chatsPOJO.setLastMessage("Start a chat, be closer");//LOL
                chatsPOJO.setUnseen(false);
                Log.d(TAG, "onReturnChatPOJO: "+studentPOJO.getName()+"   "+chatsPOJO.getReceiverName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return chatsPOJO;//last Message Is Null Here for we will return those here who we never talked with.....
    }

    public ArrayList<QuestionPOJO> getQuestion(){
        final ArrayList<QuestionPOJO> arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("question");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    QuestionPOJO questionPOJO = new QuestionPOJO();
                    questionPOJO = dataSnap.child("questionInfo").getValue(QuestionPOJO.class);
                    arrayList.add(questionPOJO);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return arrayList;
    }
    public ArrayList<AnswerPOJO> getAnswer(String questionId){
        final ArrayList<AnswerPOJO> arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("question").child(questionId).child("answers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnaps : dataSnapshot.getChildren()){
                    AnswerPOJO answerPOJO = datasnaps.getValue(AnswerPOJO.class);
                    arrayList.add(answerPOJO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return arrayList;
    }
//    public void sendAssignments(String teacherId, Uri data, String fileName){
//        String databaseLocation = "teacher/"+teacherId+"/infoTab/assginments";
//        String addressTo = teacherId+"/assignment";
//        uploadFile(data,addressTo,fileName,databaseLocation);
//    }
//    public void uploadAssignment(String teacherId,String studentId, Uri data, String fileName){
//        String databaseLocation = "student/"+studentId+"/submission/"+teacherId+"/assignments";
//        String addressTo = studentId + "/" + teacherId + "/" + "/assignment";
//        uploadFile(data,addressTo,fileName,databaseLocation);
//    }
//    public void sendProjects(String teacherId, Uri data, String fileName){
//        String databaseLocation = "teacher/"+teacherId+"/infoTab/assginments";
//        String addressTo = teacherId+"/assignment";
//        uploadFile(data,addressTo,fileName,databaseLocation);
//    }
//    public void uploadProjects(String teacherId,String studentId, Uri data, String fileName){
//        String databaseLocation = "student/"+studentId+"/submission/"+teacherId+"/project";
//        String addressTo = studentId + "/" + teacherId + "/" + "/project";
//        uploadFile(data,addressTo,fileName,databaseLocation);
//
//    }
    public void getSyllabus(String teacherId){}
    public void getMarks(String subject){}
    public void getAssignments(){}
    public void getProjects(){}
    public void uploadSyllabus(){}

    public void uploadQuestion(QuestionPOJO questionPOJO){
        databaseReference = FirebaseDatabase.getInstance().getReference("question");
        String id = databaseReference.push().getKey();
        questionPOJO.setQuestionId(id);
        questionPOJO.setCount(0);
        databaseReference.child(id).child("questionInfo").setValue(questionPOJO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Question uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void uploadAnswer(String questionId, AnswerPOJO answerPOJO){
        databaseReference = FirebaseDatabase.getInstance().getReference("question").child(questionId).child("answers");
        databaseReference.push().setValue(answerPOJO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Answer Successfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Uploading failed! Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateCount(int count, String questionId){
        databaseReference = FirebaseDatabase.getInstance().getReference("question").child(questionId).child("questionInfo");
        databaseReference.child("count").setValue(count);
    }
    private String returnTime(){
        Calendar c = Calendar.getInstance();
        String sTime = c.get(Calendar.HOUR_OF_DAY)
                + c.get(Calendar.MINUTE)/*+c.get(Calendar.MILLISECOND)*/+"";
        return sTime;
    }
    private String returnDate(){
        Calendar c = Calendar.getInstance();
        String sDate = c.get(Calendar.YEAR) + c.get(Calendar.MONTH) + (c.get(Calendar.DAY_OF_MONTH)+1) +"";
        return sDate;
    }
    public String returnChatId(String senderId, String receiverId){
        if (receiverId.compareTo(senderId) > 0 )
            return receiverId + senderId;
        else
            return senderId + receiverId;
    }

//    private void getPDF() {
//        //for greater than lolipop versions we need the permissions asked on runtime
//        //so if the permission is not available user will go to the screen to allow storage permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + context.getPackageName()));
//            context.startActivity(intent);
//            return;
//        }
//
//        //creating an intent for file chooser
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //when the user choses the file
//        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            //if a file is selected
//            if (data.getData() != null) {
//                //uploading the file
//                uploadFile(data.getData());
//            }else{
//                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    //this method is uploading the file
    public String uploadFile(Uri data, String uploaderId, final String name, final String dataType) {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("teacher").child(uploaderId).child("data")
                .child(dataType);

        final StorageReference sRef = FirebaseStorage.getInstance().getReference().child("uploadFile").child(uploaderId).child(System.currentTimeMillis()+name+".pdf");
        Log.d(TAG, "uploadFile: Uploading");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context , "File Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Upload upload = new Upload(name, uri.toString());
                                uploadId = mDatabaseReference.push().getKey();
                                mDatabaseReference.push().setValue(upload);

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "uploadFile: Uploading Failed");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Toast.makeText(context,  round(progress)+"",Toast.LENGTH_SHORT).show();
                        //textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
        return uploadId;
    }
    private double round(double value) {
        int places = 2;
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}