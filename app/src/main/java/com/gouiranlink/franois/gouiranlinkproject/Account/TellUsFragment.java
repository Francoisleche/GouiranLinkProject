package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
Fragment which is the place where people are going to report the problems they encountered
 */

public class TellUsFragment extends Fragment {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE_REQUEST = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private OnFragmentInteractionListener mListener;

    public TellUsFragment() {
        // Required empty public constructor
    }

    public static TellUsFragment newInstance() {
        TellUsFragment fragment = new TellUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = (inflater.inflate(R.layout.fragment_tellus, container, false));

        final EditText description_tellus = (EditText) v.findViewById(R.id.description_tellus);

        Button erreur_selfie_tellus = (Button) v.findViewById(R.id.erreur_selfie_tellus);
        erreur_selfie_tellus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                System.out.println(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                Uri selectedImage = i.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) v.findViewById(R.id.imgView_tellus);

                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bmp);
            }
        });




        Button envoi_mail_erreur_tellus=(Button) v.findViewById(R.id.envoi_mail_erreur_tellus);
        envoi_mail_erreur_tellus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent email = new Intent(Intent.ACTION_SEND,Uri.fromParts(
                        "mailto","abc@gmail.com", null));
                email.setType("text/plain");

                //Obligatoire d'avoir un tableau
                email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "Contact@gouiran-link.com" });
                email.putExtra(Intent.EXTRA_SUBJECT, "Avis");
                if(!description_tellus.getText().equals("")){
                    String s = description_tellus.getText().toString() + "\n";
                    email.putExtra(Intent.EXTRA_TEXT, s);
                }else if(description_tellus.getText().equals("")){
                    email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
                }


                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(email);*/
                //startActivity(Intent.createChooser(email, "Choisir le logiciel"));


                GMailSender sender = new GMailSender("meynardfrancois@gmail.com", "25030516");
                try {
                    sender.sendMail("bonjour", "bonjourtoutlemonde", "meynardfrancois@gmail.com", "meynardfrancois@gmail.com");
                    Toast.makeText(getActivity(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                    Toast.makeText(getActivity(), "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                }



                /*Mail m = new Mail("meynardfrancois@gmail.com", "25030516");

                String[] toArr = {"Contact@gouiran-link.com"};
                m.set_to(toArr);
                m.set_from("meynardfrancois@gmail.com");
                m.set_subject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                m.setBody("Email body.");

                try {
                    m.addAttachment("/sdcard/filelocation");

                    if(m.send()) {
                        Toast.makeText(getActivity(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Email was not sent.", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("MailApp", "Could not send email", e);
                }*/
            }
        });






        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }









    public class GMailSender extends javax.mail.Authenticator {
        private String mailhost = "smtp.gmail.com";
        private String user;
        private String password;
        private Session session;

        {
            Security.addProvider(new JSSEProvider());
        }

        public GMailSender(String user, String password) {
            this.user = user;
            this.password = password;

            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", mailhost);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.quitwait", "false");

            session = Session.getDefaultInstance(props, this);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }

        public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
            try{
                MimeMessage message = new MimeMessage(session);
                DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
                message.setSender(new InternetAddress(sender));
                message.setSubject(subject);
                message.setDataHandler(handler);
                if (recipients.indexOf(',') > 0)
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
                else
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
                Transport.send(message);
            }catch(Exception e){
                System.out.println("passsssssssse pas");
                e.printStackTrace();
            }
        }

        public class ByteArrayDataSource implements DataSource {
            private byte[] data;
            private String type;

            public ByteArrayDataSource(byte[] data, String type) {
                super();
                this.data = data;
                this.type = type;
            }

            public ByteArrayDataSource(byte[] data) {
                super();
                this.data = data;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContentType() {
                if (type == null)
                    return "application/octet-stream";
                else
                    return type;
            }

            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(data);
            }

            public String getName() {
                return "ByteArrayDataSource";
            }

            public OutputStream getOutputStream() throws IOException {
                throw new IOException("Not Supported");
            }
        }
    }



    public final class JSSEProvider extends Provider {

        public JSSEProvider() {
            super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
            AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    put("SSLContext.TLS",
                            "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                    put("Alg.Alias.SSLContext.TLSv1", "TLS");
                    put("KeyManagerFactory.X509",
                            "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                    put("TrustManagerFactory.X509",
                            "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                    return null;
                }
            });
        }
    }




}
