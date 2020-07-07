package com.vimalcvs.stickers_app.editor.editimage.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.editor.editimage.EditImageActivity;
import com.vimalcvs.stickers_app.editor.editimage.ModuleConfig;
import com.vimalcvs.stickers_app.editor.editimage.task.StickerTask;
import com.vimalcvs.stickers_app.editor.editimage.ui.ColorPicker;
import com.vimalcvs.stickers_app.editor.editimage.view.TextStickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by vimalcvs on 27/11/2017.
 */
public class AddTextFragment extends BaseEditFragment implements TextWatcher {
    public static final int INDEX = ModuleConfig.INDEX_ADDTEXT;
    public static final String TAG = AddTextFragment.class.getName();

    private View mainView;
    private View backToMenu;// 返回主菜单

    private EditText mInputText;//输入框
    private ImageView mTextColorSelector;//颜色选择器
    private TextStickerView mTextStickerView;// 文字贴图显示控件
    private CheckBox mAutoNewLineCheck;
    private RecyclerView recycle_view_fonts;

    private ColorPicker mColorPicker;

    private int mTextColor = Color.WHITE;
    private InputMethodManager imm;


    private SaveTextStickerTask mSaveTask;
    private List<String> fontsList = new ArrayList<>();
    private ImageView text_font;
    private LinearLayout linear_add_text;
    private LinearLayout linear_font;
    private ImageView back_to_text;

    public static AddTextFragment newInstance() {
        AddTextFragment fragment = new AddTextFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mainView = inflater.inflate(R.layout.fragment_edit_image_add_text, null);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTextStickerView = (TextStickerView)getActivity().findViewById(R.id.text_sticker_panel);

        backToMenu = mainView.findViewById(R.id.back_to_main);
        linear_font = mainView.findViewById(R.id.linear_font);
        linear_add_text = mainView.findViewById(R.id.linear_add_text);
        mInputText = (EditText) mainView.findViewById(R.id.text_input);
        mTextColorSelector = (ImageView) mainView.findViewById(R.id.text_color);
        mAutoNewLineCheck = (CheckBox) mainView.findViewById(R.id.check_auto_newline);
        text_font = (ImageView) mainView.findViewById(R.id.text_font);
        back_to_text = (ImageView) mainView.findViewById(R.id.back_to_text);
        recycle_view_fonts = (RecyclerView) mainView.findViewById(R.id.recycle_view_fonts);

        backToMenu.setOnClickListener(new BackToMenuClick());// 返回主菜单
        mColorPicker = new ColorPicker(getActivity(), 255, 0, 0);
        mTextColorSelector.setOnClickListener(new SelectColorBtnClick());
        mInputText.addTextChangedListener(this);
        mTextStickerView.setEditText(mInputText);

        //统一颜色设置
        mTextColorSelector.setBackgroundColor(mColorPicker.getColor());
        mTextStickerView.setTextColor(mColorPicker.getColor());
        fontsList = Arrays.asList(getResources().getStringArray(R.array.fonts));
        FontAdapter   adapter = new FontAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycle_view_fonts.setHasFixedSize(true);
        recycle_view_fonts.setAdapter(adapter);
        recycle_view_fonts.setLayoutManager(layoutManager);


        text_font.setOnClickListener(v -> {
            linear_font.setVisibility(View.VISIBLE);
            linear_add_text.setVisibility(View.GONE);
        });
        back_to_text.setOnClickListener(v -> {
            linear_font.setVisibility(View.GONE);
            linear_add_text.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void afterTextChanged(Editable s) {
        //mTextStickerView change
        String text = s.toString().trim();
        mTextStickerView.setText(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 颜色选择 按钮点击
     */
    private final class SelectColorBtnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            mColorPicker.show();
            Button okColor = (Button) mColorPicker.findViewById(R.id.okColorButton);
            okColor.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTextColor(mColorPicker.getColor());
                    mColorPicker.dismiss();
                }
            });
        }
    }//end inner class

    /**
     * 修改字体颜色
     *
     * @param newColor
     */
    private void changeTextColor(int newColor) {
        this.mTextColor = newColor;
        mTextColorSelector.setBackgroundColor(mTextColor);
        mTextStickerView.setTextColor(mTextColor);

    }

    public void hideInput() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null && isInputMethodShow()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isInputMethodShow() {
        return imm.isActive();
    }

    /**
     * 返回按钮逻辑
     *
     * @author panyi
     */
    private final class BackToMenuClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            backToMain();
        }
    }// end class

    /**
     * 返回主菜单
     */
    @Override
    public void backToMain() {
        hideInput();
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(MainMenuFragment.INDEX);
        activity.mainImage.setVisibility(View.VISIBLE);
        activity.bannerFlipper.showPrevious();
        mTextStickerView.setVisibility(View.GONE);
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_TEXT;
        activity.mainImage.setImageBitmap(activity.getMainBit());
        activity.bannerFlipper.showNext();
        mTextStickerView.setVisibility(View.VISIBLE);
        mInputText.clearFocus();
    }

    /**
     * 保存贴图图片
     */
    public void applyTextImage() {
        if (mSaveTask != null) {
            mSaveTask.cancel(true);
        }

        //启动任务
        mSaveTask = new SaveTextStickerTask(activity);
        mSaveTask.execute(activity.getMainBit());
    }

    /**
     * 文字合成任务
     * 合成最终图片
     */
    private final class SaveTextStickerTask extends StickerTask {

        public SaveTextStickerTask(EditImageActivity activity) {
            super(activity);
        }

        @Override
        public void handleImage(Canvas canvas, Matrix m) {
            float[] f = new float[9];
            m.getValues(f);
            int dx = (int) f[Matrix.MTRANS_X];
            int dy = (int) f[Matrix.MTRANS_Y];
            float scale_x = f[Matrix.MSCALE_X];
            float scale_y = f[Matrix.MSCALE_Y];
            canvas.save();
            canvas.translate(dx, dy);
            canvas.scale(scale_x, scale_y);
            //System.out.println("scale = " + scale_x + "       " + scale_y + "     " + dx + "    " + dy);
            mTextStickerView.drawText(canvas, mTextStickerView.layout_x,
                    mTextStickerView.layout_y, mTextStickerView.mScale, mTextStickerView.mRotateAngle);
            canvas.restore();
        }

        @Override
        public void onPostResult(Bitmap result) {
            mTextStickerView.clearTextContent();
            mTextStickerView.resetView();

            activity.changeMainBitmap(result , true);
            backToMain();
        }
    }//end inner class

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSaveTask != null && !mSaveTask.isCancelled()) {
            mSaveTask.cancel(true);
        }
    }


    public class FontAdapter extends  RecyclerView.Adapter<FontHolder>{

        @Override
        public FontHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, null);
            FontHolder mh = new FontHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(FontHolder holder, final int position) {
            holder.text_view_item_tag_item.setText("Text");
            Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/"+
                    fontsList.get(position));
            holder.text_view_item_tag_item.setTypeface(face);
            holder.text_view_item_tag_item.setOnClickListener(v -> {
                mTextStickerView.setTextFont(face);
            });
        }

        @Override
        public int getItemCount() {
            return fontsList.size();
        }


    }
    public class FontHolder extends RecyclerView.ViewHolder {
        public TextView text_view_item_tag_item ;
        public CardView card_view_tag_item_global ;

        public FontHolder(View itemView) {
            super(itemView);
            this.card_view_tag_item_global=(CardView) itemView.findViewById(R.id.card_view_tag_item_global);
            this.text_view_item_tag_item=(TextView) itemView.findViewById(R.id.text_view_item_tag_item);
        }
    }

}// end class
