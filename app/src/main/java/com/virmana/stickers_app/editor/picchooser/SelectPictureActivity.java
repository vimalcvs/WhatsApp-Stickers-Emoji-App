/*
 * Copyright 2020  Vimal CVS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.virmana.stickers_app.editor.picchooser;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.virmana.stickers_app.editor.BaseActivity;


public class SelectPictureActivity extends BaseActivity {
    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);
        checkInitImageLoader();
        setResult(RESULT_CANCELED);

        // Create new fragment and transaction
        Fragment newFragment = new BucketsFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack
        transaction.replace(android.R.id.content, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    void showBucket(final int bucketId) {
        Bundle b = new Bundle();
        b.putInt("bucket", bucketId);
        Fragment f = new ImagesFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, f).addToBackStack(null).commit();
    }

    void imageSelected(final String imgPath, final String imgTaken, final long imageSize) {
        returnResult(imgPath, imgTaken, imageSize);
    }

    private void returnResult(final String imgPath, final String imageTaken, final long imageSize) {
        Intent result = new Intent();
        result.putExtra("imgPath", imgPath);
        result.putExtra("dateTaken", imageTaken);
        result.putExtra("imageSize", imageSize);
        setResult(RESULT_OK, result);
        finish();
    }
}
