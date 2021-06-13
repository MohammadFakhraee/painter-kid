package ir.mohammadhf.painterkid.feature.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.feature.parent.fargments.AboutPainterKidFragment;
import ir.mohammadhf.painterkid.feature.parent.fargments.AboutUsFragment;
import ir.mohammadhf.painterkid.feature.parent.fargments.PurchasableListFragment;

public class ParentActivity extends BaseActivity implements View.OnClickListener {
    private TextView currentSelectedTv;

    private PurchaseHandler purchaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        purchaseHandler = new PurchaseHandler();
        purchaseHandler.getAllProductsDetailFromMarket(this);

        currentSelectedTv = findViewById(R.id.tv_parent_aboutPainterKid);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_parent_fragment_holder, new AboutPainterKidFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        if (view != currentSelectedTv) {
            currentSelectedTv.setBackground(null);
            view.setBackgroundResource(R.color.colorAccent);
            currentSelectedTv = (TextView) view;

            Fragment fragment;
            switch (view.getId()) {
                case R.id.tv_parent_purchasables:
                    fragment = new PurchasableListFragment();
                    break;
                case R.id.tv_parent_aboutUs:
                    fragment = new AboutUsFragment();
                    break;
                case R.id.tv_parent_aboutPainterKid:
                default:
                    fragment = new AboutPainterKidFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_parent_fragment_holder, fragment)
                    .commit();
        }
    }

    public PurchaseHandler getPurchaseHandler() {
        return purchaseHandler;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30001)
            purchaseHandler.handleOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        purchaseHandler.dismiss();
    }
}
