package ua.kpi.ecampus.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Recipient;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.ui.adapter.BulletinsRecipientAdapter;
import ua.kpi.ecampus.ui.adapter.ItemSpinnerAdapter;
import ua.kpi.ecampus.ui.adapter.NothingSelectedAdapter;
import ua.kpi.ecampus.ui.fragment.DatePickerFragment;
import ua.kpi.ecampus.ui.presenter.SaveBulletinPresenter;
import ua.kpi.ecampus.util.ToastUtil;

/**
 * Created by Administrator on 12.05.2016.
 */
public abstract class SaveBulletinActivity extends BaseActivity implements
        SaveBulletinPresenter.IView {

    @Bind(R.id.edit_text_bulletin_theme)
    protected EditText mSubject;
    @Bind(R.id.edit_text_bulletin_text)
    protected EditText mText;
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.text_view_start_period)
    protected EditText mStartDate;
    @Bind(R.id.text_view_end_period)
    protected EditText mEndDate;
    @Bind(R.id.text_view_creation_date_value)
    protected TextView mCreateDate;
    @Bind(R.id.spinner_profile)
    protected Spinner mSpinnerProfile;
    @Bind(R.id.spinner_group)
    protected Spinner mSpinnerGroup;
    @Bind(R.id.spinner_subdivision)
    protected Spinner mSpinnerSubdivision;
    @Bind(R.id.layout_profile)
    protected RelativeLayout mLayoutProfile;
    @Bind(R.id.layout_group)
    protected RelativeLayout mLayoutGroup;
    @Bind(R.id.rb_all)
    protected RadioButton mRbAll;
    @Bind(R.id.rb_profile)
    protected RadioButton mRbProfile;
    @Bind(R.id.rb_group)
    protected RadioButton mRbGroup;
    @Bind(R.id.btn_more_information)
    protected ImageButton mMoreInfoBtn;
    @Inject
    protected SaveBulletinPresenter mPresenter;

    protected BulletinsRecipientAdapter mAdapter;
    protected ProgressDialog mProgressDialog;

    protected final String START_DATE = "start_date";
    protected final String END_DATE = "end_date";


    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(SaveBulletinActivity.this, R.style
                .AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.progress_sending));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setSubdivisionAdapter(List<Item> list) {
        setSubdivisionSpinner(list);
    }

    @Override
    public void setProfileAdapter(List<Item> list) {
        setProfileSpinner(list);
    }

    @Override
    public void setGroupAdapter(List<Item> list) {
        setGroupSpinner(list);
    }

    @Override
    public void updateBadgeCounter(int count) {
        TextView tvCounter = findViewById(R.id.tv_badge_counter);
        tvCounter.setText(Integer.toString(count));
    }

    @OnClick(R.id.btn_add_recipient)
    public void onAddRecipient() {
        Recipient recipient = createRecipient();
        if (recipient != null) {
            if (!mAdapter.contains(recipient)) {
                mAdapter.addItem(recipient);
                updateBadgeCounter(mAdapter.getItemCount());
            } else {
                ToastUtil.showShortMessage(getString(R.string
                        .recipient_already_exists), this);
            }
        } else {
            ToastUtil.showError(getString(R.string.wrong_recipient), this);
        }
    }

    @OnClick(R.id.btn_show_recipients)
    public void onShowRecipients() {
        View inflatedView = inflateView(R.layout.recipient_popup_layout);
        setRecyclerView(inflatedView);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        PopupWindow popWindow = new PopupWindow(inflatedView, size.x - 50,
                size.y - 900, true);
        popWindow.setBackgroundDrawable(ContextCompat.getDrawable
                (getApplicationContext(), R.drawable.popup_bg));
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.showAtLocation(new LinearLayout(this), Gravity.BOTTOM, 0,
                100);
    }
    @OnClick(R.id.btn_more_information)
    public void onMoreInformation(){
        hideShowMoreInformation(findViewById(R.id.attribute_input_start_period));
        hideShowMoreInformation(findViewById(R.id.attribute_input_end_period));
    }

    private void hideShowMoreInformation(FrameLayout frameLayout){
        if(frameLayout.getVisibility() != View.VISIBLE) {
            mMoreInfoBtn.setImageResource(R.drawable.arrow_up);
            frameLayout.setVisibility(View.VISIBLE);
        }
        else{
            mMoreInfoBtn.setImageResource(R.drawable.arrow_down);
            frameLayout.setVisibility(View.GONE);
        }
    }

    protected void setDateListener() {
        mStartDate.setOnClickListener(v -> setDateTo(mStartDate, "2"));
        mEndDate.setOnClickListener(v -> setDateTo(mEndDate, "1"));
    }

    protected void setDateTo(EditText view, String uniqueString) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setView(view);
        newFragment.show(getFragmentManager(), uniqueString);
    }

    protected void setAdapter() {
        mAdapter = new BulletinsRecipientAdapter(this);
    }

    protected void setProfileSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerProfile.setAdapter(new NothingSelectedAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_profile,
                this));
    }

    protected void setGroupSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerGroup.setAdapter(new NothingSelectedAdapter(
                adapter,
                R.layout.spinner_item_nothing_selected_group,
                this));
    }

    protected void setSubdivisionSpinner(List<Item> list) {
        ArrayAdapter<Item> adapter = new ItemSpinnerAdapter(this, R.layout
                .spinner_item, R.layout.spinner_dropdown_item, list);
        mSpinnerSubdivision.setAdapter(adapter);
        mSpinnerSubdivision.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                //mPresenter.loadGroupsOfSubdivision(item.getId().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void setRadioGroup() {
        RadioGroup radioGroup = findViewById(R.id
                .radio_group_recipient);
        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_all:
                    setVisibility(View.VISIBLE, findViewById(R.id
                            .image_view_subdiv), findViewById(R.id
                            .spinner_subdivision));
                    setVisibility(View.GONE, mLayoutProfile, mLayoutGroup);
                    break;
                case R.id.rb_profile:
                    setVisibility(View.GONE, mLayoutGroup);
                    setVisibility(View.VISIBLE, mLayoutProfile, findViewById
                            (R.id.image_view_subdiv), findViewById(R.id
                            .spinner_subdivision));
                    break;
                case R.id.rb_group:
                    setVisibility(View.GONE, mLayoutProfile, findViewById(R.id
                            .image_view_subdiv), findViewById(R.id
                            .spinner_subdivision));
                    setVisibility(View.VISIBLE, mLayoutGroup);
                    break;
                default:
                    break;
            }
        });
    }

    protected void clearValues() {
        String empty = "";
        mSubject.setText(empty);
        mText.setText(empty);
        mStartDate.setText(empty);
        mEndDate.setText(empty);
        mRbAll.setChecked(true);
        mAdapter.clear();
    }

    protected void setRecyclerView(View parentView) {
        RecyclerView recView = parentView.findViewById(R.id
                .recycler_view_buffer_recipients);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recView.setLayoutManager(layoutManager);
        recView.setAdapter(mAdapter);
    }

    protected boolean isValidInput() {
        boolean isValid = true;
        if (mAdapter.getItemCount() <= 0) {
            ToastUtil.showShortMessage(getString(R.string
                    .recipient_must_be_added), this);
            isValid = false;
        }
        return (isValid &
                validateField(findViewById(R.id
                        .input_theme), mSubject.getText().toString()) &
                validateField(findViewById(R.id
                        .input_text), mText.getText().toString()) &
                validateField(findViewById(R.id
                        .input_start_period), mStartDate.getText().toString())
                & validateField(findViewById(R.id
                .input_end_period), mEndDate.getText().toString())
        );
    }

    private View inflateView(int resource) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService
                (LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(resource, null, false);
    }

    private void setVisibility(int visibility, View... views) {
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    private Recipient createRecipient() {
        Recipient r = null;
        Item subdiv = (Item) mSpinnerSubdivision.getSelectedItem();
        if(subdiv == null)
            return null;
        String subdivId = Integer.toString(subdiv.getId());
        String subdivName = subdiv.getName();
        if (mRbAll.isChecked()) {
            r = new Recipient(subdivId, subdivName,
                    null, null, null, null);
        } else if (mRbProfile.isChecked()) {
            Item profile = (Item) mSpinnerProfile.getSelectedItem();
            if (profile == null) return null;
            r = new Recipient(subdivId, subdivName, Integer.toString(profile
                    .getId()), profile.getName(), null, null);
        } else if (mRbGroup.isChecked()) {
            Item group = (Item) mSpinnerGroup.getSelectedItem();
            if (group == null) return null;
            r = new Recipient(null, null, null, null, Integer
                    .toString(group.getId()), group.getName());
        }
        return r;
    }

    private boolean validateField(TextInputLayout inputLayout, String
            inputField) {
        if (inputField.isEmpty()) {
            inputLayout.setError(getString(R.string.required_field));
            return false;
        } else {
            inputLayout.setError(null);
        }
        return true;
    }

}
