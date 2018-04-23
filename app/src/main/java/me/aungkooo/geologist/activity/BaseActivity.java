package me.aungkooo.geologist.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;

/**
 * Created by Ko Oo on 11/4/2018.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    // LocationManager.GPS_PROVIDER has weaker connection
    public String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    public String JPG_FORMAT = ".jpg";
    public String DEGREE = "\u00b0";

    public void makeShortToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void makeLongToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public AutoCompleteTextView createAutoComplete(@IdRes int id, String[] arrayValue)
    {
        AutoCompleteTextView autoCompleteTextView = findViewById(id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arrayValue
        );
        autoCompleteTextView.setAdapter(arrayAdapter);

        return autoCompleteTextView;
    }

    public MultiAutoCompleteTextView createMultiAutoCompleteComma(@IdRes int id, String[] arrayValue)
    {
        MultiAutoCompleteTextView multiAutoCompleteTextView = findViewById(id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                arrayValue
        );
        multiAutoCompleteTextView.setAdapter(arrayAdapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        return multiAutoCompleteTextView;
    }

    public Spinner createSpinner(@IdRes int id, @ArrayRes int arrayValue) {
        Spinner spinner = findViewById(id);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                arrayValue,
                android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        return spinner;
    }

    @NonNull
    public String get(TextInputEditText editText)
    {
        return editText.getText().toString();
    }

    @NonNull
    public String get(TextView text)
    {
        return text.getText().toString();
    }

    @NonNull
    public String get(AutoCompleteTextView autoText)
    {
        return autoText.getText().toString();
    }

    @NonNull
    public String get(MultiAutoCompleteTextView autoText)
    {
        return autoText.getText().toString();
    }

    @NonNull
    public String get(Spinner autoText)
    {
        return autoText.getSelectedItem().toString();
    }

    public int getInt(EditText editText)
    {
        String text = get(editText);
        return Integer.parseInt(text);
    }

    public double getDouble(EditText editText)
    {
        String text = get(editText);
        return Double.parseDouble(text);
    }

    public boolean isVisible(View view)
    {
        return view.getVisibility() == View.VISIBLE;
    }

    public void setExpandToggle(int headerId, int layoutId, int imgId) {
        final LinearLayout linearLayout = findViewById(layoutId);
        final ImageView img = findViewById(imgId);
        RelativeLayout relativeLayout = findViewById(headerId);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVisible(linearLayout))
                {
                    linearLayout.setVisibility(View.VISIBLE);
                    img.setImageResource(R.drawable.ic_expand_less);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    img.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });
    }

    public void addToMediaDatabase(String path) throws IOException
    {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void deleteImageFile(String path) throws IOException {
        /*File tempFile = new File(path);
        tempFile.deleteOnExit();*/

        this.getContentResolver().delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.DATA + " =? ",
                new String[]{path}
        );
    }

    // absolute path for external storage
    // file: path for private storage
    public void setScaledImage(ImageView imageView, String imagePath) throws IOException
    {
        // Get the dimensions of the View
        int targetW = 140;
        int targetH = 140;

        // Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        // Get orientation of image
        ExifInterface exifInterface = new ExifInterface(imagePath);
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        float rotation = 0f;
        if(orientation > 0)
        {
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90f;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180f;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270f;
                    break;
            }
        }

        // Rotate image
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        imageView.setImageBitmap(rotatedBitmap);
        // imageView.setImageBitmap(bitmap);
    }

    public void setImage(CardView layout, ImageView imgView, TextView txtImgName, String imgPath, String imgName)
    {
        layout.setVisibility(View.VISIBLE);

        try {
            setScaledImage(imgView, imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imageName = imgName + JPG_FORMAT;
        txtImgName.setText(imageName);

        try {
            addToMediaDatabase(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private File createImageFile() throws IOException
    {
        String imageFileName = "outcrop" + String.valueOf(locationNo) + "_" + Utility.getDay() +
                Utility.getMonth() + Utility.getYear() + "_" + Utility.getHour() +
                Utility.getMinute() + "_" + System.currentTimeMillis();
        *//*File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );*//*

        File storageDir = new File(Environment.getExternalStorageDirectory(), "Geologist/");
        // File image = new File(storageDir, imageFileName + ".jpg");
        File tempFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        // imagePath = "file:" + image.getAbsolutePath();
        imageAbsolutePath = tempFile.getAbsolutePath();
        imageName = imageFileName;
        return tempFile;
    }*/
}
